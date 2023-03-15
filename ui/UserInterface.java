import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;

/**
 * This class drives the UI of our IMDb Movie application.
 *
 * @version 11/3/2022
 * @authors Abdullah Mohammad Ali / Ryan Thornton / Jonah Giblin / Hudson
 * Shaeffer
 */
public class UserInterface extends JFrame {

  private final JScrollPane left;
  private final JPanel right;
  private final Container contentPane;
  private final JPanel bottom = new JPanel();
  private String activeMovieID;
  private String activeMovieTitle;
  private HomeScreen homeScreen;
  private final NextPrev nextprev;
  private final JPanel north;

  private JButton trailerButton;

  private MovieDescription activeMovieDescription;

  /**
   * This is the main constructor of UserInterface and drives the whole UI,
   * this defines the layout, sizing, and location of the subcomponents.
   * METHOD IS DEPRECATED FOR STARTING APPLICATION, USE RUN METHOD.
   */
  public UserInterface() {
    // Set layout and close operation for frame
    contentPane = getContentPane();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    // creates north panel
    north = new JPanel(new BorderLayout());
    contentPane.add(north, BorderLayout.NORTH);

    // Adds searchBar into North
    SearchBar searchBar = new SearchBar(this);
    north.add(searchBar, BorderLayout.SOUTH);

    // West side JScrollPane and JPanel for WIKI/Description/HomePage
    // Swing worker object used to load homeScreen in the background
    SwingWorker<JPanel, Void> homeScreenWorker = new SwingWorker<>() {
      @Override
      protected HomeScreen doInBackground() throws IOException {
        return new HomeScreen(UserInterface.this);
      }

      @Override
      protected void done() {
        try {
          homeScreen = doInBackground();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        left.setViewportView(homeScreen);
        doneLoading();
      }
    };

    // Initalizing NextPrev object
    this.nextprev = new NextPrev(this);
    // dummy JScrollPane until homeScreen loads
    left = new JScrollPane();
    // sets the vertical scroll speed
    left.getVerticalScrollBar().setUnitIncrement(20);
    // shows that the homeScreen is loading and executes its calling
    loading();
    homeScreenWorker.execute();
    // Right side JPanel ALWAYS the watchList, buttons added for i/o
    right = new WatchList(this);
    // adds west and east to bottom JPanel
    bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
    bottom.add(left);
    bottom.add(right);
    // Adds Bottom JPanel to main frame
    contentPane.add(bottom, BorderLayout.CENTER);

    // makes top menu bar with settings/home/display trailer/next / previous
    makeMenuBar();
    makeButtons();

    // Settings for the window go below
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    // DIMENSIONS BIGGER THAN THE PREFERRED LOOK WEIRD AF, PLEASE DON'T
    // CHANGE
    if (size.width < 1675) {
      contentPane.setPreferredSize(new Dimension(size.width - 200, size.height - 200));
    } else {
      contentPane.setPreferredSize(new Dimension(1675, size.height - 200));
    }
    setVisible(true);
    contentPane.setVisible(true);
    pack();
    // used to center the panel on the screen
    setLocationRelativeTo(null);
    resetUI();
  }

  /**
   * Creates the top settings panel.
   *
   * @author Ryan Thornton
   */
  private void makeMenuBar() {
    // Makes main menu bar
    JMenuBar menu = new MenuBar(this);
    // adds menu to north layout of JFrame
    north.add(menu, BorderLayout.NORTH);
  }

  /**
   * Creates all Buttons, with size, listeners, etc.
   *
   * @author Ryan Thornton
   */
  private void makeButtons() {
    // home button
    JButton homeButton = new JButton("Home");
    homeButton.setMaximumSize(new Dimension(160, 50));
    homeButton.setPreferredSize(new Dimension(160, 50));
    homeButton.addActionListener(event -> homeListener());
    homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    right.add(homeButton);

    // trailer button
    trailerButton = new JButton("Watch Trailer");
    trailerButton.setPreferredSize(new Dimension(160, 50));
    trailerButton.setMaximumSize(new Dimension(160, 50));
    trailerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    right.add(trailerButton);
    trailerButton.addActionListener(event -> {
      // handles displayTrailer exceptions from URI and Desktop classes
      try {
        displayTrailer(activeMovieID);
      } catch (IOException | URISyntaxException e) {
        JOptionPane.showMessageDialog(contentPane,
            "Sorry, but there was an error opening your movies trailer.  Please try again.",
            "Trailer Display", JOptionPane.ERROR_MESSAGE);
      }
    });
    trailerButton.setEnabled(false);
  }

  private void homeListener() {
    left.setViewportView(homeScreen);
    homeScreen.removeBorderAll(new JPanel());
    activeMovieTitle = "";
    activeMovieID = "";
    trailerButton.setEnabled(false);
    nextprev.add(activeMovieDescription);
    activeMovieDescription = null;
  }


  /**
   * Visualizes the fact that something is loading, both mouse and Image.
   *
   * @author Ryan Thornton
   */
  public ImageIcon loading() {
    contentPane.setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
    return new ImageIcon("lib/classic-film-reel-countdown.gif");
  }

  /**
   * Called when loading is done.
   *
   * @author Ryan Thornton
   */
  public void doneLoading() {
    contentPane.setCursor(
        (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)));
  }

