package com.example.mediaplayer.presentation.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.ui.music.OnLongClick
import com.example.mediaplayer.ui.music.SongClicked
import com.example.mediaplayer.ui.music.SongsSelected

class MusicAdapter(
    private val onLongClick: OnLongClick, private val onSongClicked: SongClicked, private val songsSelected: SongsSelected
) : RecyclerView.Adapter<MusicViewHolder>() {


    private var songsList = mutableListOf<Song>()
    private var selectedSongs = mutableListOf<Song>()
    private var selectionModeActive = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder =
        MusicViewHolder.fromParent(parent, onLongClick,onSongClicked,songsSelected)

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
//        holder.bindView(songsList[position])
        val song = songsList[position]
        holder.bindView(song, position)



        holder.mainItem.isSelected = selectedSongs.contains(song)
        holder.mainItem.setOnLongClickListener {
            onLongClick.onSongLongClicked(position)
            if (!selectionModeActive) {
                selectionModeActive = true
            }
            false
        }
        holder.mainItem.setOnClickListener {
            if (!selectionModeActive) {
                onSongClicked.onSongClicked(song)
            } else {
                if (selectedSongs.contains(song)) {
                    selectedSongs.remove(song)
                    songsSelected.onSelectSongs(getSelectedSongs())

                } else {
                    selectedSongs.add(song)

                    songsSelected.onSelectSongs(getSelectedSongs())

                }
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = songsList.size

    fun addSongs(songs: MutableList<Song>) {
        songsList = songs
        notifyDataSetChanged()
    }

    fun removeSelection() {
        selectionModeActive = false
        selectedSongs.clear()
        notifyDataSetChanged()
    }

    fun getSelectedSongs(): MutableList<Song> {
        return selectedSongs
    }

    fun updateRemoved(song: Song) {
        songsList.remove(song)
        notifyDataSetChanged()
    }



//    fun setOnSongClicked(songClick: SongClicked) {
//        this.onSongClicked = songClick
//    }
//
//    fun setOnLongClick(longClick: OnLongClick) {
//        this.onLongClick = longClick
//    }
//
//    fun setSongsSelected(selection: SongsSelected) {
//        this.songsSelected = selection
//    }


}
