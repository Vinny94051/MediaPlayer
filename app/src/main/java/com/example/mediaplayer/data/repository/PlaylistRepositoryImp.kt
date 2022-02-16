package com.example.mediaplayer.data.repository

import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.data.source.local.AppDatabase
import com.example.mediaplayer.domain.repository.PlaylistRepository

class PlaylistRepositoryImp(private val appDatabase: AppDatabase) : PlaylistRepository {

    override fun delete(song: Song) {
        appDatabase.songDao.delete(song)
    }

    override fun getSongs(): List<Song>? {
        return appDatabase.songDao.loadAll()
    }

    override fun saveSongData(song: Song):Long {
        return appDatabase.songDao.insert(song)
    }
}