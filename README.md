# Face Mask example

## What is it?

It's android example of Android application with Augmented Reality effects on face.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=0Z_BvSqQvPc" target="_blank"><img src="http://img.youtube.com/vi/0Z_BvSqQvPc/0.jpg" alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

Unsigned APK file could be downloaded here: https://drive.google.com/file/d/1pRe2OyKeeM5-DZbs3Xz9TyZ9xORk-JIO/view?usp=sharing . Please add permission to camera.

This application uses [VrFace](https://github.com/oleg-sta/VrFace) library on Android.
This project shows you how easily VrFace library could be used.

## How to build?

Download model file from http://dlib.net/files/shape_predictor_68_face_landmarks.dat.bz2.
Unzip it. Rename it to `sp68.dat`, and put under directory `app/src/main/assets/`
File size is about 70 Mbytes, that's why I didn't want to put under source control.
Need to fix build script to do it automatically. 

## How it works?

It uses my library [VrFace](https://github.com/oleg-sta/VrFace).
This library makes all the work, please read description for more details.
As it written in the library's description we  need to be implemented only:
* [layout](app/src/main/res/layout/fast_view.xml) with `com.stoleg.vrface.camera.FastCameraView` for camera and `GLSurfaceView` for providing result
* [MainActivity](app/src/main/java/com/stoleg/facemask/MainActivity.java) initialized GLSurfaceView, native library, added model for 3d face and model for points on the face
* [ShaderEffectMask](app/src/main/java/com/stoleg/facemask/ShaderEffectMask.java) implements shaders effects with [shaders](app/src/main/assets/shaders)
 
You could just fork it or clone it and add your own effect, you could try to add this library in your application for the effects.
