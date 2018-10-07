  package com.intija.intijavid;

import android.content.Context;
import android.net.Uri;

import java.io.File;

  /**
   * A video loading helper based on the basic function of ExoPlayer
   * Created by Intija
   */

  public final class IntijaVid {

      private static volatile IntijaVid singleton = null;
      private static String path;
      private Context context;

      public IntijaVid(Context c){
          this.context = c;
      }

      /* Builder class mainly use to move operation to a non static environment */
      public static class Builder {
          Context context;
          public Builder(Context c){
              if(c == null){
                  throw new IllegalArgumentException("Context must not be null");
              }
              this.context = c;
          }
          public IntijaVid build(){
              return new IntijaVid(context);
          }
      }

      /* Get the context with which it is to operate */

      public static IntijaVid with(Context c){
          if(singleton == null){
              synchronized (IntijaVid.class){
                  singleton = new Builder(c).build();
              }
          }
          return singleton;
      }

      /** Linker functions to initialize video renderer with provided Uri,URL, or File */

      public Linker play(String videoURL){
          if(videoURL == null){
              throw new IllegalArgumentException("Video URL must not be null");
          }else if(videoURL.isEmpty()){
              throw new IllegalArgumentException("Video URL must not be empty");
          }
          path = videoURL;
          return new Linker(context,path);
      }
      public Linker play(Uri videoURI){
          if(videoURI == null){
              throw new IllegalArgumentException("Video URI must not be null");
          }
          path = videoURI.toString();
          return new Linker(context,path);
      }
      public Linker play(File localVideoFile){
          if(localVideoFile == null){
              throw new IllegalArgumentException("File variable must point to a valid local video file");
          }
          path = Uri.fromFile(localVideoFile).toString();
          return new Linker(context,path);
      }
  }
