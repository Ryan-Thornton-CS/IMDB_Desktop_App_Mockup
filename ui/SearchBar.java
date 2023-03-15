import com.fasterxml.jackson.databind.JsonNode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the Search Bar component for the JMDB Project. This class
 * contains the UI elements as well as linking to the functionality classes
 * for each subcomponent that requires it.
 *
 * @version 11/02/2020
 * @authors Ryan Thornton / Abdullah Mohammad Ali / Hudson Schaeffer.
 */
public class SearchBar extends JPanel {

  // Static Vars
  // TODO add searchName once actorDescription is completed
  private static final String[] SEARCH_TYPES = {"Movies", "Titles", "TV Series", "Advanced Search"};
  // the 4 below represent the currently selected result for ease of display.
  private String currentMovieDescription = "";
  private String currentMovieImageLink = "";
  private String currentMovieTitle = "";
  private String currentMovieId = "";
  // Attribute Vars
  private JsonNode currentNode; // node returned by API query

  // Final Vars
  // Text box for search entry
  private final JTextField textInput = new JTextField("");
  // Dropdown menu for search types
  private final JComboBox<String> searchType = new JComboBox<>(SEARCH_TYPES);
  // the array the searchResultsComboBox is built off of
  private final ArrayList<String> searchResultsRefined = new ArrayList<>();
  // Dropdown for search results
  private final JComboBox<String> searchResultsComboBox = new JComboBox<>();
  private String input = ""; // what the user typed in the search bar
  private final ArrayList<String> filter = new ArrayList<>();
  // selected SEARCH_TYPES at time of search DEFAULT IS MOVIES

  private final UserInterface containingInterface;

  /**
   * Constructor for search bar, this defines the size, layouts, and
   * internal components present in the search bar.
   *
   * @authors Hudson Shaeffer / Ryan Thornton
   */
  public SearchBar(UserInterface container) {
    super();
    containingInterface = container;
    setName("Search");
    // makes the first index Movies because it won't work properly on startup without
    filter.add("Movies");

    setMinimumSize(new Dimension(800, 100)); // starting size of the window
    setLocation(100, 150); // starting location on screen
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // vertical stack to hold the subPanels

    JPanel topPanel = new JPanel(); // this subPanel will hold input and submission button
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    JPanel bottomPanel = new JPanel(); // this subPanel will hold results and search type
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

    // action listener for search button, search bar button, and filter drop down
    // to submit search query
    JButton submission = new JButton("Search");
    submission.addActionListener(e -> submissionButtonPressed());
    textInput.addActionListener(e -> submissionButtonPressed());
    searchType.addActionListener(e -> filterSelected());

    // SearchResults Panel
    JPanel searchResults = new JPanel(new BorderLayout());
    searchResults.setMinimumSize(new Dimension(textInput.getWidth(), textInput.getHeight()));
    searchResults.add(searchResultsComboBox, BorderLayout.CENTER);

    // populate subPanels
    topPanel.add(textInput);
    topPanel.add(submission);
    bottomPanel.add(searchResults);
    bottomPanel.add(searchType);

    // populate main panel with subPanels
    add(topPanel);
    add(bottomPanel);

    // panel settings
    setVisible(true);
    repaint();
  }

  /**
   * This will be used to call {@link BackEndAPIQuery#searchResults(String, ArrayList)}, which will
   * then query the information needed using the users inputted search terms.  Then a JPanel with a
   * JComboBox displaying the search results and an action listener updating a class attribute
   * String that will be to display movie description.
   *
   * @author Ryan Thornton
   */
  private void searchResults() {
    try {
      // sends the user input and selected search type to query the API
      currentNode = BackEndAPIQuery.searchResults(input, filter);
      // no search results
      if (currentNode.get("results").size() == 0) {
        throw new IOException();
      }
    } catch (IOException e) {
      // this exception is caught when the results array inside the JSonNode from the search
      // is populated with nothing. AKA THE ADVANCED SEARCH DIDNT FIND SHIT.
      JOptionPane.showMessageDialog(this,
          "Try using different Advanced Search options, nothing was found using your current "
              + "Advanced Search options when searching for: " + input + ".",
          "No Search Results to Show", JOptionPane.ERROR_MESSAGE);
    }
    // Putting title and description into an arrayList<StringBuilder> to use for building a
    // JComboBox.
    int length = currentNode.get("results").size();
    // Populates title and description of each search item.
    for (int i = 0; i < length; i++) {
      // get the title and desc of each result
      String curTitle = currentNode.get("results").get(i).get("title").toString();
      String curDesc = currentNode.get("results").get(i).get("description").toString();

      // remove quotation marks
      curTitle = curTitle.replace("\"", "");
      curDesc = curDesc.replace("\"", "");

      // append them together into a readable string and store in searchResultsRefined
      String curString = String.format("%s - %s", curTitle, curDesc);
      searchResultsRefined.add(curString);
    }

    // Adds results to a JComboBox with titles and descriptions.
    for (String str : searchResultsRefined) {
      searchResultsComboBox.addItem(str);
    }

    // assigns an actionlistener to go off when a result is selected
    searchResultsComboBox.addActionListener(e -> searchResultSelected());
  }

