import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Used to create the UserInterface's menu bar.
 *
 * @author Ryan Thornton
 * @version 12/8/2022
 */
public class MenuBar extends JMenuBar {

  private final UserInterface userInterface;

  /**
   * Constructor for MenuBar.
   *
   * @param userInterface object to use
   */
  public MenuBar(UserInterface userInterface) {
    // Makes main menu bar
    super();
    this.userInterface = userInterface;

    //creation of menuBar
    // Makes menu bars drop-downs and the menus/menu items within them
    // start of settings
    JMenu settings = new JMenu("Settings");
    JMenu colorScheme = new JMenu("Color Themes");
    JMenuItem defaultMode = new JMenuItem("Default Theme");
    JMenuItem darkMode = new JMenuItem("Dark Theme");
    JMenuItem imdb = new JMenuItem("IMDb Theme");
    JMenuItem jmu = new JMenuItem("JMU Theme");
    colorScheme.add(defaultMode);
    colorScheme.add(darkMode);
    colorScheme.add(imdb);
    colorScheme.add(jmu);
    // adds colorScheme to settings
    settings.add(colorScheme);
    // end of settings
    add(settings);
    // lets previous and next be to the far right of the menu bar
    add(Box.createHorizontalGlue());
    add(userInterface.getNextPrev());
    // makes the action listeners for menu
    menuListener(defaultMode, darkMode, imdb, jmu);
  }

  /**
   * Used to handle home menu events.
   *
   * @param home           menu item
   * @param displayTrailer menu item
   * @param darkMode       theme
   * @param imdb           theme
   * @param jmu            theme
   * @param defaultMode    theme
   * @author Ryan Thornton
   */
  private void menuListener(JMenuItem defaultMode,
                            JMenuItem darkMode, JMenuItem imdb, JMenuItem jmu) {
    // used to handle actions for colorScheme menu items
    // Default LAF - this will reset the UI
    defaultMode.addActionListener(e -> {
      UIManager.put("control", new Color(214, 217, 223));
      UIManager.put("info", new Color(242, 242, 189));
      UIManager.put("nimbusBase", new Color(51, 98, 140));
      UIManager.put("nimbusAlertYellow", new Color(255, 220, 35));
      UIManager.put("nimbusDisabledText", new Color(142, 143, 145));
      UIManager.put("nimbusFocus", new Color(115, 164, 209));
      UIManager.put("nimbusGreen", new Color(176, 179, 50));
      UIManager.put("nimbusInfoBlue", new Color(47, 92, 180));
      UIManager.put("nimbusLightBackground", new Color(255, 255, 255));
      UIManager.put("nimbusOrange", new Color(191, 98, 4));
      UIManager.put("nimbusRed", new Color(169, 46, 34));
      UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
      UIManager.put("nimbusSelectionBackground", new Color(57, 105, 138));
      UIManager.put("text", new Color(0, 0, 0));
      UIManager.put("nimbusBorder", new Color(146, 151, 161));
      SwingUtilities.updateComponentTreeUI(this);
      userInterface.resetUI();
    });
    // Dark Mode LAF
    darkMode.addActionListener(e -> {
      UIManager.put("control", new Color(128, 128, 128));
      UIManager.put("info", new Color(128, 128, 128));
      UIManager.put("nimbusBase", new Color(18, 30, 49));
      UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
      UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
      UIManager.put("nimbusFocus", new Color(115, 164, 209));
      UIManager.put("nimbusGreen", new Color(176, 179, 50));
      UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
      UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
      UIManager.put("nimbusOrange", new Color(191, 98, 4));
      UIManager.put("nimbusRed", new Color(169, 46, 34));
      UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
      UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
      UIManager.put("text", new Color(230, 230, 230));
      UIManager.put("nimbusBorder", new Color(0, 0, 0));
      SwingUtilities.updateComponentTreeUI(this);
      userInterface.resetUI();
    });
    // IMDb mode LAF
    imdb.addActionListener(e -> {
      UIManager.put("control", new Color(219, 165, 6));
      UIManager.put("info", new Color(242, 219, 131));
      UIManager.put("nimbusBase", new Color(50, 50, 50));
      UIManager.put("nimbusAlertYellow", new Color(219, 165, 6));
      UIManager.put("nimbusDisabledText", new Color(0, 0, 0));
      UIManager.put("nimbusFocus", new Color(0, 0, 0));
      UIManager.put("nimbusGreen", new Color(176, 179, 50));
      UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
      UIManager.put("nimbusLightBackground", new Color(242, 219, 131));
      UIManager.put("nimbusOrange", new Color(191, 98, 4));
      UIManager.put("nimbusRed", new Color(169, 46, 34));
      UIManager.put("nimbusSelectedText", new Color(242, 219, 131));
      UIManager.put("nimbusSelectionBackground", new Color(50, 50, 50));
      UIManager.put("text", new Color(0, 0, 0));
      UIManager.put("nimbusBorder", new Color(0, 0, 0));
      SwingUtilities.updateComponentTreeUI(this);
      userInterface.resetUI();
    });
    // JMU mode LAF
    jmu.addActionListener(e -> {
      UIManager.put("control", new Color(181, 153, 206));
      UIManager.put("info", new Color(203, 182, 119));
      UIManager.put("nimbusBase", new Color(203, 182, 119));
      UIManager.put("nimbusAlertYellow", new Color(50, 10, 125));
      UIManager.put("nimbusDisabledText", new Color(0, 0, 0));
      UIManager.put("nimbusFocus", new Color(0, 0, 0));
      UIManager.put("nimbusGreen", new Color(176, 179, 50));
      UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
      UIManager.put("nimbusLightBackground", new Color(242, 219, 131));
      UIManager.put("nimbusOrange", new Color(191, 98, 4));
      UIManager.put("nimbusRed", new Color(169, 46, 34));
      UIManager.put("nimbusSelectedText", new Color(242, 219, 131));
      UIManager.put("nimbusSelectionBackground", new Color(50, 50, 50));
      UIManager.put("text", new Color(0, 0, 0));
      UIManager.put("nimbusBorder", new Color(0, 0, 0));
      SwingUtilities.updateComponentTreeUI(this);
      userInterface.resetUI();
    });
  }
}
