import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Search {

    private String category;
    private String city;
    private String match;
    private String searchUrl;

    private int minPrice;
    private int maxPrice;

    public Search(String _category, String _city, String _match) {
        this(_category, _city, _match, -1, -1);
    }

    public Search(String _category, String _city, String _match, int _minPrice, int _maxPrice) {
        category = _category;
        city = _city;
        match = _match;
        minPrice = _minPrice;
        maxPrice = _maxPrice;

        searchUrl = generateURL(category, city, match, minPrice, maxPrice);
    }

    public String category() {
        return category;
    }

    public String match() {
        return match;
    }

    private String generateURL(String _category, String _city, String _match, int _minPrice, int _maxPrice) {
        // Since the base URL of craigslist is defined by the city
        // in which you are looking, it must be defined
        // @TODO make sure the city is a valid one. For example, "Blaine" is not valid
        if (city == null || city.equals(""))
            throw new IllegalArgumentException("Error: city argument must not be null or empty");

        String url;
        // Sets base of the URL along with the min and max prices if set
        url = "https://" + city + ".craigslist.org/search/" + category + "?";
        if (minPrice != -1)
            url += "minAsk=" + minPrice + "&";
        if (maxPrice != -1)
            url += "maxAsk=" + maxPrice + "&";

        // Creates the query aspect of the URL
        if (match == null || match.equals("")) {
            url += "s=!PAGENUMBER!";
        } else {
            url += "s=!PAGENUMBER!&query=";
            for (String word : match.split(" ")) {
                url += word + "%20";
            }
            url = url.substring(0, url.length()-3);
        }

        return url;
    }

    public String searchUrl(int page) {
        return searchUrl.replace("!PAGENUMBER!", Integer.toString(page));
    }
}
