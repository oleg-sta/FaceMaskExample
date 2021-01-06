attribute vec3 vPosition;
attribute vec2 a_TexCoordinate;
attribute vec2 vTexCoordOrtho;
varying vec2 v_TexCoordinate;
varying vec2 v_TexOrigCoordinate;
uniform mat4 u_MVPMatrix;// A constant representing the combined model/view/projection matrix
uniform int ss;

uniform mat3 u_OrthoMatrix;// A constant representing the combined model/view/projection matrix
uniform float s;
uniform vec3 t;
uniform vec2 wid;

void main() {
    v_TexCoordinate = a_TexCoordinate;
    gl_Position = u_MVPMatrix * vec4(vPosition, 1.0);
    v_TexOrigCoordinate = gl_Position.xy;
    if (ss > 0) {
        vec3 rr = u_OrthoMatrix * vPosition * s + t;
        //vec3 rr = vPosition * s + t;
        gl_Position.x = (rr.x / wid.x * 2.0 - 1.0) * gl_Position.z;
        gl_Position.y = (1.0 - rr.y / wid.y * 2.0) * gl_Position.z;
    }
    v_TexOrigCoordinate = gl_Position.xy;
    float z = 1.0;
    if (gl_Position.z != 0.0) { z = 1.0 / gl_Position.z; };
    v_TexOrigCoordinate.x = (v_TexOrigCoordinate.x * z + 1.0) / 2.0;
    v_TexOrigCoordinate.y = (v_TexOrigCoordinate.y * z + 1.0) / 2.0;
    gl_PointSize = 10.0;
}