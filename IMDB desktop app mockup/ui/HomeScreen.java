import com.fasterxml.jackson.databind.*;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

public class HomeScreen extends JPanel {

  // hashtable storing the top 10 movies <Title, ID>
  private final Hashtable<String, String> top10 = new Hashtable<>();
  // Json node containing the top 250 movies, meaning this top 10 list can be expanded up to 250.
  private final JsonNode topMoviesRaw = BackEndAPIQuery.topMoviesSearch();
  private UserInterface containerInterface;

  public HomeScreen(UserInterface containerInterface) {
    this.containerInterface = containerInterface;
    // cap the size of the homescreen otherwise it fucks a lot of the formatting
    setPreferredSize(new Dimension(1225, 2550));
    setMaximumSize(new Dimension(1225, 2550));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // vertical stack

    JLabel topBar = new JLabel("Top 25 Movies:");
    add(topBar);
    for (int i = 0; i < 25; i++) {
      // get the ranking of the movie
      JLabel movieRanking = new JLabel(topMoviesRaw.get("items").get(i).get("rank").asText());
      movieRanking.setPreferredSize(new Dimension(75, 100));
      movieRanking.setSize(new Dimension(75, 100));

      // get the image to represent the movie
      JLabel moviePoster = new JLabel();
      try { // try and get the poster image, if it cant then setText
        URL posterURL = new URL(topMoviesRaw.get("items").get(i).get("image").asText());
        Image scaledImage =
            new ImageIcon(posterURL).getImage().getScaledInstance(67, 100, Image.SCALE_SMOOTH);
        moviePoster.setIcon(new ImageIcon(scaledImage));
      } catch (MalformedURLException e) {
        moviePoster.setText(
            "Image\nNot\nFound"); // I could not test this because idk how to put a bad url in this
      }
      moviePoster.setMaximumSize(new Dimension(67, 100)); // cap the poster size; 2:3 aspect ratio

      // get the title of the movie
      JLabel movieTitle = new JLabel(topMoviesRaw.get("items").get(i).get("fullTitle").asText());
      movieTitle.setAlignmentX(Component.LEFT_ALIGNMENT); // left align so its readable

      // get the movie rating
      JLabel movieRating = new JLabel(
          "Rating: " + topMoviesRaw.get("items").get(i).get("imDbRating").asText() + "/10\t\t");
      movieRating.setAlignmentX(Component.LEFT_ALIGNMENT); // left align so its readable

      // get the movie crew (director and main actors i think)
      JLabel movieCrew =
          new JLabel("Crew: " + topMoviesRaw.get("items").get(i).get("crew").asText());
      movieCrew.setAlignmentX(Component.LEFT_ALIGNMENT); // left align so its readable

      // lower half of the movieinfo
      JPanel movieInfoLower = new JPanel();
      movieInfoLower.setMaximumSize(new Dimension(1225, 50)); // cap height
      movieInfoLower.setLayout(new BoxLayout(movieInfoLower, BoxLayout.X_AXIS));
      movieInfoLower.setAlignmentX(Component.LEFT_ALIGNMENT); // left align for fun
      // if you know how to, put a star in front of the rating instead of the: "Rating: "
      movieInfoLower.add(movieRating);
      // spacer between rating and crew because the left alignment squishes them
      // together with no spaces between. NOTE: I don't think that \t displays
      // at all, hence the 10 spaces.
      movieInfoLower.add(new JLabel("          "));
      movieInfoLower.add(movieCrew);

      // upper half of the movieinfo, only here to allow formatting to know what to do
      JPanel movieInfoUpper = new JPanel();
      movieInfoUpper.setMaximumSize(new Dimension(1225, 50));
      movieInfoUpper.setLayout(new BoxLayout(movieInfoUpper, BoxLayout.X_AXIS));
      movieInfoUpper.setAlignmentX(Component.LEFT_ALIGNMENT); // left align for fun
      movieInfoUpper.add(movieTitle);

      // combination of upper and lower movieinfo panels, respectively
      JPanel movieInfo = new JPanel();
      movieInfo.setMaximumSize(new Dimension(1225, 100));
      movieInfo.setLayout(new BoxLayout(movieInfo, BoxLayout.Y_AXIS)); // vertical this time
      movieInfo.add(movieInfoUpper);
      movieInfo.add(movieInfoLower);

      // panel that combines the ranking, poster, and info
      JPanel moviePanel = new JPanel();
      moviePanel.setPreferredSize(new Dimension(1250, 100));
      // border to distinguish elements, you can change the color if you want
      moviePanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
      moviePanel.setLayout(new BoxLayout(moviePanel, BoxLayout.X_AXIS));
      // left align for fun (i think this one actually does something)
      moviePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      moviePanel.setSize(1250, 100); // cap the size of each element
      moviePanel.add(movieRanking);
      if (i >= 9) {
        moviePanel.add(new JLabel("        ")); // another space (2 spaces shorter)
      } else {
        moviePanel.add(new JLabel("          ")); // another spacer
      }
      moviePanel.add(moviePoster);
      moviePanel.add(new JLabel("          ")); // see previous comment
      moviePanel.add(movieInfo);
      moviePanel.setVisible(true); // make sure its visible

      // add moviePanel
      add(moviePanel); // most important line
      // put the movie into the hashtable (for selection mapping to IDs)
      top10.put(movieTitle.getText(), topMoviesRaw.get("items").get(i).get("id").asText());
      final String id = topMoviesRaw.get("items").get(i).get("id").asText();
      final String title = topMoviesRaw.get("items").get(i).get("fullTitle").asText();
      moviePanel.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() >= 2) {
            SwingWorker<Void, Void> movieDescriptionWorker = new SwingWorker<>() {
              @Override
              protected Void doInBackground() {
                // spaces added because of how original code is wrote
                return containerInterface.updateMovieDescription(id, title);
              }
            };
            movieDescriptionWorker.execute();
          } else {
            moviePanel.setBorder(new LineBorder(Color.BLACK, 3));
            removeBorderAll(moviePanel);
            containerInterface.homeScreenSelection(title, id);
          }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
      });
    }
    setVisible(true);
    repaint();
  }

  @SuppressWarnings("unchecked")
  public void removeBorderAll(JPanel panel) {
    for (Component component : getComponents()) {
      if (!component.equals(panel)) {
        if (component instanceof JPanel panel2) {
          panel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        }
      }
    }
  }

}
