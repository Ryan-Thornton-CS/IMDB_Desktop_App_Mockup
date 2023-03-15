import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class BackEndAPIQueryTest {


  /**
   * Testing a standard JSON query without advanced Search.
   * NOTE: whether this passes or not is basically RNG. Everything works fine, and then
   * the JSON randomly has 1 minor tree position modified to null, so I am assuming that
   * IMDB-api is just not returning the value.
   *
   * @author Jonah Giblin.
   *
   * @throws Exception Unused potential throw to satisfy Compiler, will NOT throw.
   */
  @Test
  void searchResults() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readValue(new URL("https://imdb-api.com/en/API/SearchMovie/k_mcx0w8kk/hello"), JsonNode.class);
    ArrayList<String> movieTest = new ArrayList<>();
    movieTest.add("Movies");
    Assertions.assertEquals(node, BackEndAPIQuery.searchResults("hello", movieTest));
  }

  /**
   * Testing an Advanced Json Query with a few boxes selected.
   * Main reason this does not test all possible combos is that the results are
   * based off the advancedSearchString, which has its own tester for all possible selections.
   *
   * NOTE: This one ESPECIALLY will just sometimes arbitrarily change the Rating of the selected
   * piece to Null when it should be PG. I've reviewed the code, the most likely culprit is the api
   * itself. Rerunning the test multiple times will eventually return a pass.
   *
   * @author Jonah Giblin.
   *
   * @throws Exception If this throws, you have no internet lol.
   */
  @Test
  void advancedSearchResults() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode nodeAdvanced = mapper.readValue(new URL("https://imdb-api.com/en/API/AdvancedSearch/k_mcx0w8kk/?title=hello&title_type=tv_movie,documentary"), JsonNode.class);
    ArrayList<String> advancedTest = new ArrayList<>();
    advancedTest.add("Advanced Search");
    advancedTest.add("TV Movie");
    advancedTest.add("Documentary");
    Assertions.assertEquals(nodeAdvanced, BackEndAPIQuery.searchResults("hello", advancedTest));
  }

  /**
   *  Tests the production of a query for ALL possible advanced searches. Nom this will not
   *  produce a real movie if you queried the api, but this adequately covers all possible runnings
   *  of the code, since the while loop iterates checking just the current position's comparison
   *  of all possible word choices until it finds its.
   *
   * @author Jonah Giblin
   */
  @Test
  void advancedSearchStringTest() {
    String stringTesting = "&title_type=tv_movie,documentary,feature,short&genres=fantasy,action," +
      "adventure,war,thriller,comedy,sci_fi,animation,romance,horror,mystery&certificates=us:G," +
      "us:PG,us:R,us:PG-13,us:NC-17&colors=color,black_and_white";
    StringBuilder builder = new StringBuilder();
    String[] inputs = {"Advanced Search", "TV Movie", "Documentary",
        "Feature Film", "Short Film", "Fantasy", "Action", "Adventure", "War", "Thriller",
        "Comedy", "Sci-Fi", "Animation", "Romance", "Horror", "Mystery", "G", "PG", "R", "PG-13",
        "NC-17", "Color", "Black & White"};
    ArrayList<String> filters = new ArrayList<>(Arrays.asList(inputs));
    BackEndAPIQuery.advancedSearchString(builder, filters);
    Assertions.assertEquals(stringTesting, builder.toString());
  }
}