import data.CraigslistUrls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Manager {

    public static void main(String[] args) {
        Manager app = new Manager();
        app.run();
    }

    private String city = "minneapolis";
    private Search[] searches;

    public Manager() {
        searches = new Search[] {   new Search(CraigslistUrls.VIDEO_GAMING.owner(), "XBOX ONE"),
                                    new Search(CraigslistUrls.ELECTRONICS.owner(),  "TV -projection")
                                };
    }

    public void run() {
        parsePages();
    }

    public void parsePages() {
        /*  Parses over a Craigslist page and runs until no more pages, or settings
            defined by user indicate a stop is required. Gathers as much data as
            possible while doing so. Returns a list of new posts.
         */

        double curTime = System.currentTimeMillis();

        Document doc;
        String url;

        int i, numEntries, num_tries;

        for (Search curSearch : this.searches) {
            // Reset variables
            i = -1;
            numEntries = 100;

            // Iterate over whole pages
            while (numEntries == 100) {
                num_tries = 0;
                doc = null;

                System.out.println("Scanning Page " + (++i + 1));

                // Calculates the URL of the page and loads the HTML
                while (num_tries < 5 && doc == null) {
                    try {
                        doc = Jsoup.connect(makeUrl(this.city, curSearch, i)).get();
                    } catch (IOException e) {
                        System.out.println("Error while trying to connect to URL. Retrying");
                        num_tries++;
                    }
                }

                if (doc != null) {
                    numEntries = doc.select("p.row").size();
                    Elements something = doc.select("div.content");
                    Element cur_post;
                    for (int j = 0; j < numEntries; j++) {
                        /*
                            STEPS
                              1  - get div content (before loop) - this gets rid of offset <div class="content">
                              2  - get a post
                              3a - if title matches, generate post object
                              3b - else move on
                        */
                        cur_post = something.select("p.row").get(j);
                        System.out.print(cur_post.select("a.hdrlnk").get(0).text() + " ");          // title
                        if (cur_post.select("span.price").size() > 0)
                            System.out.println(cur_post.select("span.price").get(0).text());
                        else
                            System.out.println("-1");// price
                        System.out.println(cur_post.select("a.i").attr("abs:href"));                // link
                        System.out.println(cur_post.select("a.hdrlnk").get(0).attr("data-id"));     // data-id
                        System.out.println("\n");
                    }
                } else {
                    System.out.println("Could not connect, moving on");
                }
            }
        }
        System.out.println("Process took " + (System.currentTimeMillis() - curTime) / 1000 + " seconds");
    }

    public String makeUrl(String city, Search curSearch, int page) {
        String url = "https://" + city + ".craigslist.org/search/" + curSearch.category + "?s=" + (page*100) + "&query=";
        for (String word : curSearch.match.split(" ")) {
            url += word + "%20";
        }
        return url.substring(0, url.length()-3);
    }
}
