import data.CraigslistUrls;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchTests {

    @Test
    public void validUrls() {
        Search search1 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", null);
        Search search2 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", "Xbox One -games");
        Search search3 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", "Xbox One -games", 100, -1);
        Search search4 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", "Xbox One -games", 100, 200);

        assertEquals(search1.searchUrl(1), "https://minneapolis.craigslist.org/search/sso?s=1");
        assertEquals(search2.searchUrl(2), "https://minneapolis.craigslist.org/search/sso?s=2&query=Xbox%20One%20-games");
        assertEquals(search3.searchUrl(3), "https://minneapolis.craigslist.org/search/sso?minAsk=100&s=3&query=Xbox%20One%20-games");
        assertEquals(search4.searchUrl(4), "https://minneapolis.craigslist.org/search/sso?minAsk=100&maxAsk=200&s=4&query=Xbox%20One%20-games");
    }
}
