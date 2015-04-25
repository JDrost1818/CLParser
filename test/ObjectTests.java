import data.CraigslistUrls;
import objects.Post;
import objects.Search;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ObjectTests {

    @Test
    public void searchValidURLsTest() {
        Search search1 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", "", "");
        Search search2 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", "Xbox One", "games");
        Search search3 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", "Xbox One", "games", 100, -1);
        Search search4 = new Search(CraigslistUrls.ALL.owner(), "minneapolis", "Xbox One", "games", 100, 200);

        assertEquals(search1.searchUrl(1), "http://minneapolis.craigslist.org/search/sso?s=0");
        assertEquals(search2.searchUrl(2), "http://minneapolis.craigslist.org/search/sso?s=100&query=Xbox%20One%20-games");
        assertEquals(search3.searchUrl(3), "http://minneapolis.craigslist.org/search/sso?minAsk=100&s=200&query=Xbox%20One%20-games");
        assertEquals(search4.searchUrl(4), "http://minneapolis.craigslist.org/search/sso?minAsk=100&maxAsk=200&s=300&query=Xbox%20One%20-games");
    }

    @Test
    public void postConstructorTest() throws IOException {
        Post curPost;
        Search dummySearch = new Search("cat", "city", "match", "exclusion", -1, -1);
        String dir = System.getProperty("user.dir") + "\\test\\resources";

        // Tests to make sure a valid Post is created
        // When everything is present
        File curFile = new File(dir, "valid.html");
        Element curElement = Jsoup.parse(curFile, "UTF-8", "http://craigslist.org").select("p.row").get(0);
        curPost = new Post(dummySearch, curElement);

        assertEquals(curPost.title(), "Title");
        assertEquals(curPost.county(), "cty");
        assertEquals(curPost.category(), "cat");
        assertEquals(curPost.id(), "9999999999");
        assertEquals(curPost.location(), "America");
        assertEquals(curPost.price(), 100);
        assert(curPost.isFromSearch(dummySearch));

        // Tests to make sure a valid Post is created
        // When price is not present
        curFile = new File(dir, "noPrice.html");
        curElement = Jsoup.parse(curFile, "UTF-8", "http://craigslist.org").select("p.row").get(0);
        curPost = new Post(dummySearch, curElement);

        assertEquals(curPost.title(), "Title");
        assertEquals(curPost.county(), "cty");
        assertEquals(curPost.category(), "cat");
        assertEquals(curPost.id(), "9999999999");
        assertEquals(curPost.location(), "America");
        assertEquals(curPost.price(), -1);
        assert(curPost.isFromSearch(dummySearch));

        // Tests to make sure a valid Post is created
        // When location is not present
        curFile = new File(dir, "noLocation.html");
        curElement = Jsoup.parse(curFile, "UTF-8", "http://craigslist.org").select("p.row").get(0);
        curPost = new Post(dummySearch, curElement);

        assertEquals(curPost.title(), "Title");
        assertEquals(curPost.county(), "cty");
        assertEquals(curPost.category(), "cat");
        assertEquals(curPost.id(), "9999999999");
        assertEquals(curPost.location(), "");
        assertEquals(curPost.price(), 100);
        assert(curPost.isFromSearch(dummySearch));

    }
}
