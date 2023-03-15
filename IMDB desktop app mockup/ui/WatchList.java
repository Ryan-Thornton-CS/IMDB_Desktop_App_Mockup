import java.awt.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import com.fasterxml.jackson.databind.*;

import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class WatchList extends JPanel {

  // hashtable of all movies <title, id> in the watch list
  private static final Map<String, String> watchListArray = new HashMap<>();
  // sorted set of all titles in the watch list
  private static final TreeSet<String> watchListTitles = new TreeSet<>();
  // JList that displays the titles from watchListTitles
  private static final JList<String> watchList = new JList<>();
  private final JScrollPane watchListPane = new JScrollPane(watchList);
  private int currentMovieIndex; // this is used for removing and displaying

  private UserInterface containerInterface;

  /**
   * constructor
   */
  public WatchList(UserInterface activeInterface) {
    super();
    this.containerInterface = activeInterface;
    setMaximumSize(new Dimension(275,
        Toolkit.getDefaultToolkit().getScreenSize().height - 20));
    setPreferredSize(new Dimension(275, 40));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // vertical layout, no limit on elements

    // action listeners
    JButton addButton = new JButton("Add");
    addButton.addActionListener(e -> {
      try {
        addButtonPressed();
      } catch (IOException ex) {
        // do nothing
      }
    });
    JButton removeButton = new JButton("Remove");
    removeButton.addActionListener(e -> {
      try {
        removeButtonPressed();
      } catch (IOException ex) {
        // do nothing
      }
    });
    //use this action listener to display the photo when the item in watch list is double-clicked
    mouseListener();
    // layout fuckery to get add the remove buttons on the same line
    JPanel buttonBox = new JPanel();
    buttonBox.setLayout(
        new FlowLayout(FlowLayout.CENTER, 2, 2)); // centered, 2 pixel gap, 2 pixel gap
    buttonBox.setMaximumSize(new Dimension(200, 30)); // cap height
    buttonBox.add(addButton); // add-button on the left
    buttonBox.add(removeButton); // remove-button on the right

    // center the Watch List title
    JPanel titleText = new JPanel();
    titleText.setMaximumSize(new Dimension(200, 20)); // cap height
    titleText.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    titleText.add(new JLabel("Watch List"));

    // add watchList to the scrollpane
    watchListPane.setViewportView(watchList);
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize().getSize();
    watchList.setMaximumSize(new Dimension(275, 2500));
    watchListPane.setPreferredSize(new Dimension(275, size.height - 300));

    // initial construction
    add(titleText); // adds title to the top of the watch list
    add(buttonBox); // adds the add and remove button
    add(watchListPane);
    setVisible(true);
    repaint();

    // get the local info on the watchlist
    load();
    update(0);
  }

  /**
   * Adds mouse listener to the watch list over the list selection listener as to add better
   * functionality.  AKA you now can click once without the worry of loading something by mistake.
   * To load a movie, just double click.
   *
   * @author Ryan Thornton
   */
  private void mouseListener() {
    watchList.addMouseListener(new MouseInputAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          currentMovieIndex = watchList.getSelectedIndex();
          //get the title of the selected movie
          String currmovieTitle = watchList.getModel().getElementAt(currentMovieIndex);
          //find the movie ID based on the title found above, and plug it into
          //the MovieDescription displayWIKI(String ID) method
          String currmovieId = watchListArray.get(currmovieTitle).replace("\"", "")
              .replace("\\", "");
          // resets the movie description class and does this as a background task
          SwingWorker<Void, Void> movieDescriptionWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
              // spaces added because of how original code is wrote
              return containerInterface.updateMovieDescription(currmovieId, currmovieTitle);
            }
          };
          movieDescriptionWorker.execute();
        }
      }
    });
  }

  /**
   * Updates watchListTitles with watchListArray, then
   * updates watchListButtons with watchListTitles.
   *
   * @param state current state of the program, 0 if just
   *              launched and buttons need to be initialized, 1 otherwise
   */
  private void update(int state) {
    // if the file needs to be loaded
    if (state == 0) {
      load();
    }

    // add any missing movies to the title list
    watchListTitles.addAll(watchListArray.keySet());

    watchList.setModel(new AbstractListModel<>() {
      final Object[] titles = watchListTitles.toArray();

      public int getSize() {
        return titles.length;
      }

      public String getElementAt(int i) {
        if (titles.length > 0 && i >= 0 && i < titles.length) {
          return (String) titles[i];
        }
        return null;
      }
    });

    watchList.validate();
    watchList.repaint();
    watchListPane.validate();
    watchListPane.repaint();
    validate();
    repaint();
  }

  /**
   * Action listener for the add button, will add the currently selected movie into the watch list.
   */
  private void addButtonPressed() throws IOException {
    String title =
        containerInterface.getActiveMovieTitle();
    String id = containerInterface.getActiveMovieID();

    // check that there is a current movie
    if (title != null && !(title.equals(" - ") && id.equals(""))) {
      // add the currently selected movie from searchresults to the watchlist array
      watchListArray.put(title, id);
    }
    save();
    update(1); // update the components necessary
  }

  /**
   * Actionlistener for the remove button, will remove the current button & its info
   * from the UI, watchListButtons, watchListTitles, watchListArray, and sets currentButton to null.
   */
  private void removeButtonPressed() throws IOException {
    // get title of selected element
    String toRemove =
        watchList.getModel().getElementAt(currentMovieIndex); // get title of selected element
    watchListArray.remove(toRemove); // remove from the hashmap
    try {
      watchListTitles.remove(toRemove); // remove from the sortedset
      // handles the index out of bounds that I found.
    } catch (NullPointerException e) {
      JOptionPane.showMessageDialog(this,
          "Your watch list is empty.",
          "Watch List Empty", JOptionPane.ERROR_MESSAGE);
    }
    save();
    update(1); // update the componenets necessary which will remove it from the JList
  }

  /**
   * This method saves the information from the watchListArray of movies into the JSON file.
   */
  private void save() {
    ObjectMapper mapper = new ObjectMapper(); // makes mapper
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(
          new File("watchList.json"), watchListArray); // writes the array to to json
    } catch (IOException e) {
      System.out.println("Failed to write WatchList.json");
    }

  }

  /**
   * This method loads the information from the JSON file into the watchListArray of Movies.
   *
   * @author Ryan Thornton
   */
  private void load() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode list = mapper.readTree(new File("watchList.json"));

      Iterator<String> it = list.fieldNames();

      // does uga-buga shit with the jackson mapper to get hashmap values from the json file
      while (it.hasNext()) {
        String next = it.next();
        String value = list.findValue(next).toString();
        watchListArray.put(next, value);
      }
    } catch (IOException e) {
      System.out.println("Failed to load watchlist.json");
    }
  }

  /**
   * Used for testing.
   *
   * @param args N/A
   */
  public static void main(String[] args) {
    JFrame testing = new JFrame();
    UserInterface testingInterface = new UserInterface();
    testing.add(new WatchList(testingInterface));
    testing.setVisible(true);
  }
}