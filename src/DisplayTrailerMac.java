import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class is used to obtain the YT trailer link for Mac's.
 *
 * @author Ryan Thornton
 * @version 12/6/2022
 */
public class DisplayTrailerMac {

  /**
   * Dummy constructor, only used to stay inline with object-oriented programming.
   */
  public DisplayTrailerMac() {
  }

  /**
   * Returns movie trailers link.
   *
   * @param currentMovieId the current movie's id
   * @return String of YouTube Trailer
   */
  public String trailerLink(String currentMovieId) throws IOException, IllegalArgumentException {
    ObjectMapper mapper = new ObjectMapper();
    // gets YouTubeTrailer node to pull YouTube link
    JsonNode link = mapper.readValue(new URL(
        "https://imdb-api.com/en/API/YouTubeTrailer/k_mcx0w8kk/" +
            currentMovieId), JsonNode.class);
    // gets the last portion of the YouTube link
    String videoCode = link.get("videoUrl").toString();
    System.out.println(videoCode);
    if (videoCode.length() <= 3 || videoCode.equals("null")) {
      throw new IllegalArgumentException();
    }
    // returns the embedded display of the YouTube Video
    //TODO YES, 33 IS THE ACTUAL INDEX OF THE STRING NEEDED EACH TIME. DONT JUDGE....
    return "https://www.youtube.com/embed/" + videoCode.substring(33, videoCode.length() - 1);
  }
}
