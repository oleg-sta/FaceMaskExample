package com.stoleg.facemask;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.stoleg.vrface.CompModel;
import com.stoleg.vrface.ModelLoaderTask;
import com.stoleg.vrface.Static;
import com.stoleg.vrface.camera.FastCameraView;
import com.stoleg.vrface.commonlib.Settings;
import com.stoleg.vrface.renderer.MaskRenderer;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends Activity implements ModelLoaderTask.Callback, MaskRenderer.Callback {

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    TypedArray eyesResourcesSmall;

    static CompModel compModel;
    FastCameraView cameraView;

    private static final String TAG = "ActivityFast";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("detection_based_tracker");

                    Static.libsLoaded = true;
                    // load cascade file from application resources
                    Log.e(TAG, "findLandMarks onManagerConnected");
                    compModel.loadHaarModel(Static.resourceDetector[0]);
                    compModel.load3lbpModels(R.raw.lbp_frontal_face, R.raw.lbp_left_face, R.raw.lbp_right_face);
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public static GLSurfaceView gLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_view);

        if (shouldAskPermissions()) {
            askPermissions();
        }
        cameraView = findViewById(R.id.fd_fase_surface_view);

        if (!Static.libsLoaded) {
            compModel = new CompModel();
            compModel.context = getApplicationContext();
        }

        gLSurfaceView = findViewById(R.id.fd_glsurface);
        gLSurfaceView.setEGLContextClientVersion(2);
        gLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        gLSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        final MaskRenderer meRender = new MaskRenderer(this, compModel, new ShaderEffectMask(this), this);
        gLSurfaceView.setRenderer(meRender);
        gLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        meRender.frameCamera = cameraView.frameCamera;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        final SharedPreferences prefs = getSharedPreferences(Settings.PREFS, Context.MODE_PRIVATE);
        prefs.edit().putString(Settings.MODEL_PATH, "/storage/emulated/0/shape_predictor_68_face_landmarks.dat2").apply();
        if (!Static.libsLoaded) {
            OpenCVLoader.initDebug();
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            new ModelLoaderTask(this).execute(compModel);
        } else {
        }
        gLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
        gLSurfaceView.onPause();
        cameraView.disableView();
    }

    @Override
    public void onModelLoaded() {
    }

    @Override
    public void photoSaved() {

    }
}