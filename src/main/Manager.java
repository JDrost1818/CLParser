package main;

import data.CraigslistUrls;
import dataHandlers.*;
import objects.Post;
import objects.Search;

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

        addSearch(new Search(CraigslistUrls.VIDEO_GAMING.owner(), "minneapolis", "XBOX ONE", ""));
        addSearch(new Search(CraigslistUrls.CELL_PHONES.owner(), "minneapolis", "", ""));
        addSearch(new Search(CraigslistUrls.ELECTRONICS.owner(), "minneapolis", "TV", "Projection"));
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
        loop();
    }

    private void loop() {
        while (true) try {
            // Do a search and then sleep
            // for 20 minutes
            singleSearch(searches);
            System.out.println("\nSleeping...");
            Thread.sleep(1200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void login(String _username, String _password) {
        this.username =_username;
        this.password = _password;
        this.toEmail = username;
    }

    public void singleSearch(ArrayList<Search> searches) {
        ArrayList<Post> newPosts = parser.parseCraigslist(searches);
        int i = 0;
        String priceSearch;
        for (Post curPost : newPosts) {
            if (CraigslistUrls.CELL_PHONES.contains(curPost.category())) {
                System.out.println(i++ + " Finding a price");
                priceSearch = translator.translate(curPost);
                if (!priceSearch.equals(""))
                    curPost.setValue(pricer.getItemPrice(priceSearch));
            }
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
