package com.intija.intijavid

/**
 * Created by Intija on 10/8/2018.
 */
interface VideoListener {

    fun onVideoStarted()        fun onVideoFinished()

    fun onVideoPlayed()         fun onVideoPaused()

    fun onVideoStopped()        fun onBufferStarted()

    fun onBufferFinished()

}