import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * The purpose of this class is to display a movie of the current selected movie when prompted to.
 *
 * <p>*****WARNING, IF YOU HAVE SOMETHING COPIED TO YOUR CLIPBOARD THAT IS JAVA CODE AND YOU RUN
 * THIS CLASS, YOU WILL GET ERRORS!!!! DON'T ASK ME WHY BECAUSE I CAN'T TELL YOU*****<p/>
 *
 * @author Ryan Thornton
 * @version 11/02/2022
 */
public class DisplayTrailer extends JPanel {

  // keep this public and non-static we also do not need JWebBrowser.destroyOnFinalization() in the
  // constructor because this is now object-oriented
  public JWebBrowser webBrowser =
      new JWebBrowser(JWebBrowser.useEdgeRuntime());

  /**
   * This is the default constructor of DisplayTrailer.
   */
  public DisplayTrailer(String id) throws IOException {
    super(new BorderLayout());
    webBrowser.setBarsVisible(false);
    add(webBrowser, BorderLayout.CENTER);
    displayNewTrailer(id);
    setVisible(true);
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    // change this if you want, it controls the size of the JOptionPane
    setPreferredSize(new Dimension(size.width - 100, size.height - 200));
  }

  /**
   * Used to display a new movie trailer.
   */
  public void displayNewTrailer(String id) throws IOException, IllegalArgumentException {
    webBrowser.navigate(trailerLink(id));
    webBrowser.revalidate();
    webBrowser.repaint();
  }

  /**
   * Returns movie trailers link.
   *
   * @param currentMovieId the current movie's id
   * @return String of YouTube Trailer
   */
  private String trailerLink(String currentMovieId) throws IOException, IllegalArgumentException {
    ObjectMapper mapper = new ObjectMapper();
    // gets YouTubeTrailer node to pull YouTube link
    JsonNode link = mapper.readValue(new URL(
        "https://imdb-api.com/en/API/YouTubeTrailer/k_mcx0w8kk/" +
            currentMovieId), JsonNode.class);
    // gets the last portion of the YouTube link
    String videoCode = link.get("videoUrl").toString();
    if (videoCode.length() <= 3) {
      throw new IllegalArgumentException();
    }
    // returns the embedded display of the YouTube Video
    //TODO YES, 33 IS THE ACTUAL INDEX OF THE STRING NEEDED EACH TIME. DONT JUDGE....
    return "https://www.youtube.com/embed/" + videoCode.substring(33, videoCode.length() - 1);
  }

  /**
   * Used for local testing and to show how this class must be run from else where.
   *
   * @param args N/A
   */
  public static void main(String[] args) {
    //THIS IS HOW THIS CLASS HAS TO BE RAN WITHIN THE MAIN UI!!!!!!!!
    NativeInterface.open();
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Test");
      frame.setLayout(new BorderLayout());
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.pack();
      frame.setSize(800, 600);
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
      try {
        frame.getContentPane().add(new DisplayTrailer("tt0093176"), BorderLayout.CENTER);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    NativeInterface.runEventPump();
    // don't forget to properly close native components
    Runtime.getRuntime().addShutdownHook(new Thread(NativeInterface::close));
  }
}
