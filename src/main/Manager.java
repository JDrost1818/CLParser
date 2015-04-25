package main;

import data.CraigslistUrls;
import dataHandlers.*;
import objects.Post;
import objects.Search;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Scanner;

public class Manager {

    private boolean testing = false;
    private String username = "";
    private String password = "";
    private String toEmail = "";

    private Parser parser;
    private Pricer pricer;
    private Translator translator;

    public static void main(String[] args) {
        Manager app = new Manager();
        app.run(args);
    }

    private ArrayList<Search> searches = new ArrayList<Search>();

    public Manager() {
        this.parser = new Parser();
        this.pricer = new Pricer();
        this.translator = new Translator();

        //addSearch(new Search(CraigslistUrls.VIDEO_GAMING.owner(), "minneapolis", "XBOX ONE", ""));
        addSearch(new Search(CraigslistUrls.CELL_PHONES.owner(), "minneapolis", "", ""));
        //addSearch(new Search(CraigslistUrls.ELECTRONICS.owner(), "minneapolis", "TV", "Projection"));
    }

    public void run(String[] args) {
        if (args.length > 0) {
            testing = true;
            username = args[0];
            password = args[1];
            toEmail = args[2];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Email: ");
            username = scanner.next();
            System.out.println("Password: ");
            password = scanner.next();
            System.out.println("Email Recipient: ");
            toEmail = scanner.next();
        }
        singleSearch(searches);
    }

    public void login(String _username, String _password) {
        this.username =_username;
        this.password = _password;
        this.toEmail = username;
    }

    public void singleSearch(ArrayList<Search> searches) {
        ArrayList<Post> newPosts = parser.parseCraigslist(searches);
        String priceSearch;
        int i = 0;
        for (Post curPost : newPosts) {
            if (i++ > 20) {
                break;
            }
            System.out.println(curPost);
            priceSearch = translator.translate(curPost);
            if (!priceSearch.equals(""))
                curPost.setValue(pricer.getItemPrice(priceSearch));
        }

        if (newPosts.size() > 0 && !username.equals(""))
            this.emailPosts(this.username, this.password, this.toEmail, searches, newPosts);
    }

    private void emailPosts(String user, String pass, String toEmail, ArrayList<Search> searches, ArrayList<Post> newPosts) {
        String message = "";
        for (Search curSearch : searches) {
            // Creates heading for each search category
            message += CraigslistUrls.titleFromKey(curSearch.category()) + "\n\n";
            for (Post curPost : newPosts) {
                if (curPost.isFromSearch(curSearch)) {
                    message += "\t" +  curPost + "\n";
                }
            }
            message += "\n";
        }
        Email.sendMail(user, pass, toEmail, message);
    }

    public void addSearch(Search newSearch) {
        searches.add(newSearch);
    }
}
