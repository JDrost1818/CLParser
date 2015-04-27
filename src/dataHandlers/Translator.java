package dataHandlers;

import data.CraigslistUrls;
import objects.Post;

import java.util.HashMap;

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
        Node curNode;

        if (CraigslistUrls.CELL_PHONES.contains(cat)) {
            curNode = phonesNode;
        } else {
            // Focusing solely on cell phones right now
            curNode = null;
        }

        while (curNode != null) {
            if (!curNode.translatedText.equals(""))
                translatedSearch += curNode.translatedText + " ";
            curNode = searchNode(curNode, post);
        }

        return translatedSearch;
    }

    public String[] translateConsole(Post post) {
        return null;
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

        Node[] colorNodes = new Node[] {
                new Node("space grey",  "Space Grey",   carrierNodes),
                new Node("space gray",  "Space Grey",   carrierNodes),
                new Node("black",       "Black",        carrierNodes),
                new Node("grey",        "Grey",         carrierNodes),
                new Node("gray",        "Grey",         carrierNodes),
                new Node("white",       "White",        carrierNodes),
                new Node("gold",        "Gold",         carrierNodes),
                new Node(NONE_FOUND,    "",             carrierNodes)
        };

        Node[] capacityNodes = new Node[] {
                new Node("128gb",   "128gb",    colorNodes),
                new Node("128",     "128gb",    colorNodes),
                new Node("64gb",    "64gb",     colorNodes),
                new Node("64",      "64gb",     colorNodes),
                new Node("32gb",    "32gb",     colorNodes),
                new Node("32",      "32gb",     colorNodes),
                new Node("16gb",    "16gb",     colorNodes),
                new Node("16",      "16gb",     colorNodes),
                new Node("8gb",     "8gb",      colorNodes),
                new Node("8",       "8gb",      colorNodes),
                new Node(NONE_FOUND, "",        colorNodes),
        };

        return new Node("Phones", "", new Node[] {
                new Node("iphone", "Apple iPhone", new Node[] {
                        new Node("6 plus",  "6 Plus",   capacityNodes),
                        new Node("6+",      "6 Plus",   capacityNodes),
                        new Node(" 6 ",     "6",        capacityNodes),
                        new Node("5s",      "5S",       capacityNodes),
                        new Node("5c",      "5C",       capacityNodes),
                        new Node(" 5 ",     "5",        capacityNodes),
                        new Node("4s",      "4S",       capacityNodes),
                        new Node(" 4 ",     "4",        capacityNodes)
                }),
                new Node("nexus", "Google Nexus", new Node[] {
                        new Node(" 6 ", "6", capacityNodes),
                        new Node(" 5 ", "5", capacityNodes),
                        new Node(" 4 ", "4", capacityNodes)
                }),
                new Node("galaxy", "Samsung Galaxy", new Node[] {
                        new Node("s6 edge",     "S6 Edge",  capacityNodes),
                        new Node("s6",          "S6",       capacityNodes),
                        new Node(" 6 ",         "S6",       capacityNodes),
                        new Node("note 4",      "Note 4",   capacityNodes),
                        new Node("note iv",     "Note 4",   capacityNodes),
                        new Node("s5",          "S5",       capacityNodes),
                        new Node(" 5 ",         "S5",       capacityNodes),
                        new Node("note 3",      "Note 3",   capacityNodes),
                        new Node("note iii",    "Note 3",   capacityNodes),
                        new Node("s4",          "S4",       capacityNodes),
                        new Node(" 4 ",         "S4",       capacityNodes),
                        new Node("note 2",      "Note 2",   capacityNodes),
                        new Node("note ii",     "Note 2",   capacityNodes),
                        new Node("s3",          "S3",       capacityNodes),
                        new Node(" 3 ",         "S3",       capacityNodes),
                        new Node("note",        "Note",     capacityNodes),
                        new Node("s2",          "S2",       capacityNodes),
                        new Node(" 2 ",         "S2",       capacityNodes),
                }),
                new Node("htc one", "HTC One", new Node[] {
                        new Node("m9", "M9", capacityNodes),
                        new Node("m8", "M8", capacityNodes),
                        new Node("m7", "M7", capacityNodes)
                }),
                new Node("moto", "Motorola Moto", new Node[] {
                        new Node("x", "X", new Node[] {
                                new Node("2014",    "2014", capacityNodes),
                                new Node("2nd",     "2014", capacityNodes),
                                new Node("second",  "2014", capacityNodes),
                                new Node("2013",    "2013", capacityNodes),
                                new Node("1st",     "2013", capacityNodes),
                                new Node("first",   "2013", capacityNodes),
                        }),
                        new Node("g", "G", new Node[] {
                                new Node("2014",    "2014", capacityNodes),
                                new Node("2nd",     "2014", capacityNodes),
                                new Node("second",  "2014", capacityNodes),
                                new Node("2013",    "2013", capacityNodes),
                                new Node("1st",     "2013", capacityNodes),
                                new Node("first",   "2013", capacityNodes),
                        })
                }),
                new Node("lg", "LG", new Node[] {
                        new Node("g4", "G4", capacityNodes),
                        new Node("g3", "G3", capacityNodes),
                        new Node("g2", "G2", capacityNodes),
                })
        });
    }
}
