import java.io.File;
import java.net.URL;

/**
 * This class helps add the correct SWT to project
 *
 * @author Ryan Thornton
 * @version 11/16/2022
 */
public class SWTJarSelector {

  /**
   * Returns the correct file path for the SWT file.  WIN 64 / MAC64 / MAC aarch64.
   *
   * @return String of the correct SWT jar
   */
  public String getArchFileName(String prefix) {
    return prefix + "_" + getOSName() + "_" + this.getArchName() + ".jar";
  }

  /**
   * Gets the current OS's name.
   *
   * @return String of the correct OS
   */
  public static String getOSName() {
    String osNameProperty = System.getProperty("os.name");
    if (osNameProperty == null) {
      throw new RuntimeException("os.name property is not set");
    } else {
      osNameProperty = osNameProperty.toLowerCase();
      if (osNameProperty.contains("win")) {
        return "win";
      } else if (osNameProperty.contains("mac")) {
        return "osx";
      } else {
        throw new RuntimeException("Unknown OS name: " + osNameProperty);
      }
    }
  }

  /**
   * Gets your operating system's arch.
   *
   * @return string of arch
   */
  private String getArchName() {
    String osArch = System.getProperty("os.arch");
    if (osArch != null && osArch.contains("aarch64")) {
      return "aarch64";
    } else if (osArch != null && osArch.contains("64")) {
      return "64";
    } else {
      throw new RuntimeException("32 bit not supported.");
    }
  }

  /**
   * Adds correct SWT jar before launch of UI.
   *
   * @param jarFile the correct SWT jar for the current operating system
   */
  public void addJarToClasspath(File jarFile) {
    try {
      JarClassLoader loader = new JarClassLoader();
      URL url = jarFile.toURI().toURL();
      loader.add(url);
    } catch (Throwable var4) {
      var4.printStackTrace();
    }
  }
}
