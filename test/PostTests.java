import objects.Post;
import objects.Search;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PostTests {

    @Test
    public void constructorTest() throws IOException {
        Post curPost;
        Search dummySearch = new Search("cat", "city", "match", -1, -1);
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
