package com.intija.intijavid;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Linker: connects IntijaVid, the initializer with Renderer, the final rendering process
 * Literally delivers the processed Uri path to exoplayer
 * Created by Intija
 */

public class Linker {

    private Context context;
    private String path;
    private boolean controller = true;

    public Linker(Context c, String path){

        this.context = c; this.path = path;

    }

    public void in(View v){
        Renderer renderer = new Renderer();
        Bundle bundle = new Bundle(1);
        bundle.putString("url",path);
        renderer.setArguments(bundle);

        FragmentTransaction trans = ((AppCompatActivity)context).getFragmentManager().beginTransaction();

       if(trans.isEmpty())
       { trans.add(v.getId(), renderer).commit(); return;}
         trans.replace(v.getId(), renderer);

    }
    public void in(final int containerResId){
        final Renderer renderer = new Renderer();
        Bundle bundle = new Bundle(1);
        bundle.putString("url",path);
        bundle.putBoolean("controller",controller);
        renderer.setArguments(bundle);

       final FragmentTransaction trans = ((AppCompatActivity)context).getFragmentManager().beginTransaction();

        try {
           bindToFragment(trans,containerResId,renderer);
        }catch (IllegalStateException e){

            /** Here, activity have gotten destroyed and video is not rendered.
             * Leaving behind a need to reinitialize the video onResume **/
            Log.e("Activity destroyed", "Connected activity have been destroyed");
        }
    }

    private void bindToFragment(FragmentTransaction trans, int containerResId, Renderer renderer) {
        if (trans.isEmpty()) {
            trans.add(containerResId, renderer).commit();
            return;
        }
        trans.replace(containerResId, renderer);
    }
 /* set controller of the exoplayer to visible or not-visible */
    public Linker control(boolean controller){
        this.controller = controller;
        return this;
    }
}
