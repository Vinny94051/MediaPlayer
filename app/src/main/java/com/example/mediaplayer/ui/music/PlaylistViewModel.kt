package com.example.mediaplayer.ui.music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.domain.usecase.DeleteSongUseCase
import com.example.mediaplayer.domain.usecase.GetSongsUseCase
import com.example.mediaplayer.domain.usecase.SaveSongDataUseCase

class PlaylistViewModel(
    private val saveSongDataUseCase: SaveSongDataUseCase,
    private val getSongsUseCase: GetSongsUseCase,
    private val deleteSongUseCase: DeleteSongUseCase,
) : ViewModel() {


    val playlistData = MutableLiveData<List<Song>>()

    fun saveSongData(song: Song) {
        saveSongDataUseCase.saveSongItem(song)
    }

    fun getSongsFromDb() {
        playlistData.value = getSongsUseCase.getSongs()
    }

    fun removeItemFromList(song: Song) {
        deleteSongUseCase.deleteSongItem(song)
        val list = playlistData.value as ArrayList<Song>
        list.remove(song)
        playlistData.value = list
    }
    fun test(song:Song){

        val list = playlistData.value as ArrayList<Song>
        playlistData.value = listOf(song)
    }
}
