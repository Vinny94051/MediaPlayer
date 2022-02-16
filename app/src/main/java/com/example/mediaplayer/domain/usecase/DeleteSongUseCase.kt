package com.example.mediaplayer.domain.usecase

import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.domain.repository.PlaylistRepository

interface DeleteSongUseCase {
    fun deleteSong(song: Song)
}

class DeleteSongUseCaseImpl(private val playlistRepository: PlaylistRepository): DeleteSongUseCase {
    fun deleteSongItem(song: Song) {
        playlistRepository.delete(song)
    }
}
