import data.CraigslistUrls;
import dataHandlers.Translator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TranslatorTests {

    @Test
    public void translateTitlePhoneTest() {
        Translator translator = new Translator();

        String[] titles = new String[] {
                "iPhone 4 Verizon",
                "HTC One M8 16GB Sealed *Unlocked to all networks",
                "Moto x 2nd gen unlocked",
                "BRAND NEW IPHONE 6 16GB SPACE GREY UNLOCKED",
                "Apple Iphone 4 unlocked for Tmobile",
                "TMobile Samsung Galaxy 2",
                "Samsung Galaxy S3 for Tmobile",
                "iPhone 5 | 16 GB | Unlocked | T-Mobile/Verizon/GSM | iOS 8",
                "Sprint Samsung Galaxy S5",
                "SAMSUNG GALAXY S4 16GB WHITE ANY NETWORK NEW"
        };

        String[] expectedTranslations = new String[] {
                "Apple iPhone 4 Verizon ",
                "HTC One M8 16gb Unlocked ",
                "Motorola Moto X 2014 Unlocked ",
                "Apple iPhone 6 Space Grey 16gb Unlocked ",
                "Apple iPhone 4 Unlocked ",
                "Samsung Galaxy S2 TMobile ",
                "Samsung Galaxy S3 TMobile ",
                "Apple iPhone 5 16gb Unlocked ",
                "Samsung Galaxy S5 Sprint ",
                "Samsung Galaxy S4 White 16gb Unlocked "
        };

        for (int i=0; i < titles.length; i++) {
            assertEquals(expectedTranslations[i], translator.translate(titles[i], CraigslistUrls.CELL_PHONES.all()));
        }
    }
}
