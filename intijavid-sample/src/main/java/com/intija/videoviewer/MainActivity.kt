package com.intija.videoviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.intija.intijavid.*
import com.intija.intijavid.IntijaVid

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoTestTwo() //OR videoTestOne()
    }

    private fun videoTestOne(){
        IntijaVid.with(this).play("http://video.com/one").`in`(R.id.videoContainer)
    }

    private fun videoTestTwo(){
        val intijaVid: IntijaVid = IntijaVid(this)

        intijaVid.play("https://video.com/two").`in`(R.id.videoContainer, object: VideoListener{
            override fun onBufferFinished() {
                //Do stuff
            }

            override fun onVideoFinished() {
                //do stuff
            }

            override fun onBufferStarted() {
                //do stuff
            }

            override fun onVideoPaused() {
                //do stuff
            }

            override fun onVideoPlayed() {
                //do your thing
            }

            override fun onVideoStarted() {
                //do you thing
            }

            override fun onVideoStopped() {
                //do your stuff
            }
        })
    }
}
