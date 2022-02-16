package com.example.mediaplayer.ui.music

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.databinding.FragmentMusicBinding
import com.example.mediaplayer.playback.MusicNotificationManager
import com.example.mediaplayer.playback.MusicService
import com.example.mediaplayer.playback.PlaybackInfoListener
import com.example.mediaplayer.playback.PlayerAdapter
import com.example.mediaplayer.presentation.recycler.MusicAdapter
import com.example.mediaplayer.utils.util.Utils
import com.example.mediaplayer2.utils.SongProvider
import org.koin.android.viewmodel.ext.android.viewModel

class MusicFragment : Fragment(R.layout.fragment_music){

    private val adapter by lazy { MusicAdapter(onLongClick, songClicked, songsSelected) }

    private val viewModel: PlaylistViewModel by viewModel()
    private val binding: FragmentMusicBinding by viewBinding(FragmentMusicBinding::bind)

    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    private var deviceMusic = mutableListOf<Song>()

    private var mMusicService: MusicService? = null
    private var mIsBound: Boolean? = null
    private var mPlayerAdapter: PlayerAdapter? = null
    private var mUserIsSeeking = false
    private var mPlaybackListener: PlaybackListener? = null
    private var deviceSongs: MutableList<Song>? = null
    private var mMusicNotificationManager: MusicNotificationManager? = null


    private val onLongClick: OnLongClick = object : OnLongClick {
        override fun onSongLongClicked(position: Int) {
            TODO("onSongLongClicked")
        }
    }

    private val songClicked: SongClicked = object : SongClicked {
        override fun onSongClicked(song: Song) {
            onSongSelected(song, deviceSongs!!)
        }
    }

    private val songsSelected: SongsSelected = object : SongsSelected {
        override fun onSelectSongs(selectedSongs: MutableList<Song>) {
            TODO("onSelectSongs")
        }
    }


