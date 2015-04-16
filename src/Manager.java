import data.CraigslistUrls;
import data.DATA;
import data.Email;
import dataHandlers.JSoupAddOn;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Manager {

    private boolean testing = false;
    private String username = "";
    private String password = "";
    private String toEmail = "";

    public static void main(String[] args) {
        Manager app = new Manager();
        app.run(args);
    }

    private Search[] searches;

    public Manager() {
        searches = new Search[] {
                new Search(CraigslistUrls.VIDEO_GAMING.owner(), "minneapolis", "XBOX ONE"),
                new Search(CraigslistUrls.ELECTRONICS.owner(), "minneapolis", "TV -projection")
        };
    }

    public void run(String[] args) {
        if (args.length > 0) {
            testing = true;
            username = args[0];
            password = args[1];
            toEmail = args[2];
        }

        ArrayList<Post> newPosts = parsePages();
        this.emailPosts(newPosts);
    }

    private void emailPosts(ArrayList<Post> newPosts) {
        ArrayList<String> message = new ArrayList<String>();

        for (Search curSearch : this.searches) {
            // Creates heading for each search category
            message.add(CraigslistUrls.titleFromKey(curSearch.category()) + "\n\n");
            for (Post curPost : newPosts) {
                if (curPost.isFromSearch(curSearch)) {
                    message.add("\t$" + curPost.price() + " - " +
                                        curPost.title() + " " +
                                        curPost.link() + "\n");
                }
            }
        }

        Email.sendMail(this.username, this.password, this.toEmail, message.toArray(new String[message.size()]));
    }

    /*  Parses over a Craigslist page and runs until no more pages, or settings
        defined by user indicate a stop is required. Gathers as much data as
        possible while doing so. Returns a list of new posts.
    */
    public ArrayList<Post> parsePages() {
        double curTime = System.currentTimeMillis();

        ArrayList<Post> newPosts = new ArrayList<Post>();
        Document doc;

        int i, numEntries, numAlreadyVisited=0;

        for (Search curSearch : this.searches) {
            // Reset variables
            i = -1; numEntries = 100;

            // Iterate over whole pages
            while (numEntries == 100 && numAlreadyVisited < 25) {
                numAlreadyVisited = 0;

                System.out.println("Scanning Page " + (++i + 1));
                doc = JSoupAddOn.connect(curSearch.searchUrl(i));

                if (doc != null) {
                    // Gets the number of posts found and isolates
                    // the posts by trimming the document to only them
                    numEntries = (testing) ? 5 : doc.select("p.row").size();
                    Elements something = doc.select("div.content");
                    Element curPost;

                    // Iterates through all the posts found for the page
                    // Currently just prints all relevant information
                    for (int j = 0; j < numEntries; j++) {
                        curPost = something.select("p.row").get(j);
                        doc = JSoupAddOn.connect(curPost.select("a.i").attr("abs:href"));
                        if (doc != null) {
                            Post newPost = new Post(curSearch, doc);
                            if (DATA.alreadyVisited(newPost.id())) {
                                numAlreadyVisited++;
                            } else {
                                newPosts.add(newPost);
                                DATA.addPage(newPost.id());
                            }
                        }
                    }
                } else {
                    System.out.println("Could not connect, moving on");
                }
            }
        }
        System.out.println("Process took " + (System.currentTimeMillis() - curTime) / 1000 + " seconds");
        return newPosts;
    }
}
