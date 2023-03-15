import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class used to test DisplayTrailerMacs.
 *
 * @author Ryan Thornton
 * @version 12/06/2022
 * /
 */
class DisplayTrailerMacTest {

  private final DisplayTrailerMac test = new DisplayTrailerMac();

  @Test
  void trailerLink() throws IOException {
    System.out.println(test.trailerLink("tt0068646"));
    assertEquals("https://www.youtube.com/embed/1x0GpEZnwa8",
        test.trailerLink("tt0068646"));
    assertThrows(IllegalArgumentException.class, () -> test.trailerLink("-11"));
  }
}