    private fun initRecycler() {
        binding.recyclerContainer.adapter = adapter
        binding.recyclerContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStart() {
        super.onStart()

        initRecycler()
//        viewModel.getSongsFromDb()
//        viewModel.playlistData.observe(this, Observer {
           getMusic()
//        })



        doBindService()
        setViews()
        initializeSeekBar()
    }
    ////////////////////////////////////////////

    private fun getMusic() {
        deviceMusic.addAll(SongProvider.getAllDeviceSongs(requireContext()))
        adapter.addSongs(deviceMusic)
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {

            mMusicService = (iBinder as MusicService.LocalBinder).instance
            mPlayerAdapter = mMusicService!!.mediaPlayerHolder
            mMusicNotificationManager = mMusicService!!.musicNotificationManager

            if (mPlaybackListener == null) {
                mPlaybackListener = PlaybackListener()
                mPlayerAdapter!!.setPlaybackInfoListener(mPlaybackListener!!)
            }
            if (mPlayerAdapter != null && mPlayerAdapter!!.isPlaying()) {

                restorePlayerStatus()
            }
//            checkReadStoragePermissions()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mMusicService = null
        }
    }

    override fun onPause() {
        super.onPause()
        doUnbindService()
        if (mPlayerAdapter != null && mPlayerAdapter!!.isMediaPlayer()) {
            mPlayerAdapter!!.onPauseActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        doBindService()
        if (mPlayerAdapter != null && mPlayerAdapter!!.isPlaying()) {

            restorePlayerStatus()

            binding.controls.buttonPlayPause.setOnClickListener { resumeOrPause() }
            binding.controls.buttonNext.setOnClickListener { skipNext() }
            binding.controls.buttonPrevious.setOnClickListener { skipPrev() }
        }
    }

    private fun setViews() {
        deviceSongs = SongProvider.getAllDeviceSongs(requireContext())
    }
    
    private fun updatePlayingInfo(restore: Boolean, startPlay: Boolean) {

        if (startPlay) {
            mPlayerAdapter!!.getMediaPlayer()?.start()
            Handler().postDelayed({
                mMusicService!!.startForeground(MusicNotificationManager.NOTIFICATION_ID,
                    mMusicNotificationManager!!.createNotification())
            }, 200)
        }

        val selectedSong = mPlayerAdapter!!.getCurrentSong()

        binding.controls.songTitle.text = selectedSong?.title
        val duration = selectedSong?.duration
        binding.controls.seekBar.max = duration!!
        binding.controls.imageViewControl.setImageBitmap(Utils.songArt(selectedSong.path!!,
            requireContext()))

        if (restore) {
            binding.controls.seekBar.progress = mPlayerAdapter!!.getPlayerPosition()
            updatePlayingStatus()

            Handler().postDelayed({
                if (mMusicService!!.isRestoredFromPause) {
                    mMusicService!!.stopForeground(false)
                    mMusicService!!.musicNotificationManager!!.notificationManager
                        .notify(MusicNotificationManager.NOTIFICATION_ID,
                            mMusicService!!.musicNotificationManager!!.notificationBuilder!!.build())
                    mMusicService!!.isRestoredFromPause = false
                }
            }, 200)
        }
    }

    private fun updatePlayingStatus() {
        val drawable = if (mPlayerAdapter!!.getState() != PlaybackInfoListener.State.PAUSED)
            R.drawable.ic_pause
        else
            R.drawable.ic_play
        binding.controls.buttonPlayPause.post {
            binding.controls.buttonPlayPause.setImageResource(drawable)
        }
    }

    private fun restorePlayerStatus() {
        binding.controls.seekBar.isEnabled = mPlayerAdapter!!.isMediaPlayer()

        if (mPlayerAdapter != null && mPlayerAdapter!!.isMediaPlayer()) {

            mPlayerAdapter!!.onResumeActivity()
            updatePlayingInfo(true, false)
        }
    }

    private fun doBindService() {
        activity?.bindService(Intent(requireContext(),
            MusicService::class.java), mConnection, Context.BIND_AUTO_CREATE)
        mIsBound = true

        val startNotStickyIntent = Intent(requireContext(), MusicService::class.java)
        activity?.startService(startNotStickyIntent)
    }

    private fun doUnbindService() {
        if (mIsBound!!) {
            // Detach our existing connection.
            activity?.unbindService(mConnection)
            mIsBound = false
        }
    }

    private fun onSongSelected(song: Song, songs: List<Song>) {
        if (!binding.controls.seekBar.isEnabled) {
            binding.controls.seekBar.isEnabled = true
        }
        try {
            mPlayerAdapter!!.setCurrentSong(song, songs)
            mPlayerAdapter!!.initMediaPlayer()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun skipPrev() {
        if (checkIsPlayer()) {
            mPlayerAdapter!!.instantReset()
        }
    }

    private fun resumeOrPause() {
        if (checkIsPlayer()) {
            mPlayerAdapter!!.resumeOrPause()
        } else {
            val songs = SongProvider.getAllDeviceSongs(requireContext())
            if (songs.isNotEmpty()) {
                onSongSelected(songs[0], songs)
            }
        }
    }

    private fun skipNext() {
        if (checkIsPlayer()) {
            mPlayerAdapter!!.skip(true)
        }
    }

    private fun checkIsPlayer(): Boolean {
        return mPlayerAdapter!!.isMediaPlayer()
    }


    private fun initializeSeekBar() {
        binding.controls.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                var userSelectedPosition = 0

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    mUserIsSeeking = true
                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                    if (fromUser) {
                        userSelectedPosition = progress

                    }

                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {

                    if (mUserIsSeeking) {

                    }
                    mUserIsSeeking = false
                    mPlayerAdapter!!.seekTo(userSelectedPosition)
                }
            })
    }

    internal inner class PlaybackListener : PlaybackInfoListener() {

        override fun onPositionChanged(position: Int) {
            if (!mUserIsSeeking) {
                binding.controls.seekBar.progress = position
            }
        }

        override fun onStateChanged(@State state: Int) {

            updatePlayingStatus()
            if (mPlayerAdapter!!.getState() != State.PAUSED
                && mPlayerAdapter!!.getState() != State.PAUSED
            ) {
                updatePlayingInfo(false, true)
            }
        }

        override fun onPlaybackCompleted() {

        }
    }
}

