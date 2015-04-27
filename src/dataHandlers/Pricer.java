package dataHandlers;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class Pricer {

    private HashMap<String, Double> prices = new HashMap<>();

    public double getItemPrice(String searchTerm) {
        double price = 0;

        // First attempts to get price from the database
        // of prices that we have already found
        Double hashedPrice = prices.get(searchTerm);
        if (hashedPrice != null) {
            System.out.println("Found hashed price");
            return hashedPrice;
        }

        // If we can't find the price in the database,
        // pulls the price from web and then caches it
        String url = getThePriceGeekUrl(searchTerm);
        Document doc = JSoupAddOn.connect(url);
        if (doc != null) {
            Elements el = doc.select("div.span12");
            if (el.size() > 0) {
                price =  Double.parseDouble(el.get(0).text().split("\\$")[1]);
                prices.put(searchTerm, price);
            }
        }

        return price;
    }

    public double getItemsPrice(String[] searchTerms) {
        double total = 0;
        for (String searchTerm : searchTerms) {
            total += getItemPrice(searchTerm);
        }
        return total;
    }

    private String getThePriceGeekUrl(String searchTerm) {
        String[] terms = searchTerm.split(" ");
        String url = "http://www.thepricegeek.com/results/";
        for (int i=0; i < terms.length; i++) {
            url += terms[i];
            if (i < terms.length-1)
                url += "+";
        }
        url += "?country=us";
        return url;
    }

}
