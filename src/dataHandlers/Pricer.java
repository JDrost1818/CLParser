package dataHandlers;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Pricer {

    public double getItemPrice(String searchTerm) {
        String url = getThePriceGeekUrl(searchTerm);
        Document doc = JSoupAddOn.connect(url);
        Elements el = doc.select("div.span12");
        if (el.size() > 0) {
            return Double.parseDouble(el.get(0).text().split("\\$")[1]);
        }
        return 0;
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
