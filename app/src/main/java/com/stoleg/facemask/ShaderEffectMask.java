package com.stoleg.facemask;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.stoleg.vrface.commonlib.Settings;
import com.stoleg.vrface.renderer.ShaderEffect;
import com.stoleg.vrface.renderer.ShaderEffectHelper;
import com.stoleg.vrface.utils.FileUtils;
import com.stoleg.vrface.utils.OpenGlHelper;
import com.stoleg.vrface.utils.PointsConverter;
import com.stoleg.vrface.utils.PoseHelper;
import com.stoleg.vrface.utils.ShaderUtils;

public class ShaderEffectMask extends ShaderEffect {

    private static final String TAG = "ShaderEffect";

    private int vPos3d;
    private int vTexFor3d;

    // shaders programs
    int noEffectProgramId;
    int noFaceProgramId;
    int particleProgramId;
    int _3DProgramId;

    private int maskTextureid;


    public ShaderEffectMask(Context contex) {
        super(contex);
    }

    public void init() {
        super.init();
        int vertexShaderId = ShaderUtils.createShader(GLES20.GL_VERTEX_SHADER, FileUtils.getStringFromAsset(context.getAssets(), "shaders/vss_2d.glsl"));
        int fragmentNoEffectShaderId = ShaderUtils.createShader(GLES20.GL_FRAGMENT_SHADER, FileUtils.getStringFromAsset(context.getAssets(), "shaders/fss_2d_simple.glsl"));
        int fragmentNoFaceShaderId = ShaderUtils.createShader(GLES20.GL_FRAGMENT_SHADER, FileUtils.getStringFromAsset(context.getAssets(), "shaders/fss_no_face.glsl"));
        int fragmentParticleShaderId = ShaderUtils.createShader(GLES20.GL_FRAGMENT_SHADER, FileUtils.getStringFromAsset(context.getAssets(), "shaders/fss_particle.glsl"));
        noFaceProgramId = ShaderUtils.createProgram(vertexShaderId, fragmentNoFaceShaderId);
        noEffectProgramId = ShaderUtils.createProgram(vertexShaderId, fragmentNoEffectShaderId);
        particleProgramId = ShaderUtils.createProgram(vertexShaderId, fragmentParticleShaderId);

        maskTextureid = OpenGlHelper.loadTexture(context, R.raw.simple_mask);


        int vertex3d = ShaderUtils.createShader(GLES20.GL_VERTEX_SHADER, FileUtils.getStringFromAsset(context.getAssets(), "shaders/vss3d.glsl"));
        int fragment3d = ShaderUtils.createShader(GLES20.GL_FRAGMENT_SHADER, FileUtils.getStringFromAsset(context.getAssets(), "shaders/fss3d.glsl"));
        _3DProgramId = ShaderUtils.createProgram(vertex3d, fragment3d);
        vPos3d = GLES20.glGetAttribLocation(_3DProgramId, "vPosition");
        GLES20.glEnableVertexAttribArray(vPos3d);
        vTexFor3d = GLES20.glGetAttribLocation(_3DProgramId, "a_TexCoordinate");
        GLES20.glEnableVertexAttribArray(vTexFor3d);
    }

    @Override
    public void makeShaderMask(int indexEye, PoseHelper.PoseResult poseResult, int width, int height, int texIn, long time, int iGlobTime) {
        Log.i(TAG, "poseResult " + poseResult.foundFeatures + " ");
        int vPos = GLES20.glGetAttribLocation(noEffectProgramId, "vPosition");
        int vTex = GLES20.glGetAttribLocation(noEffectProgramId, "vTexCoord");
        GLES20.glEnableVertexAttribArray(vPos);
        GLES20.glEnableVertexAttribArray(vTex);
        if (poseResult.foundLandmarks != null) {
            int effectNum = 2;
            Log.i(TAG, "point 0 x " + poseResult.foundLandmarks[0].x + " " +  poseResult.foundLandmarks[0].y);
            if (effectNum == 0) {
                ShaderEffectHelper.shaderEffect2dWholeScreen(poseResult, texIn, noEffectProgramId, vPos, vTex, width, height);
            } else if (effectNum == 1) {
                int vPos2 = GLES20.glGetAttribLocation(particleProgramId, "vPosition");
                GLES20.glEnableVertexAttribArray(vPos2);
                ShaderEffectHelper.effect2dParticle(0, 0, particleProgramId, vPos2, PointsConverter.convertFromPointsGlCoord(poseResult.foundLandmarks, width, height), new float[]{1, 1, 1});
            } else if (effectNum == 2) {
                ShaderEffectHelper.shaderEffect2dWholeScreen(poseResult.leftEye, poseResult.rightEye, texIn, noEffectProgramId, vPos, vTex);

                GLES20.glEnable(GLES20.GL_DEPTH_TEST);
                GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);

                int vTexOrtho = GLES20.glGetAttribLocation(_3DProgramId, "vTexCoordOrtho");
                GLES20.glEnableVertexAttribArray(vTexOrtho);
                ShaderEffectHelper.shaderEffect3d2(PoseHelper.convertToArray(poseResult.glMatrix), texIn, width, height, model, maskTextureid, 0.5f, _3DProgramId, vPos3d, vTexFor3d, PointsConverter.convertFromProjectedTo2dPoints(poseResult.projected, width, height), vTexOrtho, Settings.flagOrtho, poseResult.initialParams);
                GLES20.glDisable(GLES20.GL_DEPTH_TEST);

            }

        } else {
            // should be deprecated
            ShaderEffectHelper.shaderEffect2dWholeScreen(poseResult.leftEye, poseResult.rightEye, texIn, noFaceProgramId, vPos, vTex);
        }
    }
}
