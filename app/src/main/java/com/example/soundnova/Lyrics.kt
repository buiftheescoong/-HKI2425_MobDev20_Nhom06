package com.example.soundnova

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Lyrics : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_LYRICS = "lyrics"

        fun newInstance(lyrics: String): Lyrics {
            val fragment = Lyrics()
            val args = Bundle()
            args.putString(ARG_LYRICS, lyrics)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.show_lyrics_fragment, container, false)
        val fullLyricsTextView = view.findViewById<TextView>(R.id.full_lyrics)

        val fullLyrics = arguments?.getString(ARG_LYRICS) ?: ""
        fullLyricsTextView.text = fullLyrics

        return view
    }
}