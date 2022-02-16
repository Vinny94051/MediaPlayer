package com.example.mediaplayer.di.module

import com.example.mediaplayer.domain.repository.PlaylistRepository
import com.example.mediaplayer.domain.usecase.DeleteSongUseCase
import com.example.mediaplayer.domain.usecase.GetSongsUseCase
import com.example.mediaplayer.domain.usecase.SaveSongDataUseCase
import com.example.mediaplayer.ui.music.PlaylistViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { PlaylistViewModel(get(), get(), get()) }

    single { createSaveSongDataUseCase(get()) }

    single { createGetSongsUseCase(get()) }

    single { createDeleteSongUseCase(get()) }

    single { createPlaylistRepository(get()) }

}


fun createSaveSongDataUseCase(
    playlistRepository: PlaylistRepository
): SaveSongDataUseCase {
    return SaveSongDataUseCase(playlistRepository)
}

fun createDeleteSongUseCase(
    playlistRepository: PlaylistRepository
): DeleteSongUseCase {
    return DeleteSongUseCase(playlistRepository)
}


fun createGetSongsUseCase(
    playlistRepository: PlaylistRepository
): GetSongsUseCase {
    return GetSongsUseCase(playlistRepository)
}
