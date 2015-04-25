package dataHandlers;

import objects.Post;
import objects.Search;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Parser {

    /*
        Parses over a Craigslist page and runs until no more pages, or settings
        defined by user indicate a stop is required. Gathers as much data as
        possible while doing so. Returns a list of new posts.
     */
    public ArrayList<Post> parseCraigslist(ArrayList<Search> searches) {
        double curTime = System.currentTimeMillis();

        ArrayList<Post> newPosts = new ArrayList<>();
        Document doc;

        int i, numEntries, numAlreadyVisited;

        for (Search curSearch : searches) {
            // Reset variables
            i = 0; numEntries = 100; numAlreadyVisited = 0;

            // Iterate over whole pages
            while (numEntries == 100 && numAlreadyVisited < 25) {
                numAlreadyVisited = 0;

                System.out.println("Scanning Page " + (++i) + " @ " + curSearch.searchUrl(i));
                doc = JSoupAddOn.connect(curSearch.searchUrl(i));

                if (doc == null) {
                    System.out.println("Could not connect, moving on");
                    break;
                } else {
                    // Gets the number of posts found and isolates
                    // the posts by trimming the document to only them
                    numEntries = doc.select("p.row").size();
                    Elements allPosts = doc.select("div.content");
                    Element curPost;

                    // Iterates through all the posts found for the page
                    // Currently just prints all relevant information
                    for (int j = 0; j < numEntries; j++) {
                        curPost = allPosts.select("p.row").get(j);
                        if (curSearch.alreadyVisited(curPost.attr("data-pid"))) {
                            numAlreadyVisited++;
                        } else {
                            newPosts.add(new Post(curSearch, curPost));
                            curSearch.addPage(curPost.attr("data-pid"));

                        }
                    }
                }
            }
        }
        System.out.println("Process took " + (System.currentTimeMillis() - curTime) / 1000 + " seconds");
        return newPosts;
    }
}
