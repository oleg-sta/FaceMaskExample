precision mediump float;        // Set the default precision to medium. We don't need as high of a
// precision in the fragment shader.
uniform sampler2D u_TextureOrig; // original screen texture
uniform sampler2D u_Texture; // mask texture
varying vec2 v_TexCoordinate;
varying vec2 v_TexOrigCoordinate;
uniform float f_alpha; // mask texture

void main()
{
    vec4 maskColor = texture2D(u_Texture, v_TexCoordinate);
    vec4 origColor = texture2D(u_TextureOrig, v_TexOrigCoordinate);
    float alpha =  maskColor[3] * f_alpha;
    if (alpha == 0.0) discard; // solving problem with showing area behind ears
    gl_FragColor = mix(origColor, maskColor, alpha);
}