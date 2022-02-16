package com.example.mediaplayer.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Song
import com.example.mediaplayer.databinding.TrackItemBinding
import com.example.mediaplayer.ui.music.OnLongClick
import com.example.mediaplayer.ui.music.SongClicked
import com.example.mediaplayer.ui.music.SongsSelected

class MusicViewHolder(
    itemView: View,
    private val onLongClick: OnLongClick,
    private val onSongClicked: SongClicked,
    private val songsSelected: SongsSelected,
) :
    RecyclerView.ViewHolder(itemView) {

    private val binding: TrackItemBinding by viewBinding()
    private var position: Int? = null


    companion object {
        fun fromParent(
            parent: ViewGroup,
            onLongClick: OnLongClick,
            onSongClicked: SongClicked,
            songsSelected: SongsSelected,
        ) =
            MusicViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.track_item, parent, false),
                onLongClick, onSongClicked, songsSelected
            )
    }

    private val imageViewMusic: ImageView by lazy { binding.imageView }
    private val textViewTitle: TextView by lazy { binding.textViewSongTitle }
    private val textViewArtist: TextView by lazy { binding.textViewArtistName }
    private val textViewDuration: TextView by lazy { binding.tvDuration }


    val mainItem by lazy { binding.mainConstraint }

    fun bindView(song: Song, pos: Int) {
        textViewTitle.text = song.title
        textViewArtist.text = song.artistName
        textViewDuration.text = song.duration.toString()

        position = pos

//        mainItem.setOnClickListener {
//            onMusicClickListener.onItemClickListener(song)
//        }


    }
}