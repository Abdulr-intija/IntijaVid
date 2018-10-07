# IntijaVid
An helper video loader library for the basic function of ExoPlayer

The main use of this helper is to play a video with just a single line of code preferably in a FrameLayout(other containers can also be used)

# Installation
To start using this helper, 
* clone the repository import
OR
* import as a module

# Usage
Create a container in your XML layout where video will be played
 ```xml
 ...
 <FrameLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:id="@+id/container" />
 ...
 ```
 
 Then load image into container
 ```
 IntijaVid.with(this).play("http://upnepa.ng/intija/videoplayback.3gp").in(R.id.container);
 ```
 To hide/show controller simply use
 ```
 IntijaVid.with(this).play("http://upnepa.ng/intija/videoplayback.3gp").control(false).in(R.id.container);
 /**
   *where .control(false) simply tells the controller to stay hidden
   *it is set to true by default
 ```