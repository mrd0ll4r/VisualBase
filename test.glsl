precision highp float;
precision highp int;

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PI 3.14159265358979323846264338327950288

uniform vec2 u_resolution;
uniform int u_mode;
uniform float u_repeatAngle;
uniform vec4 u_bgColor;

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform vec2 texOffset; // (1/width, 1/height), with width and height being the resolution of the texture

varying vec4 vertColor;
varying vec4 vertTexCoord; // only xy, from 0.0 to 1.0

void main(){
    vec2 st = vertTexCoord.st;

    if (st.s < 0.2) {
        st.t += 0.1;
    } else if (st.s < 0.4) {
        st.t += 0.2;
    } else if (st.s < 0.6) {
        st.t += 0.3;
    } else if (st.s < 0.8) {
        st.t += 0.4;
    } else {
        st.t += 0.5;
    }

    if (st.t > 1.0) {
        st.t -= 1.0;
    } else if (st.t < 0.0) {
        st.t += 1.0;
    }

    gl_FragColor = texture2D(texture,st) * vertColor;
}