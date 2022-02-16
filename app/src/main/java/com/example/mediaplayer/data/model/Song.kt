package com.example.mediaplayer.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//@Suppress("DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES")
@Entity(tableName = "Song")
@Parcelize
data class Song(
//    @PrimaryKey val id: Long = 0,
    val title: String,
    val trackNumber: Int,
    val year: Int,
    val duration: Int,
    val path: String?,
    val albumName: String,
    @PrimaryKey val artistId: Int,
    val artistName: String,
) : Parcelable