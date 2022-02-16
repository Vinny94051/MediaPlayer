package com.example.mediaplayer.utils

import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.SeekBar
import com.example.mediaplayer.data.model.Song

//private fun addSong() {
//    // Initialize an empty mutable list of music
//    val list: MutableList<Song> = mutableListOf()
//
//    // Get the external storage media store audio uri
//    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
////    val uri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
//
//    // IS_MUSIC : Non-zero if the audio file is music
//    val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
//
//    // Sort the musics
//    val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
////    val sortOrder = MediaStore.Audio.Media.TITLE + " DESC"
//
//    // Query the external storage for music files
//    val cursor: Cursor? = requireContext().contentResolver.query(
//        uri, // Uri
//        null, // Projection
//        selection, // Selection
//        null, // Selection arguments
//        sortOrder // Sort order
//    )
//
//
//    // Если результат запроса не пустой
//    if (cursor != null && cursor.moveToFirst()) {
//        val id: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
//        val title: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
//
//        // Теперь пролистайте музыкальные файлы
//        do {
//            val audioId: Long = cursor.getLong(id)
//            val audioTitle: String = cursor.getString(title)
//            val uriStr = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
//            val duration =
//                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
//
//
//            // Добавьте текущую музыку в список
////                val song = Song(audioId, audioTitle, 0, 2022, 10000,uriStr,"album",0,artist)
//
////                viewModel.saveSongData(song)
//
//        } while (cursor.moveToNext())
//    }
//    cursor?.close()
//}



//// Прослушиватель смены панели поиска
//binding.controls.seekBar.setOnSeekBarChangeListener(object :
//    SeekBar.OnSeekBarChangeListener {
//    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//        if (b) {
//            mPlayer?.seekTo(i * 1000)
//        }
//    }
//    override fun onStartTrackingTouch(seekBar: SeekBar) {
//    }
//    override fun onStopTrackingTouch(seekBar: SeekBar) {
//    }
//})




//// Способ инициализации панели поиска и статистики звука
//private fun initializeSeekBar() {
//    binding.controls.seekBar.max = mPlayer?.seconds!!
//
//    runnable = Runnable {
//        binding.controls.seekBar.progress = mPlayer?.currentSeconds!!
//
//        binding.controls.tvPassControls.text =
//            parsingIntTimeString(mPlayer?.currentSeconds)
//        val diff = mPlayer?.seconds!! - mPlayer?.currentSeconds!!
//        binding.controls.tvDueControls.text = parsingIntTimeString(diff)
//
//        handler.postDelayed(runnable, 1000)
//    }
//    handler.postDelayed(runnable, 1000)
//}



//private fun parsingIntTimeString(intTime: Int?): String {
//    if (intTime != null) {
//        return "${(intTime).div(60)}:${(intTime).rem(60)}"
//    }
//    return "0"
//}




//fun playMusic(uriString: String) {
//    mPlayer?.stop()
//    mPlayer?.reset()
//    mPlayer?.release()
//    val uri = Uri.parse(uriString)
//    Log.d("my!!!", "           $uri")
//    mPlayer = MediaPlayer.create(context, uri)
//    mPlayer?.start()
//}