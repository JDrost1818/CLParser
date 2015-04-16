package data;

public class DATA {

    public static String pageIds = "";

    public static boolean alreadyVisited(String pageId) {
        return pageIds.contains(pageId);
    }

    public static void addPage(String pageId) {
        pageIds += pageId + " ";
    }
}
