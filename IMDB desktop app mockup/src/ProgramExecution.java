import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.File;

/**
 * This class executes UserInterface in a separate class as error handling due to cross-platform
 * issues.
 *
 * @author Ryan Thornton
 * @version 11/17/2022
 */
public class ProgramExecution {

  /**
   * Ground Zero for UserInterface execution.
   *
   * @param args currently not used for anything
   */
  public static void main(String[] args) {
    // Mac users skip this as Mac is not supported for trailer displaying
    if (!SWTJarSelector.getOSName().equals("osx")) {
      // gets the correct SWT for current operating system
      // MAC is currently not supported
      SWTJarSelector jarSelector = new SWTJarSelector();
      File correctSWT = new File(jarSelector.getArchFileName("lib/swt"));
      jarSelector.addJarToClasspath(correctSWT);
      // starts the NativeInterface Used for the Display Trailer Class
      NativeInterface.open();
      // from the java doc "Certain platforms require this method call at the end of the main
      // method to function properly, so it is suggested to always add it".  Even though its ran
      // on a different thread using a SwingWorker, it still freezes the UI unless ran on a
      // completely separate thread... No idea why
      new Thread(NativeInterface::runEventPump);
    }
    // used to create the initial thread for the UI to run on - this is done to stop freezing
    SwingUtilities.invokeLater(() -> {
      // sets UI look and feel to Nimbus
      try {
        // changes theme of Nimbus to match IMDb
        NimbusLookAndFeel laf = new NimbusLookAndFeel();
        UIManager.setLookAndFeel(laf);
        laf.getDefaults().put("defaultFont", new Font("Copperplate Gothic Bold",
            Font.PLAIN, 15));
      } catch (Exception ignored) {
        // if caught, SWING will use the default look and feel
      }
      UserInterface userInterface = new UserInterface();
      userInterface.resetUI();
    });
  }
}