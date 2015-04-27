import data.CraigslistUrls;
import dataHandlers.Translator;
import objects.Post;
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

    @Test
    public void translateConsoleTest() {
        Translator translator = new Translator();

        Post consolePost = new Post("x-box one with kinect",
                                    "Looking to get rid of all my Xbox stuff. I have : Xbox one 500 GB all cords and Kinect. I put a carbon fiber skin on the Xbox and Kinect, looks really cool. \n" +
                                    "3 Xbox one wireless controllers\n" +
                                    "1 Battery charging station with 2 batteries \n" +
                                    "1 Xbox brand rechargeable battery\n" +
                                    "Far cry 4\n" +
                                    "Madden 25\n" +
                                    "Saints Row 4\n" +
                                    "Forza 5\n" +
                                    "Halo 4\n" +
                                    "GTA V\n",
                                    "", "", "", "", "", 0);

        String[] items = translator.translateConsole(consolePost);

        String[] expectedItems = new String[] {
                "Xbox One with Kinect",
                "Xbox One Controller",
                "Xbox One Controller",
                "Xbox One Controller",
                "Far Cry 4",
                "Madden 25",
                "Saints Row 4",
                "Forza 5",
                "Halo 4",
                "Grand Theft Auto 5"
        };

        for (int i=0; i < items.length; i++) {
            assertEquals(expectedItems[i], items[i]);
        }
    }
}
