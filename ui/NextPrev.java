import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * NextPrev creates the Next and previous button for the main UserInterface Class.
 *
 * @authors Abdullah Mohammad Ali, Jonah Giblin
 *
 */
public class NextPrev extends JPanel {
  private final JButton nextButton;
  private final UserInterface containerInterface;
  private final JButton prevButton;
  private final Stack<MovieDescription> previous;
  private final Stack<MovieDescription> next;

  /**
   * Constructor for next previous buttons
   *
   * @param inter Current user interface.
   */
  public NextPrev(UserInterface inter) {
    super();

    //Defining size of JButton storage
    setPreferredSize(new Dimension(200, 30));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // vertical layout

    // Initalize all variables
    containerInterface = inter;

    nextButton = new JButton("Next");
    nextButton.setEnabled(false);
    nextButton.addActionListener(e -> nextPressed());
    next = new Stack<>();

    prevButton = new JButton("Prev");
    prevButton.setEnabled(false);
    prevButton.addActionListener(e -> prevPressed());
    previous = new Stack<>();

    // Layout for JButtons
    JPanel buttonBox = new JPanel();
    buttonBox.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2)); // centered,
    // 2 pixel
    // gap, 2
    // pixel
    // gap
    buttonBox.setMaximumSize(new Dimension(200, 30)); // cap height
    buttonBox.add(this.prevButton); // prev on the left
    buttonBox.add(this.nextButton); // next on the right

    add(buttonBox);
    setVisible(true);
    repaint();

  }

  /**
   * Method Listening for when previous button is pressed, pops the stack, loads to other stack
   * if necessary.
   *
   * @author Jonah Giblin
   */

  public void prevPressed() {
    MovieDescription loading = previous.pop();
    if (containerInterface.getActiveMovieDescription() != null) {
      next.push(containerInterface.getActiveMovieDescription());
      nextButton.setEnabled(true);
    }
    containerInterface.updateMovieDescription(loading);
    if (previous.isEmpty()) {
      prevButton.setEnabled(false);
    }
  }

  /**
   * Method Listening for when next button is pressed, pops the stack, loads to other stack
   * if necessary.
   *
   * @author Jonah Giblin
   */
  public void nextPressed() {
    MovieDescription loading = next.pop();
    if (containerInterface.getActiveMovieDescription() != null) {
      previous.push(containerInterface.getActiveMovieDescription());
      prevButton.setEnabled(true);
    }
    containerInterface.updateMovieDescription(loading);
    if (next.isEmpty()) {
      nextButton.setEnabled(false);
    }
  }

  /**
   * Adds a MovieDescription to the necessary stack, only if not previously existing.
   * @param movieDescription inputted movieDescription, validates its lack of presence.
   */
  public void add(MovieDescription movieDescription) {
    if (!previous.contains(movieDescription)) {
      previous.add(movieDescription);
      prevButton.setEnabled(true);
    }
  }
}
