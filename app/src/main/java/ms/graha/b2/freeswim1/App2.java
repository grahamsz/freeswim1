package ms.graha.b2.freeswim1;
import org.jocl.*;
import static org.jocl.CL.*;

public class App2 {
    /**
     * The entry point of this sample
     *
     * @param args Not used
     */
    public static void main(String[] args) {
        // Initialize the OpenCL context
        CL.setExceptionsEnabled(true);

        // Obtain the number of platforms
        int[] numPlatformsArray = new int[1];
        clGetPlatformIDs(0, null, numPlatformsArray);
        int numPlatforms = numPlatformsArray[0];

        // Obtain the platform IDs
        cl_platform_id[] platforms = new cl_platform_id[numPlatforms];
        clGetPlatformIDs(platforms.length, platforms, null);

        // Iterate over the platforms
        for (cl_platform_id platform : platforms) {
            // Print the platform name
        //    String platformName = getString(platform, CL_PLATFORM_NAME);
         //   System.out.println("Platform: " + platformName);

            // Obtain the number of devices for the current platform
            int[] numDevicesArray = new int[1];
            clGetDeviceIDs(platform, CL_DEVICE_TYPE_ALL, 0, null, numDevicesArray);
            int numDevices = numDevicesArray[0];

            // Obtain the device IDs
            cl_device_id[] devices = new cl_device_id[numDevices];
            clGetDeviceIDs(platform, CL_DEVICE_TYPE_ALL, numDevices, devices, null);

            // Iterate over all devices
            for (cl_device_id device : devices) {
                // Print the device name
                String deviceName = getString(device, CL_DEVICE_NAME);
                System.out.println("Device: " + deviceName);
            }
        }
    }

  /**
     * Returns the value of the device info parameter with the given name
     *
     * @param device The device
     * @param paramName The parameter name
     * @return The value
     */
    private static String getString(cl_device_id device, int paramName) {
        // Obtain the length of the string that will be queried
        long[] size = new long[1];
        clGetDeviceInfo(device, paramName, 0, null, size);

        // Create a buffer of the appropriate size and fill it with the info
        byte[] buffer = new byte[(int)size[0]];
        clGetDeviceInfo(device, paramName, buffer.length, Pointer.to(buffer), null);

        // Create a string from the buffer (excluding the trailing \0 byte)
        return new String(buffer, 0, buffer.length - 1);
    }
}

