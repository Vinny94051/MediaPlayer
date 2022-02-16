package com.example.mediaplayer.ui.music

import com.example.mediaplayer.data.model.Song

interface SongClicked {
    fun onSongClicked(song: Song)
}