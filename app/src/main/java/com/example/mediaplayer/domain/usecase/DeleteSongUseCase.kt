package com.example.mediaplayer.domain.usecase

import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.domain.repository.PlaylistRepository

class DeleteSongUseCase(private val playlistRepository: PlaylistRepository) {


    fun deleteSongItem(song: Song) {
        playlistRepository.delete(song)
    }
}