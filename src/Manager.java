import data.CraigslistUrls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Manager {

    public static void main(String[] args) {
        Manager app = new Manager();
        app.run();
    }

    private Search[] searches;

    public Manager() {
        searches = new Search[] {
                new Search(CraigslistUrls.VIDEO_GAMING.owner(), "minneapolis", "XBOX ONE"),
                new Search(CraigslistUrls.ELECTRONICS.owner(), "minneapolis", "TV -projection")
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

        int i, numEntries;

        for (Search curSearch : this.searches) {
            // Reset variables
            i = -1; numEntries = 100;

            // Iterate over whole pages
            while (numEntries == 100) {
                System.out.println("Scanning Page " + (++i + 1));
                doc = curSearch.search(i);

                if (doc != null) {
                    // Gets the number of posts found and isolates
                    // the posts by trimming the document to only them
                    numEntries = doc.select("p.row").size();
                    Elements something = doc.select("div.content");
                    Element cur_post;

                    // Iterates through all the posts found for the page
                    // Currently just prints all relevant information
                    for (int j = 0; j < numEntries; j++) {
                        cur_post = something.select("p.row").get(j);
                        System.out.print(cur_post.select("a.hdrlnk").get(0).text() + " ");          // title

                        // Listing the price of the item is not a req. by Craigslist;
                        // therefore, we must check it is there to prevent a
                        // IndexOutOfBounds error
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
}
