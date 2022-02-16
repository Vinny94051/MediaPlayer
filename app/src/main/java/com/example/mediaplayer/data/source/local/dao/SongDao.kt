package com.example.mediaplayer.data.source.local.dao

import androidx.room.*
import com.example.mediaplayer.data.model.Song

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: Song): Long

    @Query("SELECT * FROM Song")
    fun loadAll(): MutableList<Song>

    @Delete
    fun delete(song: Song)

    @Query("DELETE FROM Song")
    fun deleteAll()

    @Query("SELECT * FROM Song where artistId = :songId")
    fun loadOneBySongId(songId: Int): Song?

    @Query("SELECT * FROM Song where title = :songTitle")
    fun loadOneBySongTitle(songTitle: String): Song?

    @Update
    fun update(song: Song)

}