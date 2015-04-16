import dataHandlers.JSoupAddOn;
import org.jsoup.nodes.Document;

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
    private double timePosted;

    // Contact Information
    private String ownerName;
    private String phone;
    private String email;

    private boolean haveContacted = false;

    public Post(Document post) {
        title = JSoupAddOn.getMetaTag(post, "og:title");
        description = JSoupAddOn.getMetaTag(post, "og:description");
        link = JSoupAddOn.getMetaTag(post, "og:url");

        // Gets county, category, postId (all from URL)
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

        location = post.select("h2.postingtitle").select("small").text().replace("(", "").replace(")", "");

        // This is the link to the html that actually contains the
        // contact information for the owner of the post.
        String contactInfoLink = post.select("a#replylink").attr("abs:href");
        Document contactPost = JSoupAddOn.connect(contactInfoLink);
        email = contactPost.select("div.anonemail").text();
        System.out.println(email);
    }
}
