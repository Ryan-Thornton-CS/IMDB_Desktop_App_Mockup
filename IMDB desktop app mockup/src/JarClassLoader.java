import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class handles adding jars to the class path on runtime.
 *
 * @author Ryan Thornton
 * @version 11/16/2022
 */
public class JarClassLoader extends URLClassLoader {

  /**
   * Main constructor.
   */
  public JarClassLoader(String name, ClassLoader parent) {
    super(name, new URL[0], parent);
  }

  /**
   * Second constructor.
   */
  public JarClassLoader(ClassLoader parent) {
    this("classpath", parent);
  }

  /**
   * The constructor we call.
   */
  public JarClassLoader() {
    this(Thread.currentThread().getContextClassLoader());
  }

  /**
   * Adds a class path during runtime.
   *
   * @param url to file to add to class path
   */
  public void add(URL url) {
    this.addURL(url);
  }

  static {
    registerAsParallelCapable();
  }
}
