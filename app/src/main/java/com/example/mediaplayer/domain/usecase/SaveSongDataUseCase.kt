package com.example.mediaplayer.domain.usecase

import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.domain.repository.PlaylistRepository

class SaveSongDataUseCase(private val playlistRepository: PlaylistRepository) {

    fun saveSongItem(song: Song) {
        playlistRepository.saveSongData(song)
    }
}