import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;

/**
 * The purpose of this class is to pull data from IMDb using the given input String from the search
 * bar.
 *
 * @author Ryan Thornton
 * @version 10/31/2022
 */
public class BackEndAPIQuery {

  // Filter options
  private static final HashMap<String, String> filterMap = new HashMap<>() {{
    put("Movies", "SearchMovie");
    put("Titles", "SearchTitle");
    put("TV Series", "SearchSeries");
    put("Advanced Search", "AdvancedSearch");
  }};

  /**
   * This method grabs the data that matches the user's input into the search bar from IMDb and
   * returns it as a JsonNode, which can be easily read and used.  This class will later be used
   * with the advanced filter option on the search bar.
   *
   * @param searchBarInput user input from search bar
   * @param filter         this is the filter option from search bar
   * @return JsonNode that can be easily manipulated
   * @throws IOException thrown if the URL is bad
   */
  public static JsonNode searchResults(String searchBarInput, ArrayList<String> filter)
      throws IOException {
    // creates a mapper to populate a JsonNode
    ObjectMapper mapper = new ObjectMapper();
    // has to be done due to how I remove things from the arrayList when constructing the advanced
    // searches API link.
    @SuppressWarnings("unchecked") ArrayList<String> copyOfFilter =
        (ArrayList<String>) filter.clone();

    // handles advanced searches
    if (filter.get(0).equals("Advanced Search")) {
      StringBuilder advancedPortion = new StringBuilder();
      advancedSearchString(advancedPortion, copyOfFilter);
      // this needs to stay like this, because when a search is null, this mapper will not return
      // as an inline variable of return.  Has to be in a node for some odd ass reason when the
      // search results are null.
      @SuppressWarnings("UnnecessaryLocalVariable") JsonNode node = mapper.readValue(new URL(
          "https://imdb-api.com/en/API/" + BackEndAPIQuery.filterMap.get(filter.get(0)) +
              "/k_mcx0w8kk/?title=" + searchBarInput + advancedPortion), JsonNode.class);

      return node;
    }
    // populates and returns a JsonNode filled with the required data, the first filter will always
    // be filter selection.
    return mapper.readValue(new URL(
        "https://imdb-api.com/en/API/" + BackEndAPIQuery.filterMap.get(filter.get(0)) +
            "/k_mcx0w8kk/" + searchBarInput), JsonNode.class);
  }

  /**
   * Builds the advanced search portion of the API link. PUBLIC for testing purposes.
   *
   * @param advancedPortion StringBuilder that will be used
   * @param filter          to build off of
   */
  public static void advancedSearchString(StringBuilder advancedPortion,
                                           ArrayList<String> filter) {
    if (filter.size() > 1) {
      // Don't judge my below code..... This made me want to never do
      // anything with advanced search again t(-_-t) @IMDb
      int titleType = 0;
      int genres = 0;
      int usRatings = 0;
      int colorInfo = 0;
      while (filter.size() > 1) {
        if (AdvancedFilterUI.titleType.containsKey(filter.get(1))) {
          if (titleType == 0) {
            advancedPortion.append("&").append("title_type=")
                .append(AdvancedFilterUI.titleType.get(filter.get(1)));
          } else {
            advancedPortion.append(",").append(AdvancedFilterUI.titleType.get(filter.get(1)));
          }
          filter.remove(1);
          titleType++;
        } else if (AdvancedFilterUI.genres.containsKey(filter.get(1))) {
          if (genres == 0) {
            advancedPortion.append("&").append("genres=")
                .append(AdvancedFilterUI.genres.get(filter.get(1)));
          } else {
            advancedPortion.append(",").append(AdvancedFilterUI.genres.get(filter.get(1)));
          }
          filter.remove(1);
          genres++;
        } else if (AdvancedFilterUI.usRatings.containsKey(filter.get(1))) {
          if (usRatings == 0) {
            advancedPortion.append("&").append("certificates=")
                .append(AdvancedFilterUI.usRatings.get(filter.get(1)));
          } else {
            advancedPortion.append(",").append(AdvancedFilterUI.usRatings.get(filter.get(1)));
          }
          filter.remove(1);
          usRatings++;
        } else if (AdvancedFilterUI.colorInfo.containsKey(filter.get(1))) {
          if (colorInfo == 0) {
            advancedPortion.append("&").append("colors=")
                .append(AdvancedFilterUI.colorInfo.get(filter.get(1)));
          } else {
            advancedPortion.append(",").append(AdvancedFilterUI.colorInfo.get(filter.get(1)));
          }
          filter.remove(1);
          colorInfo++;
        }
      }
    }
  }
  
  /**
   * Method will query the API for the current Top 250 movies.
   *
   * @return A JsonNode, use your resources to find what the node will look like
   */
  public static JsonNode topMoviesSearch(){
    // creates a mapper to populate a JsonNode
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = null;
    try {
    node = mapper.readValue(new URL(
        "https://imdb-api.com/en/API/Top250Movies/k_mcx0w8kk/"), JsonNode.class);
    } catch (IOException e) {
      System.out.println("Error Retrieving the top movies.");
    }
    return node;
  }// just a normal api search I copy pasted from Ryan's code above
}