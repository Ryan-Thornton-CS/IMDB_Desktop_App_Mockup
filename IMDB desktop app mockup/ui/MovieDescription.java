import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JPanel;

/**
 * MovieDescription class that displays the movie description
 * after a movie is searched for.
 *
 * @author Ethan Himes / Ryan Thornton
 * @version 11/2/2022
 */
public class MovieDescription extends JPanel {


  //JLabel that is added to the JPanel to display the wiki image
  private final JLabel label = new JLabel();
  private final String iD;
  private final String title;
  private final UserInterface containerInterface;


  /**
   * Constructor for a MovieDescription, only ever has 1 id and title. New ID? New instance.
   *
   * @param id the id of the movie that is desired to be displayed.
   * @param title the title of the movie that is desired to be displayed.
   * @param containerInterface The container of the MovieDescription, what will be visualizing.
   */
  public MovieDescription(String id, String title, UserInterface containerInterface) {
    super();
    this.containerInterface = containerInterface;
    this.title = title;
    this.iD = id;
    setMaximumSize(
        new Dimension(1500, Toolkit.getDefaultToolkit().getScreenSize().height));
    setLayout(new BorderLayout());
    add(label, BorderLayout.CENTER);
    setVisible(true);
  }


  /**
   * DisplayDescription method will display the movie's description
   * and any other relevant information about it.
   *
   * @param id of current movie, INCLUDING QUOTES (idk ask Ryan why)
   */
  private URL displayWIKI(String id) {
    try {
      URL url = new URL(
          "https://imdb-api.com/en/API/Report/k_mcx0w8kk/" + id
              + "/Wikipedia,"); //sets the url variable to a custom URL using the id parameter
      return url; //returns the URL
    } catch (MalformedURLException e) {
      JOptionPane.showMessageDialog(label,
          "There are issues retrieving the movie's WIKI page.",
          "Error 404 Not Found", JOptionPane.ERROR_MESSAGE);
    }
    return null;
  }

  /**
   * Reset method that reconstructs the JPanel.
   */
  public Void visualize() {
    // sets loading gif and cursor until page loads
    containerInterface.loading();
    ImageIcon loading = containerInterface.loading();
    label.setIcon(loading);
    loading.setImageObserver(label);
    // image variable that is used in the JLabel and is displayed
    ImageIcon image = new ImageIcon(displayWIKI(iD));
    containerInterface.doneLoading();
    label.setIcon(image); //the image is set as the JLabel's icon
    return null;
  } // No idea why the return type has to be Void instead of void, but it's intentional

  public String getID() {
    return iD;
  }

  public String getTitle() { return title; }

  /**
   * Checks if two Movie Descriptions are equal.
   *
   * @param other object being compared.
   * @return True if equal, false if not.
   */

  @Override
  public boolean equals(Object other) {
    if (other instanceof MovieDescription other2) {
      return iD.equals(other2.iD);
    }
    return false;
  }
}