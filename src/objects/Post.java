package objects;

import org.jsoup.nodes.Element;

public class Post {

    // Post information
    private String title;
    private String description;
    private String link;
    private String county;
    private String category;
    private String postId; // This could be an int. Decide later
    private String location;

    private int price = -1;
    private double value = -1;
    private double timePosted;

    private Search search;

    private boolean haveContacted = false;

    /*
        This does not set description
     */
    public Post(Search _search, Element post) {
        search = _search;

        title = post.select("a.hdrlnk").text();
        postId = post.attr("data-pid");

        // Gets county, category, postId (all from URL)
        link = post.select("a.i").get(0).attr("abs:href");
        if (link != null) {
            // If this ever causes a IndexOutOfRange error,
            // I would be amazed. Never should happen.
            String[] info = link.split(".org/")[1].split("/");

            // Again, if any of these causes a IndexOutOfRange error,
            // I would be amazed. I'll think about safety if it happens.
            county = info[0];
            category = info[1];
            postId = info[2].replace(".html", "");
        }

        // It is not mandatory on Craigslist to list a price; therefore,
        // this checks that one exists before setting price. Also, the
        // substring call removes the leading $. ie $450 -> 450
        if (post.select("span.price").size() > 0)
            price = Integer.parseInt(post.select("span.price").get(0).text().substring(1));

        location = post.select("span.pnr").select("small").text().replace("(", "").replace(")", "");
    }

    public String title() {
        return this.title;
    }

    public String county() {
        return this.county;
    }

    public String category() {
        return this.category;
    }

    public String link() {
        return this.link;
    }

    public String id() {
        return this.postId;
    }

    public String location() {
        return this.location;
    }

    @Override
    public String toString() {
        return "$" + price + " ($" + value + ") " + title + " " + link;
    }

    public int price() {
        return this.price;
    }

    public void setValue(double _value) {
        value = _value;
    }

    public boolean isFromSearch(Search potSearch) {
        return potSearch == search;
    }
}
