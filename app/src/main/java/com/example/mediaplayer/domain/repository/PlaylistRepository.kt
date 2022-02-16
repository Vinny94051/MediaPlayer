package com.example.mediaplayer.domain.repository

import com.example.mediaplayer.data.model.Song

interface PlaylistRepository {

    fun saveSongData(song: Song): Long

    fun getSongs(): List<Song>?

    fun delete(song: Song)

}