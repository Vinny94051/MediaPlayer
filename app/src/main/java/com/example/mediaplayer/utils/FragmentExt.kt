package com.example.mediaplayer.utils

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mediaplayer.data.model.Song

//// Extension method to get all music files list from external storage/sd card
//fun Fragment.musicFiles(): MutableList<Song> {
//    // Initialize an empty mutable list of music
//    val list: MutableList<Song> = mutableListOf()
//
//    // Get the external storage media store audio uri
//    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
////    val uri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
//
//    // IS_MUSIC : Non-zero if the audio file is music
//    val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
//
//    // Sort the musics
//    val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
////    val sortOrder = MediaStore.Audio.Media.TITLE + " DESC"
//
//    // Query the external storage for music files
//    val cursor: Cursor? = requireContext().contentResolver.query(
//        uri, // Uri
//        null, // Projection
//        selection, // Selection
//        null, // Selection arguments
//        sortOrder // Sort order
//    )
//
//
//    // Если результат запроса не пустой
//    if (cursor != null && cursor.moveToFirst()) {
//        val id: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
//        val title: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
//
//        // Теперь пролистайте музыкальные файлы
//        do {
//            val audioId: Long = cursor.getLong(id)
//            val audioTitle: String = cursor.getString(title)
//            val uriStr = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
//            val duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
//
//
//            // Добавьте текущую музыку в список
//            list.add(Song(audioId, audioTitle, uriStr, artist,duration))
//        } while (cursor.moveToNext())
//    }
//
//    return list
//
//}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}