  /**
   * Used to reset search results panel and display new searches.
   *
   * @return Void as to work with SwingWorker
   * @author Ryan Thornton
   */
  private Void searchResultsResetAndDisplay() {
    // clears array and Combo box so that it can be filled with new result
    searchResultsRefined.clear();
    searchResultsComboBox.removeAllItems();
    // calls searchResults
    searchResults();
    // revalidates and repaints comboBox
    searchResultsComboBox.revalidate();
    searchResultsComboBox.repaint();
    return null;
  }

  /**
   * Used to gather all advanced filter options being selected.
   */
  private void filterSelected() {
    // updates filter
    filter.clear();
    filter.add(Objects.requireNonNull(searchType.getSelectedItem()).toString());
    // if filter type is Advanced Search, do Advanced Search things
    if (Objects.equals(filter.get(0), "Advanced Search")) {
      JOptionPane.showMessageDialog(searchType, new AdvancedFilterUI(), "Filter Options",
          JOptionPane.PLAIN_MESSAGE);
      ArrayList<String> optionsSelected = AdvancedFilterUI.actionListener();
      // if nothing is selected, prompt user to select something
      if (optionsSelected.size() == 0) {
        JOptionPane.showMessageDialog(searchType, "Please select at least one option.",
            "Invalid Search", JOptionPane.ERROR_MESSAGE);
      } else {
        // populates filter with selected checkboxes strings
        //          filter.set(i + 1, optionsSelected.get(i));
        filter.addAll(optionsSelected);
      }
    }
  }

  /**
   * Action listener for the submission button, it will update the currently
   * displayed search results with the new searches' results.
   */
  private void submissionButtonPressed() {
    containingInterface.loading();
    input = textInput.getText();
    // calls this to reset and display new results, this runs in a background thread to not slow
    // the UI
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
      @Override
      protected Void doInBackground() {
        return searchResultsResetAndDisplay();
      }

      @Override
      protected void done() {
        containingInterface.doneLoading();
      }
    };
    worker.execute();
  }

  /**
   * Action listener for the search result, when a result is selected it will update the
   * currentMovie... attributes to the selected result for use in MovieDescription.
   */
  private void searchResultSelected() {
    // Grabs the id, title, image link, and description of the selected movie if you need it.
    try {
      currentMovieId =
          currentNode.get("results").get(searchResultsComboBox.getSelectedIndex()).get("id")
              .toString().replace("\"", "").replace("\\", "");
      currentMovieTitle =
          currentNode.get("results").get(searchResultsComboBox.getSelectedIndex()).get("title")
              .toString().replace("\"", "").replace("\\", "");
      currentMovieDescription =
          currentNode.get("results").get(searchResultsComboBox.getSelectedIndex())
              .get("description").toString().replace("\"", "")
              .replace("\\", "");
      currentMovieImageLink =
          currentNode.get("results").get(searchResultsComboBox.getSelectedIndex()).get("image")
              .toString();
    } catch (Exception f) {
      System.out.println("searchResultSelected failed to assign variables.");
    }

    // resets the movie description class and does this as a background task
    SwingWorker<Void, Void> movieDescriptionWorker = new SwingWorker<>() {
      @Override
      protected Void doInBackground() {
        return containingInterface.updateMovieDescription(currentMovieId, currentMovieTitle);
      }
    };
    movieDescriptionWorker.execute();
  }

  /**
   * Getter function for the input of the searchbar.
   *
   * @return input of the search.
   * @authors Abdullah Mohammad Ali
   */
  public String getInput() {
    return this.input;
  }

  /**
   * Gets the current movie's ID from search results.
   *
   * @return String of ID
   * @author Ryan Thornton
   */
  public String getCurrentMovieId() {
    return currentMovieId;
  }

  /**
   * Gets the current movie's Image Link from search results.
   *
   * @return String of Image Link
   * @author Ryan Thornton
   */
  public String getCurrentMovieImageLink() {
    return currentMovieImageLink;
  }

  /**
   * Gets the current movie's Description from search results.
   *
   * @return String of Description
   * @author Ryan Thornton
   */
  public String getCurrentMovieDescription() {
    return currentMovieDescription;
  }

  /**
   * Gets the current movie's Title from search results.
   *
   * @return String of Title
   * @author Ryan Thornton
   */
  public String getCurrentMovieTitle() {
    return currentMovieTitle;
  }

  /**
   * Gets the current JsonNode that was returned from the current search.  This is another resource
   * to help you create your portion of the search bar.
   *
   * @return JsonNode current node
   * @author Ryan Thornton
   */
  public JsonNode getCurrentJSonNode() {
    return currentNode;
  }
}