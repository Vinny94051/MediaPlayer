package com.example.mediaplayer.domain.usecase

import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.domain.repository.PlaylistRepository

class GetSongsUseCase(private val playlistRepository: PlaylistRepository) {
    fun getSongs(): List<Song>? {
        return playlistRepository.getSongs()
    }
}