package com.example.soundnova.musicPlayer

import java.io.IOException

interface ActionPlaying {
    @Throws(IOException::class)
    fun prevBtnClicked()
    @Throws(IOException::class)
    fun nextBtnClicked()
    @Throws(IOException::class)
    fun playPauseBtnClicked()
    @Throws(IOException::class)
    fun endClicked()
}