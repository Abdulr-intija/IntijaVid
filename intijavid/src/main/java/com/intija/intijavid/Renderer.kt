package com.intija.intijavid


import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory


class Renderer : Fragment() {

    private var mainView: View? = null
    private var sep: SimpleExoPlayer? = null
    private var sev: SimpleExoPlayerView? = null
    private var urlURI: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_renderer, container, false)

        init()
        play()

        return mainView
    }
    private fun play() {
        val ts = DefaultTrackSelector()
        val lc = DefaultLoadControl()

        sep = ExoPlayerFactory.newSimpleInstance(activity, ts, lc)

        sev!!.player = sep
        val ms = ExtractorMediaSource(urlURI, DefaultDataSourceFactory(activity, "intija"), DefaultExtractorsFactory(), null, null)
        sep!!.prepare(ms)
        sep!!.playWhenReady = true
    }

    private fun init() {
        sev = mainView!!.findViewById<View>(R.id.player) as SimpleExoPlayerView
        sev!!.useController = arguments!!.getBoolean("controller")
        urlURI = Uri.parse(arguments!!.getString("url", ""))

    }


    /* Completely releases the current video playbact instance */
    private fun nullify() {
        if (sep != null) {
            sep!!.stop()
            sep!!.release()
            sep = null
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
