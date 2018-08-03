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
uniform vec2 texOffset;

varying vec4 vertColor;
varying vec4 vertTexCoord;

void main(void) {
	vec2 st = gl_FragCoord.st/u_resolution;
	vec2 mid = vec2(0.5);
	vec2 fromMid = vertTexCoord.st - mid;
	float phi = atan(fromMid.y, fromMid.x) + PI;
	float len = length(fromMid);
	
	if (u_mode == 0) { // Cakepiece repetition
		float degPhi = (phi/(2*PI))*360.0;
		float newPhi = mod(degPhi, u_repeatAngle);
		newPhi = (newPhi/360.0)*(2*PI);
		vec2 newFromMid = vec2(len * cos(newPhi), len*sin(newPhi));
		vec2 newVertTexCoord = newFromMid + mid;
		
		if (newVertTexCoord.x > 1.0) {
			float maxLen = length(vec2(0.5-1.0,0.5-newVertTexCoord.y));
			float newLen = maxLen - (len - maxLen);
			newFromMid = vec2(newLen * cos(newPhi), newLen*sin(newPhi));
			newVertTexCoord = newFromMid + mid;
			gl_FragColor = texture2D(texture, newVertTexCoord);
		} else if (newVertTexCoord.x < 0.0 || newVertTexCoord.x > 1.0 || newVertTexCoord.y < 0.0 || newVertTexCoord.y > 1.0)  // out of texture bounds
			gl_FragColor = u_bgColor;
		else
			gl_FragColor = texture2D(texture, newVertTexCoord);
	} else if (u_mode == 1) { // Cakepiece mirror
		float degPhi = (phi/(2*PI))*360.0;
		float newPhi = mod(degPhi, u_repeatAngle*2.0);
		if (newPhi > u_repeatAngle)
			newPhi = u_repeatAngle*2.0 - newPhi;
		newPhi = (newPhi/360.0)*(2*PI);
		vec2 newFromMid = vec2(len * cos(newPhi), len*sin(newPhi));
		vec2 newVertTexCoord = newFromMid + mid;
		
		if (newVertTexCoord.x > 1.0) {
			float maxLen = length(vec2(0.5-1.0,0.5-newVertTexCoord.y));
			float newLen = maxLen - (len - maxLen);
			newFromMid = vec2(newLen * cos(newPhi), newLen*sin(newPhi));
			newVertTexCoord = newFromMid + mid;
			gl_FragColor = texture2D(texture, newVertTexCoord);
		} else if (newVertTexCoord.x < 0.0 || newVertTexCoord.x > 1.0 || newVertTexCoord.y < 0.0 || newVertTexCoord.y > 1.0) {// out of texture bounds
			// we should probably handle y > 1.0
			//gl_FragColor = vec4(1.0,0.0,0.0,1.0);
			gl_FragColor = u_bgColor;
		}else
			gl_FragColor = texture2D(texture, newVertTexCoord);	
	} else { // unknown mode
		// indicate error
		gl_FragColor = vec4(1.0,0.0,0.0,1.0);
	}
}
