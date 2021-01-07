# Face Mask example

## What is it?

It's android example of Android application with Augmented Reality effects on face.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=0Z_BvSqQvPc" target="_blank"><img src="http://img.youtube.com/vi/0Z_BvSqQvPc/0.jpg" alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

Undsigned APK file could be downloaded here: https://drive.google.com/file/d/1pRe2OyKeeM5-DZbs3Xz9TyZ9xORk-JIO/view?usp=sharing . Please add permission to camera.

This application uses [VrFace](https://github.com/oleg-sta/VrFace) library on Android.
This project shows you how easily VrFace library could be used.

## How to build?

Put under directory `app/src/main/assets/` [file-model](http://dlib.net/files/) under the name sp68.dat. File size is about 70 Mbytes.
Didn't want to put under source control. Need to fix build script to do it automatically. 

## How it works?

It uses my submodule [VrFace](https://github.com/oleg-sta/VrFace) with all description of logic in it.

You could just fork it or clone it and add your own effect, you could try to add this library in your application for the effects.