  /**
   * Displays a trailer in a separate frame, given an id.
   *
   * @author Ryan Thornton
   */
  public void displayTrailer(String currentMovieId) throws IOException, URISyntaxException {
    if (SWTJarSelector.getOSName().equals("osx")) {
      // checks to make sure the Desktop class is support on this platform and getting the
      // default browser is support as well
      if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(new URI(new DisplayTrailerMac().trailerLink(currentMovieId)));
      } else {
        // displayed if Desktop is not supported
        JOptionPane.showMessageDialog(contentPane,
            "Sorry, but this feature is currently not available for your platform.",
            "Trailer Display", JOptionPane.ERROR_MESSAGE);
      }
      return;
    }
    SwingWorker<Object, Void> displayTrailerWorker = new SwingWorker<>() {
      @Override
      protected DisplayTrailer doInBackground() throws Exception {
        return new DisplayTrailer(currentMovieId);
      }

      @Override
      protected void done() {
        try {
          DisplayTrailer trailer = doInBackground();
          JOptionPane.showMessageDialog(contentPane, trailer,
              "Trailer Display", JOptionPane.PLAIN_MESSAGE);
          trailer.webBrowser.disposeNativePeer();
          trailer.webBrowser.stopLoading();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(contentPane,
              "There are no available trailers for this movie.",
              "No Trailer Available", JOptionPane.ERROR_MESSAGE);
        }
      }
    };
    // executes the SwingWorker
    displayTrailerWorker.execute();
  }

  /**
   * This resets the UI when changes are made.
   *
   * @author Ryan Thornton
   */
  public void resetUI() {
    north.validate();
    north.repaint();
    left.revalidate();
    left.repaint();
    right.revalidate();
    right.repaint();
    bottom.revalidate();
    bottom.repaint();
    contentPane.revalidate();
    contentPane.repaint();
  }

  /**
   * Primary updater for a MovieDescription, will replace the left panel with the relevant id's
   * MovieDescription.
   *
   * @authors Jonah Giblin, Ryan Thornton, Abdullah Mohammad Ali
   *
   * @param id inputted id to generate the MovieDescription based off.
   * @param newTitle inputted title that corresponds to the MovieDescription instance.
   *
   * @return custom Void return to properly accommodate the SwingWorker.
   */
  public Void updateMovieDescription(String id, String newTitle) {
    if (activeMovieDescription != null) {
      nextprev.add(activeMovieDescription);
    }
    // Holds the current size of the history list
    activeMovieID = id;
    activeMovieTitle = newTitle;
    trailerButton.setEnabled(true);
    activeMovieDescription = new MovieDescription(id, newTitle, this);
    left.setViewportView(activeMovieDescription);
    bottom.removeAll();
    bottom.add(left);
    bottom.add(right);
    activeMovieDescription.visualize();
    resetUI();
    return null;
  }

  /**
   * Loads a MovieDescription from a stored location in memory, currently only used by NextPrev.
   *
   * @author Jonah Giblin
   * @param prevNextLoaded the MovieDescription instance to load.
   */
  public void updateMovieDescription(MovieDescription prevNextLoaded) {
    activeMovieID = prevNextLoaded.getID();
    activeMovieTitle = prevNextLoaded.getTitle();
    activeMovieDescription = prevNextLoaded;
    left.setViewportView(prevNextLoaded);
    trailerButton.setEnabled(true);
  }

  public String getActiveMovieID() {
    return activeMovieID;
  }

  public String getActiveMovieTitle() {
    return activeMovieTitle;
  }

  public void homeScreenSelection(String newMovieTitle, String newMovieID) {
    activeMovieID = newMovieID;
    activeMovieTitle = newMovieTitle;
    trailerButton.setEnabled(true);
  }

  /**
   * Gets the current nextprev instance.
   *
   * @return nextprev instance
   */
  public NextPrev getNextPrev() {
    return nextprev;
  }

  /**
   * Gets the active MovieDescription instance. NO checkers for null, must be handled at receiving.
   * @return the currently active MovieDescription instance.
   *
   * @author Jonah Giblin
   */
  public MovieDescription getActiveMovieDescription() {
    return activeMovieDescription;
  }
}
