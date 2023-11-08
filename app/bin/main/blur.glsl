#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;

varying vec4 vertColor;
varying vec4 vertTexCoord;

// Gaussian kernel values (assuming this is a 1-dimensional Gaussian kernel for a 9x9 blur)
float kernel[9] = float[9](0.002, 0.1060, 0.1606, 0.2417, 0.3829, 0.2417, 0.1606, 0.1060, 0.002);
// Converts an RGB color to HSV
vec3 rgb2hsv(vec3 c) {
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

// Converts an HSV color to RGB
vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}


// Function to boost the intensity
float boostIntensity(float value) {
    return clamp(value * value * 15, 0.0, 1.0); // Adjust this function as needed
}

void main() {
   // vec3 hsvSum = vec3(0.0);
    float hue =-1;
    float highestIntensityHue=0.0001;


    float intensitySum=0;
    float totalIntensityWeight = 0;
    float width = 2560.0;
    float widthOfOnePixel = 1.0 / width;

    // Sample 81 pixels around the pixel we care about
    for (int x = -4; x <= 4; x++) {
        for (int y = -4; y <= 4; y++) {
            float coef = kernel[x + 4] * kernel[y + 4];
                  vec2 coords = vec2(
        vertTexCoord.x + float(x) * widthOfOnePixel,  
        vertTexCoord.y + float(y) * widthOfOnePixel);

            vec4 colorSample = texture2D(texture, coords);


            if ((colorSample.x>highestIntensityHue) && (colorSample.z!=0))
            {
            hue =  colorSample.z;
            highestIntensityHue = colorSample.x;
            }
            intensitySum += coef * colorSample.x;
            totalIntensityWeight += coef;
        }
    }
 
 if (hue==-1)
 {
  totalIntensityWeight=0;
 }

    // Boost the intensity (value)
 //   avgHSV.z = boostIntensity(avgHSV.z);

  vec3 avgHSV=vec3(hue,1,1.6* intensitySum/totalIntensityWeight);

    // Convert the average HSV back to RGB
    vec3 rgbColor = hsv2rgb(avgHSV);


    rgbColor = mix(rgbColor, vec3(1.0), clamp((intensitySum-0.6)*2,0,1));

    // Output the final color
    gl_FragColor = vec4(rgbColor, 1.0);
}
