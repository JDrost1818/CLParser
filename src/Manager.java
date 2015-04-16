import data.CraigslistUrls;
import dataHandlers.JSoupAddOn;
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

    /*  Parses over a Craigslist page and runs until no more pages, or settings
        defined by user indicate a stop is required. Gathers as much data as
        possible while doing so. Returns a list of new posts.
    */
    public void parsePages() {
        double curTime = System.currentTimeMillis();
        Document doc;

        int i, numEntries;

        for (Search curSearch : this.searches) {
            // Reset variables
            i = -1; numEntries = 100;

            // Iterate over whole pages
            while (numEntries == 100) {
                System.out.println("Scanning Page " + (++i + 1));
                doc = JSoupAddOn.connect(curSearch.searchUrl(i));

                if (doc != null) {
                    // Gets the number of posts found and isolates
                    // the posts by trimming the document to only them
                    numEntries = doc.select("p.row").size();
                    Elements something = doc.select("div.content");
                    Element curPost;

                    // Iterates through all the posts found for the page
                    // Currently just prints all relevant information
                    for (int j = 0; j < numEntries; j++) {
                        curPost = something.select("p.row").get(j);
                        doc = JSoupAddOn.connect(curPost.select("a.i").attr("abs:href"));
                        if (doc != null)
                            new Post(doc);

                    }
                } else {
                    System.out.println("Could not connect, moving on");
                }
            }
        }
        System.out.println("Process took " + (System.currentTimeMillis() - curTime) / 1000 + " seconds");
    }
}
