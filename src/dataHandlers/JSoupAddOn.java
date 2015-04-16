package dataHandlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JSoupAddOn {

    public static Document connect(String url) {
        Document doc = null;
        int numTries = 0;
        while (numTries < 5 && doc == null) {
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                System.out.println("Error while trying to connect to URL. Retrying");
                numTries++;
            }
        }
        return doc;
    }

    // Found on StackOverflow @
    // http://stackoverflow.com/questions/9958425/get-title-meta-description-content-using-url
    public static String getMetaTag(Document document, String attr) {
        Elements elements = document.select("meta[name=" + attr + "]");
        for (Element element : elements) {
            final String s = element.attr("content");
            if (s != null) return s;
        }
        elements = document.select("meta[property=" + attr + "]");
        for (Element element : elements) {
            final String s = element.attr("content");
            if (s != null) return s;
        }
        return null;
    }
}
