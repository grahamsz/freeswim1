#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;

varying vec4 vertColor;
varying vec4 vertTexCoord;

void main() {
  //gl_FragColor = texture2D(texture, vertTexCoord.st) * vertColor;


  float width = 3840;
  
  // Note that when using floats here we need to always use the decimal point.
  // If we tried "1/width" here it would fail
  float widthOfOnePixel=1.0/width;

  vec4 combinedColor=vec4(0);
  
  // Let's start by doing some kind of blur, not a real guassian kernel, but close enough
  
  
  // we build a 9x9 kernel here and sample 81 pixels around the pixel we care about
   for (int x = -2; x <= +2; x++) {
    for (int y = -2; y <= +2; y++) {
      
      float coef = 1.0;
      // more convolution, same set of hand-picked coefficeints
      if ((x==-4) || (x==4)) { coef *=0.25;}
      if ((x==-3) || (x==3)) { coef*=0.26;}
      if ((x==-2) || (x==2)) { coef*=0.27;}
      if ((x==-1) || (x==1)) { coef*=0.34;}
      if ((y==-4) || (y==4)) { coef *=0.25;}
      if ((y==-3) || (y==3)) { coef*=0.26;}
      if ((y==-2) || (y==2)) { coef*=0.27;}
      if ((y==-1) || (y==1)) { coef*=0.34;}



      // The texture2D function requires we index based on the 0..1 coordinate space, but
      // we passed in the width from p5 so we can calculate the difference between the pixels
      
      vec2 coords = vec2(
        vertTexCoord.x + float(x) * widthOfOnePixel,  
        vertTexCoord.y + float(y) * widthOfOnePixel);
      
      // we use the texture2D function to get the pixel color from the underlying p5 graphics
      // we can use vector arithmetic to multiply it by our kernel
      combinedColor += (coef *0.4) * texture2D(texture, coords);

    }

  }

  
  // Finally add the scan-line effect, b
  int scanLine = int(vertTexCoord.y * (width/1.5));

  
  if (scanLine - (scanLine / 2) * 2 == 0) {
    combinedColor = combinedColor /2.0; //half the color for the scan line gaps
  }

  
  
  gl_FragColor =combinedColor;

}