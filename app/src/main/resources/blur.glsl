#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;

varying vec4 vertColor;
varying vec4 vertTexCoord;


vec3 hsv2rgb(vec3 c) {
  vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
  vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
  return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float boostIntensity(float value) {

    // Clamp the value to make sure it doesn't exceed 1.0 due to numerical issues.
    return clamp(value*value*10, 0.0, 6.0);
}


  float kernel[9] = float[9](0.0002,	0.0060,	0.0606,	0.2417,	0.3829,	0.2417,	0.0606,	0.0060,	0.0002);

void main() {
  //gl_FragColor = texture2D(texture, vertTexCoord.st) * vertColor;


  float width = 3840;
  
  // Note that when using floats here we need to always use the decimal point.
  // If we tried "1/width" here it would fail
  float widthOfOnePixel=1.0/width;

  vec4 combinedColor=vec4(0);
  
  // Let's start by doing some kind of blur, not a real guassian kernel, but close enough
  
  
  // we build a 5x5 kernel here and sample 81 pixels around the pixel we care about
   for (int x = -2; x <= +2; x++) {
    for (int y = -2; y <= +2; y++) {
      
    float coef = kernel[x + 4] * kernel[y + 4];
    
      // The texture2D function requires we index based on the 0..1 coordinate space, but
      // we passed in the width from p5 so we can calculate the difference between the pixels
      
      vec2 coords = vec2(
        vertTexCoord.x + float(x) * widthOfOnePixel,  
        vertTexCoord.y + float(y) * widthOfOnePixel);
      
      // we use the texture2D function to get the pixel color from the underlying p5 graphics
      // we can use vector arithmetic to multiply it by our kernel
      combinedColor += coef * texture2D(texture, coords);

    }

  }

  
  // After the loop, convert the sampled color
  vec4 colorSample = combinedColor;
  
  // Use the red channel for intensity (value) and blue channel for hue
  float hue = colorSample.b; // Assuming hue is in 0.0 - 1.0 range
  float saturation = 1.0; // Full saturation
  float value = boostIntensity(colorSample.r); // Use the red channel for intensity
  
  vec3 hsvColor = vec3(hue, saturation, value);
  vec3 rgbColor = hsv2rgb(hsvColor);
  
  gl_FragColor = vec4(rgbColor, 1.0); // Assuming alpha is always 1.0



  

}