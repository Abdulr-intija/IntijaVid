package com.intija.intijavid

/**
 * Created by Intija on 10/8/2018.
 */
class Video {
    companion object {
        class StateController: VideoStateController{
            override fun pauseVideo() {

            }

            override fun playVideo() {

            }

            override fun isPlaying(): Boolean {
                return false
            }

        }
        class Listener: VideoListener{
            override fun onVideoStarted() {

            }

            override fun onVideoFinished() {

            }

            override fun onVideoPlayed() {

            }

            override fun onVideoPaused() {

            }

            override fun onVideoStopped() {

            }

            override fun onBufferStarted() {

            }

            override fun onBufferFinished() {

            }

        }
    }
}