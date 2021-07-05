# Face Mask example

## What is it?

It's android example of Android application with Augmented Reality effects on face.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=0Z_BvSqQvPc" target="_blank"><img src="http://img.youtube.com/vi/0Z_BvSqQvPc/0.jpg" alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

Unsigned APK file could be downloaded here: https://drive.google.com/file/d/1pRe2OyKeeM5-DZbs3Xz9TyZ9xORk-JIO/view?usp=sharing . Please add permission to camera.

This application uses [VrFace](https://github.com/oleg-sta/VrFace) library on Android.
This project shows you how easily VrFace library could be used.

## How to build?

Fork and clone this project.

Download model file from http://dlib.net/files/shape_predictor_68_face_landmarks.dat.bz2.
Unzip it, like this:
`bzip2 -d shape_predictor_68_face_landmarks.dat.bz2`.
As a result you should have file:
`shape_predictor_68_face_landmarks.dat`,
rename it to:
`sp68.dat`, and put under the directory `app/src/main/assets/`.
File size is about 70 Mbytes, that's why I don't want to put it under source control. 

**Note.** Loading model `sp68.dat` is very heavy task, it can take several seconds or dozen seconds to be uploaded.
You could try to retrain model and decrease size and therefore size of the application and initialization time. 

Create `local.properties` in root directory. Add two properties into file:
```
GITHUB_USER=YOU_GITHUB_USER_NAME
GITHUB_TOKEN=YOUR_GITHUB_TOKEN
```
put your `github` username instead of `YOU_GITHUB_USER_NAME`, to get `YOUR_GITHUB_TOKEN` open https://github.com/settings/tokens. Generate new token with `read:packages` permission. 
To read more about credentials please read [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages)

Build project and install onto your phone. Add permission to Camera manually in your settings.

## How it works?

It uses my library [VrFace](https://github.com/oleg-sta/VrFace).
This library makes all the work, please read description for more details.
As it written in the library's description we need to implement only:
* [layout](app/src/main/res/layout/fast_view.xml) with `com.stoleg.vrface.camera.FastCameraView` for camera and `GLSurfaceView` for providing result
* [MainActivity](app/src/main/java/com/stoleg/facemask/MainActivity.java) initialize GLSurfaceView, initialize native library, add model for 3d face and model for points on the face
* [ShaderEffectMask](app/src/main/java/com/stoleg/facemask/ShaderEffectMask.java) implements shaders effects with [shaders](app/src/main/assets/shaders)

You could just fork it or clone it and add your own effects.

## Questions?

Just write me. Will glad to hear if it's useful.