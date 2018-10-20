package com.intija.intijavid


import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory


class Renderer : Fragment(), VideoStateController {
    override fun pauseVideo() {
        pause()
    }

    override fun playVideo() {
       play()
    }

    override fun isPlaying(): Boolean {
        return sep!!.playWhenReady
    }

    private var mainView: View? = null
    private var sep: SimpleExoPlayer? = null
    private var sev: SimpleExoPlayerView? = null
    private var urlURI: Uri? = null
    private var callbacks: VideoListener? = null
    private var videoPlayListener: ExoPlayer.EventListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_renderer, container, false)

        init()
        setupPlayer()

        retainInstance = true
        return mainView
    }

    /* Prepare video player for rendering */
    private fun setupPlayer() {
        val ts = DefaultTrackSelector()
        val lc = DefaultLoadControl()

        sep = ExoPlayerFactory.newSimpleInstance(activity, ts, lc)

        sev!!.player = sep
        val ms = ExtractorMediaSource(urlURI, DefaultDataSourceFactory(activity, "intija"), DefaultExtractorsFactory(), null, null)
        sep!!.prepare(ms)

        sep!!.addListener(videoPlayListener)

        //trigger callback for video started
        if(callbacks != null) {
            callbacks!!.onVideoStarted()
        }
        //start playing video
        play()
    }

    private fun init() {
        sev = mainView!!.findViewById<View>(R.id.player) as SimpleExoPlayerView
        sev!!.useController = arguments!!.getBoolean("controller")
        urlURI = Uri.parse(arguments!!.getString("url", ""))

        declareListeners()
    }

    fun attachVideoListener(listener: VideoListener?){
        if(listener == null){
            return
        }
        
        this.callbacks = listener
    }
    
    private fun declareListeners() {
        videoPlayListener = object: ExoPlayer.EventListener{
            private var bufferCallbackTriggered: Boolean = false

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
               if(callbacks == null){ return }

                if(playbackState == ExoPlayer.STATE_ENDED){
                    callbacks!!.onVideoFinished()
                    callbacks!!.onVideoStopped()

                    //package's environmental boolean variable for runtime video play state
                    StaticVideoVariables.isPlaying = false
                }

                if(playbackState == ExoPlayer.STATE_BUFFERING){
                    if(!bufferCallbackTriggered){
                        callbacks!!.onBufferStarted()
                        bufferCallbackTriggered = true
                    }

                }else{
                    if(bufferCallbackTriggered){
                        callbacks!!.onBufferFinished()
                        bufferCallbackTriggered = false
                    }
                }
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                //empty method required
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                //empty method required
            }

            override fun onPositionDiscontinuity() {
                //empty method required
            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
                //empty method required
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
                //empty method required
            }
        }
    }

    /* Pause video and notify callback for video paused
    * */
    private fun pause(){
     if(sep!!.playWhenReady){
         sep!!.playWhenReady = false

         if(callbacks != null){ callbacks!!.onVideoPaused() }

         //package's environmental boolean variable for runtime video play state
         StaticVideoVariables.isPlaying = false
     }
    }

    /* Play video and notify callback for video played
     */
    private fun play(){
        if(!sep!!.playWhenReady){
            sep!!.playWhenReady = true
            if(callbacks != null){ callbacks!!.onVideoPlayed() }

            //package's environmental boolean variable for runtime video play state
            StaticVideoVariables.isPlaying = true
        }
    }

    /* Completely releases the current video playback instance
    * and mnotify callback for stopped video
    * */
    private fun nullify() {
        if (sep != null) {
            sep!!.stop()
            sep!!.release()
            sep = null
            if(callbacks != null){ callbacks!!.onVideoStopped() }

            //package's environmental boolean variable for runtime video play state
            StaticVideoVariables.isPlaying = false
        }
    }

    override fun onPause() {
        super.onPause()
        nullify()
    }

    override fun onDestroy() {
        super.onDestroy()
        nullify()
    }

}
