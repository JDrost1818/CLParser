package objects;

public class Search {

    private String category;
    private String city;
    private String match;
    private String exclusions;
    private String fullSearch;
    private String searchUrl;

    private int minPrice;
    private int maxPrice;

    private String visitedIds = "";

    public Search(String _category, String _city, String _match, String _exclusions) {
        this(_category, _city, _match, _exclusions, -1, -1);
    }

    public Search(String _category, String _city, String _match, String _exclusions, int _minPrice, int _maxPrice) {
        category = _category;
        city = _city;
        match = _match;
        exclusions = _exclusions;
        minPrice = _minPrice;
        maxPrice = _maxPrice;

        fullSearch = joinMatchAndExclusions(match, exclusions);

        searchUrl = generateURL(category, city, fullSearch, minPrice, maxPrice);
    }

    public String category() {
        return category;
    }

    public String match() {
        return match;
    }

    public String exclusions() {
        return exclusions;
    }

    public int minPrice() {
        return minPrice;
    }

    public int maxPrice() {
        return maxPrice;
    }

    private String joinMatchAndExclusions(String matches, String exclusions) {
        String joined = matches;
        if (exclusions != null && !(exclusions.equals(""))) {
            String[] exclusionWords = exclusions.split(" ");
            for (String exclusion : exclusionWords) {
                joined += " -" + exclusion;
            }
        }
        return joined;
    }

    private String generateURL(String category, String city, String full_search, int minPrice, int maxPrice) {
        // Since the base URL of craigslist is defined by the city
        // in which you are looking, it must be defined
        // @TODO make sure the city is a valid one. For example, "Blaine" is not valid
        if (city == null || city.equals(""))
            throw new IllegalArgumentException("Error: city argument must not be null or empty");

        String url;
        // Sets base of the URL along with the min and max prices if set
        url = "http://" + city + ".craigslist.org/search/" + category + "?";
        if (minPrice != -1)
            url += "minAsk=" + minPrice + "&";
        if (maxPrice != -1)
            url += "maxAsk=" + maxPrice + "&";

        // Creates the query aspect of the URL
        if (full_search == null || full_search.equals("")) {
            url += "s=!PAGENUMBER!";
        } else {
            url += "s=!PAGENUMBER!&query=";
            for (String word : full_search.split(" ")) {
                url += word + "%20";
            }
            url = url.substring(0, url.length()-3);
        }

        return url;
    }

    public String searchUrl(int page) {
        return searchUrl.replace("!PAGENUMBER!", Integer.toString(100*(page-1)));
    }

    public boolean alreadyVisited(String postId) {
        return visitedIds.contains(postId);
    }

    public void addPage(String postId) {
        visitedIds += postId + " ";
    }
}
