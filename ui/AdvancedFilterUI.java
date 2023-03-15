import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is for displaying the Advanced Filter options on SearchBar.
 *
 * @author Ryan Thornton
 * @version 11/6/2022
 */
public class AdvancedFilterUI extends JPanel {

  private static final Checkbox tvMovie = new Checkbox();
  private static final Checkbox documentary = new Checkbox();
  private static final Checkbox featureFilm = new Checkbox();
  private static final Checkbox shortFilm = new Checkbox();
  private static final Checkbox fantasy = new Checkbox();
  private static final Checkbox action = new Checkbox();
  private static final Checkbox adventure = new Checkbox();
  private static final Checkbox war = new Checkbox();
  private static final Checkbox thriller = new Checkbox();
  private static final Checkbox comedy = new Checkbox();
  private static final Checkbox sciFi = new Checkbox();
  private static final Checkbox animation = new Checkbox();
  private static final Checkbox romance = new Checkbox();
  private static final Checkbox horror = new Checkbox();
  private static final Checkbox mystery = new Checkbox();
  private static final Checkbox g = new Checkbox();
  private static final Checkbox pg = new Checkbox();
  private static final Checkbox r = new Checkbox();
  private static final Checkbox pg13 = new Checkbox();
  private static final Checkbox nc17 = new Checkbox();
  private static final Checkbox color = new Checkbox();
  private static final Checkbox blackNwhite = new Checkbox();
  // Stores all CheckBoxes into memory using this array.  This array is then called to get each
  // CheckBoxes state (True if checked, False if not)
  private static final Checkbox[] checkboxes =
      new Checkbox[] {tvMovie, documentary, featureFilm, shortFilm, fantasy, action, adventure, war,
          thriller, comedy, sciFi, animation, romance, horror, mystery, g, pg, r, pg13, nc17, color,
          blackNwhite};
  // This is the start of the four sections of advanced search options.  These HashMaps are used as
  // helpers with help identifying how to construct the advanced search link to the IMDb database.
  public static final HashMap<String, String> titleType = new HashMap<>() {{
    put("TV Movie", "tv_movie");
    put("Feature Film", "feature");
    put("Short Film", "short");
    put("Documentary", "documentary");
  }};
  public static final HashMap<String, String> usRatings = new HashMap<>() {{
    put("PG-13", "us:PG-13");
    put("PG", "us:PG");
    put("NC-17", "us:NC-17");
    put("G", "us:G");
    put("R", "us:R");
  }};
  public static final HashMap<String, String> genres = new HashMap<>() {{
    put("Action", "action");
    put("Comedy", "comedy");
    put("Thriller", "thriller");
    put("Romance", "romance");
    put("Fantasy", "fantasy");
    put("Mystery", "mystery");
    put("Adventure", "adventure");
    put("Sci-Fi", "sci_fi");
    put("War", "war");
    put("Horror", "horror");
    put("Animation", "animation");
  }};
  public static final HashMap<String, String> colorInfo = new HashMap<>() {{
    put("Color", "color");
    put("Black & White", "black_and_white");
  }};

  /**
   * Primary constructor that constructs the advanced filter selection panel.
   */
  public AdvancedFilterUI() {
    super();
    buildsAdvancedSearchFilterPanel();
    revalidate();
    repaint();
  }

  /**
   * Gets which check boxes are checked and their names using {@link Checkbox#getState()} of each
   * Checkbox and grabbing their names from the checkboxes array.
   */
  public static ArrayList<String> actionListener() {
    ArrayList<String> list = new ArrayList<>();

    for (Checkbox checkbox : checkboxes) {
      if (checkbox.getState()) {
        list.add(checkbox.getLabel());
      }
    }

    return list;
  }

  /**
   * Testing.
   *
   * @param args N/A
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Test");
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setSize(350, 295);
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
    JOptionPane.showMessageDialog(frame, new AdvancedFilterUI(), "Filter Options",
        JOptionPane.PLAIN_MESSAGE);
    actionListener();
    frame.revalidate();
    frame.repaint();
  }

  /**
   * Builds the advanced filter panel.
   */
  private void buildsAdvancedSearchFilterPanel() {
    JLabel genres = new JLabel();
    JLabel usRatings = new JLabel();
    JLabel titleType = new JLabel();
    JLabel colorInfo = new JLabel();

    // Title Type Start
    titleType.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    titleType.setText("Title Type");
    titleType.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    titleType.setHorizontalTextPosition(SwingConstants.CENTER);

    tvMovie.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    tvMovie.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    tvMovie.setLabel("TV Movie");

    documentary.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    documentary.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    documentary.setLabel("Documentary");

    featureFilm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    featureFilm.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    featureFilm.setLabel("Feature Film");

    shortFilm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    shortFilm.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    shortFilm.setLabel("Short Film");
    // Title Type End

    // Genres Start
    genres.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    genres.setText("Genres");
    genres.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    genres.setHorizontalTextPosition(SwingConstants.CENTER);

    fantasy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    fantasy.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    fantasy.setLabel("Fantasy");

    action.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    action.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    action.setLabel("Action");

    adventure.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    adventure.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    adventure.setLabel("Adventure");

    war.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    war.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    war.setLabel("War");

    thriller.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    thriller.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    thriller.setLabel("Thriller");

    comedy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    comedy.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    comedy.setLabel("Comedy");

    sciFi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    sciFi.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    sciFi.setLabel("Sci-Fi");

    animation.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    animation.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    animation.setLabel("Animation");

    romance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    romance.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    romance.setLabel("Romance");

    horror.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    horror.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    horror.setLabel("Horror");

    mystery.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    mystery.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    mystery.setLabel("Mystery");
    // Genres End

    // US Ratings Start
    usRatings.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    usRatings.setText("US Ratings");
    usRatings.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    usRatings.setHorizontalTextPosition(SwingConstants.CENTER);

    g.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    g.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    g.setLabel("G");

    pg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pg.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    pg.setLabel("PG");

    r.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    r.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    r.setLabel("R");

    pg13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    pg13.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    pg13.setLabel("PG-13");

    nc17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    nc17.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    nc17.setLabel("NC-17");
    // US Ratings End

    // Color Info Start
    colorInfo.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    colorInfo.setText("Color Info");
    colorInfo.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    colorInfo.setHorizontalTextPosition(SwingConstants.CENTER);

    color.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    color.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    color.setLabel("Color");

    blackNwhite.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    blackNwhite.setFont(new java.awt.Font("Copperplate Gothic Bold",
        Font.PLAIN, 10)); // NOI18N
    blackNwhite.setLabel("Black & White");
    // Color Info End

    // Some Uga-Buga stuff happens below here.  Don't venture too far.
    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(titleType, GroupLayout.Alignment.LEADING,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(genres, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usRatings, GroupLayout.Alignment.LEADING,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(colorInfo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(tvMovie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(action, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(fantasy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(war, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(g, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(pg13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(color, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(shortFilm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(featureFilm, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(
                                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(comedy, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mystery, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(horror, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pg, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addGroup(
                                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nc17, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(adventure, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(thriller, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(animation, GroupLayout.PREFERRED_SIZE,
                                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(r, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(romance, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(sciFi, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(blackNwhite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(tvMovie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(featureFilm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleType)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(documentary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(shortFilm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(romance, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(thriller, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(adventure, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(sciFi, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comedy, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(mystery, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(action, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(genres))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fantasy, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(horror, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(war, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(animation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nc17, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(pg13, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(pg, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(usRatings))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(r, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(g, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(color, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(blackNwhite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorInfo))
                .addGap(17, 17, 17))
    );
  }
}