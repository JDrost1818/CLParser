package dataHandlers;

import data.CraigslistUrls;
import objects.Post;

public class Translator {

    private class Node {
        private String info;
        private String translatedText;
        private Node[] nextNodes;

        private Node(String _info, String _translatedText, Node[] _nextNodes) {
            info = _info;
            translatedText = _translatedText;
            nextNodes = _nextNodes;
        }
    }

    private Node defaultNode;
    private Node electronicsNode;
    private Node videoGamesNode;
    private Node phonesNode;

    private String NONE_FOUND = "None Found";

    public Translator() {
        phonesNode = buildPhonesNode();

        defaultNode = new Node("default", "", new Node[] { phonesNode });
    }

    public String translate(Post post) {
        return translate(post.title(), post.category());
    }

    public String translate(String post, String cat) {
        String translatedSearch = "";
        post += " ";  // For cases like "Apple iPhone 4"
        int level = 1;
        Node curNode;

        if (CraigslistUrls.CELL_PHONES.contains(cat)) {
            curNode = phonesNode;
        } else if (CraigslistUrls.VIDEO_GAMING.contains(cat)) {
            curNode = videoGamesNode;
        } else if (CraigslistUrls.ELECTRONICS.contains(cat)) {
            curNode = videoGamesNode;
        } else {
            curNode = defaultNode;
            level = 0;
        }

        while (curNode != null) {
            if (!curNode.translatedText.equals(""))
                translatedSearch += curNode.translatedText + " ";
            curNode = searchNode(curNode, post);
            level++;
        }

        return translatedSearch;
    }

    private Node searchNode(Node searchNode, String searchText) {
        if (searchNode.nextNodes != null) {
            for (Node curSearchNode : searchNode.nextNodes) {
                if (searchText.toLowerCase().contains(curSearchNode.info)) {
                    return curSearchNode;
                } else if (curSearchNode.info.equals(NONE_FOUND)) {
                    return curSearchNode;
                }
            }
        }
        return null;
    }

    private Node buildPhonesNode() {
        Node[] carrierNodes = new Node[] {
                new Node("unlocked",        "Unlocked", null),
                new Node("all carriers",    "Unlocked", null),
                new Node("any network",     "Unlocked", null),
                new Node("verizon",         "Verizon",  null),
                new Node("at&t",            "AT&T",     null),
                new Node("att",             "AT&T",     null),
                new Node("tmobile",         "TMobile",  null),
                new Node("t-mobile",        "TMobile",  null),
                new Node("sprint",          "Sprint",   null),
                new Node(NONE_FOUND,        "",         null)
        };

        Node[] capacityNodes = new Node[] {
                new Node("128gb",   "128gb",    carrierNodes),
                new Node("128",     "128gb",    carrierNodes),
                new Node("64gb",    "64gb",     carrierNodes),
                new Node("64",      "64gb",     carrierNodes),
                new Node("32gb",    "32gb",     carrierNodes),
                new Node("32",      "32gb",     carrierNodes),
                new Node("16gb",    "16gb",     carrierNodes),
                new Node("16",      "16gb",     carrierNodes),
                new Node("8gb",     "8gb",      carrierNodes),
                new Node("8",       "8gb",      carrierNodes),
                new Node(NONE_FOUND, "",        carrierNodes),
        };

        Node[] colorNodes = new Node[] {
                new Node("black",       "Black",        capacityNodes),
                new Node("white",       "White",        capacityNodes),
                new Node("gold",        "Gold",         capacityNodes),
                new Node("space grey",  "Space Grey",   capacityNodes),
                new Node("space gray",  "Space Grey",   capacityNodes),
                new Node(NONE_FOUND,    "",             capacityNodes)
        };

        return new Node("Phones", "", new Node[] {
                new Node("iphone", "Apple iPhone", new Node[] {
                        new Node("6 plus",  "6 Plus",   colorNodes),
                        new Node("6+",      "6 Plus",   colorNodes),
                        new Node(" 6 ",     "6",        colorNodes),
                        new Node("5s",      "5S",       colorNodes),
                        new Node(" 5 ",     "5",        colorNodes),
                        new Node("4s",      "4S",       colorNodes),
                        new Node(" 4 ",     "4",        colorNodes)
                }),
                new Node("galaxy", "Samsung Galaxy", new Node[] {
                        new Node("s6 edge",     "S6 Edge",  colorNodes),
                        new Node("s6",          "S6",       colorNodes),
                        new Node(" 6 ",         "S6",       colorNodes),
                        new Node("note 4",      "Note 4",   colorNodes),
                        new Node("note iv",     "Note 4",   colorNodes),
                        new Node("s5",          "S5",       colorNodes),
                        new Node(" 5 ",         "S5",       colorNodes),
                        new Node("note 3",      "Note 3",   colorNodes),
                        new Node("note iii",    "Note 3",   colorNodes),
                        new Node("s4",          "S4",       colorNodes),
                        new Node(" 4 ",         "S4",       colorNodes),
                        new Node("note 2",      "Note 2",   colorNodes),
                        new Node("note ii",     "Note 2",   colorNodes),
                        new Node("s3",          "S3",       colorNodes),
                        new Node(" 3 ",         "S3",       colorNodes),
                        new Node("note",        "Note",     colorNodes),
                        new Node("s2",          "S2",       colorNodes),
                        new Node(" 2 ",         "S2",       colorNodes),
                }),
                new Node("htc one", "HTC One", new Node[] {
                        new Node("m9", "M9", colorNodes),
                        new Node("m8", "M8", colorNodes),
                        new Node("m7", "M7", colorNodes)
                }),
                new Node("moto", "Motorola Moto", new Node[] {
                        new Node("x", "X", new Node[] {
                                new Node("2014",    "2014", colorNodes),
                                new Node("2nd",     "2014", colorNodes),
                                new Node("second",  "2014", colorNodes),
                                new Node("2013",    "2013", colorNodes),
                                new Node("1st",     "2013", colorNodes),
                                new Node("first",   "2013", colorNodes),
                        }),
                        new Node("g", "G", new Node[] {
                                new Node("2014",    "2014", colorNodes),
                                new Node("2nd",     "2014", colorNodes),
                                new Node("second",  "2014", colorNodes),
                                new Node("2013",    "2013", colorNodes),
                                new Node("1st",     "2013", colorNodes),
                                new Node("first",   "2013", colorNodes),
                        })
                }),
                new Node("nexus", "Google Nexus", new Node[] {
                        new Node(" 6 ", "6", colorNodes),
                        new Node(" 5 ", "5", colorNodes),
                        new Node(" 4 ", "4", colorNodes)
                })
        });
    }
}
