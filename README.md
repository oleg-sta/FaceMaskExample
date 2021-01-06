# Face Mask example

## What is it?

It's android example of Android application with Augmented Reality effects on face.
This application uses VrFace library on Android.
This project shows you how easily VrFace library could be used.

## How to build?

Put under directory `app/src/main/assets/` [file-model](http://dlib.net/files/) under the name sp68.dat. File size is about 70 Mbytes.
Didn't want to put under source control. Need to fix build script to do it automatically. 

## How it works?

It uses my submodule [VrFace](https://github.com/oleg-sta/VrFace) with all description of logic in it.

You could just fork it or clone it and add your own effect, you could try to add this library in your application for the effects.