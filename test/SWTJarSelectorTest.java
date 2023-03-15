import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing SWTJarSelector.
 */
class SWTJarSelectorTest {

  private final SWTJarSelector test = new SWTJarSelector();

  /**
   * Tests both getOSName and getArchFileName to the best of its ability.
   *
   * <p>Tests that cannot be done, RuntimeException check for when the osNameProperty would be null. I
   * have no idea of how to set the osNameProperty string in the method of getOSName to null unless
   * making it a class variable.  Same goes for the RuntimeException for having the wrong operating
   * system.  I could only test this if I was using an operating system other than win 64 or mac.
   * lastly, I am unsure of how to test addJarToClasspath as this is past my scope of knowledge.<p/>
   */
  @Test
  void getArchFileNameAndGetOSName() {
    // test for windows operating systems
    if (SWTJarSelector.getOSName().equals("win")) {
      String winPATH = "lib/swt_win_64.jar";
      assertEquals(winPATH, test.getArchFileName("lib/swt"));
      // test for mac operating systems
    } else if (SWTJarSelector.getOSName().equals("osx")) {
      String osxPATH = "lib/swt_osx_64.jar";
      String osxAarchPATH = "lib/swt_osx_aarch64.jar";
      ArrayList<String> PATHs = new ArrayList<>();
      PATHs.add(osxAarchPATH);
      PATHs.add(osxPATH);
      assertTrue(PATHs.contains(test.getArchFileName("lib/swt")));
    }
  }
}