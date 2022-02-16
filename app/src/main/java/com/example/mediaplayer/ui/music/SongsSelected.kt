package com.example.mediaplayer.ui.music

import com.example.mediaplayer.data.model.Song

interface SongsSelected {
    fun onSelectSongs(selectedSongs: MutableList<Song>)
}