package data;

public enum CraigslistUrls {

    ALL               (new String[] {"sss", "sso", "ssq", "All"}),
    ANTIQUES          (new String[] {"ata", "atq", "atd", "Antiques"}),
    APPLIANCES        (new String[] {"ppa", "app", "ppo", "Appliances"}),
    ARTS_AND_CRAFTS   (new String[] {"ara", "art", "ard", "Arts & Crafts"}),
    ATV_UTVS_SNOW     (new String[] {"sna", "snw", "snd", "ATVs UTVs & Snow"}),
    AUTO_PARTS        (new String[] {"pta", "pts", "ptd", "Auto Parts"}),
    BABY_AND_KID      (new String[] {"baa", "bab", "bad", "Baby & Kid"}),
    BARTER            (new String[] {"bar", "bar", "bar", "Barter"}),
    BEAUTY_AND_HEALTH (new String[] {"haa", "hab", "had", "Beauty & Health"}),
    BIKES             (new String[] {"bia", "bik", "bid", "Bikes"}),
    BIKE_PARTS        (new String[] {"bip", "bop", "bdp", "Bike Parts"}),
    BOATS             (new String[] {"boo", "boa", "bod", "Boats"}),
    BOAT_PARTS        (new String[] {"bpa", "bpo", "bpd", "Boat Parts"}),
    BOOKS             (new String[] {"bka", "bks", "bpd", "Books"}),
    BUSINESS          (new String[] {"bfa", "bfs", "bfd", "Business"}),
    CARS_AND_TRUCKS   (new String[] {"cta", "cto", "ctd", "Cars & Trucks"}),
    CDS_DVD_VHS       (new String[] {"ema", "emd", "emq", "CDs DVDs & VHS"}),
    CELL_PHONES       (new String[] {"moa", "mob", "mod", "Cell Phones"}),
    CLOTHES_AND_ACC   (new String[] {"cla", "clo", "cld", "Clothes & Accessories"}),
    COLLECTIBLES      (new String[] {"cba", "clt", "cbd", "Collectibles"}),
    COMPUTERS         (new String[] {"sya", "sys", "syd", "Computers"}),
    COMPUTER_PARTS    (new String[] {"syp", "sop", "sdp", "Computer Parts"}),
    ELECTRONICS       (new String[] {"ela", "ele", "eld", "Electronics"}),
    FARM_AND_GARDEN   (new String[] {"gra", "grd", "grq", "Farm & Garden"}),
    FREE              (new String[] {"zip", "zip", "zip", "Free"}),
    FURNITURE         (new String[] {"fua", "fuo", "fud", "Furniture"}),
    GARAGE_SALE       (new String[] {"gms", "gms", "gms", "Garage Sale"}),
    GENERAL           (new String[] {"foa", "for", "fod", "General"}),
    HEAVY_EQUIP       (new String[] {"hva", "hvo", "hvd", "Heavy Equipment"}),
    HOUSEHOLD         (new String[] {"hsa", "hsh", "hsd", "Household"}),
    JEWELRY           (new String[] {"jwa", "jwl", "jwd", "Jewelry"}),
    MATERIALS         (new String[] {"maa", "mat", "mad", "Materials"}),
    MOTORCYCLES       (new String[] {"mca", "mcy", "mcd", "Motorcycles"}),
    MOTORCYCLE_PARTS  (new String[] {"mpa", "mpo", "mpd", "Motorcycle Parts"}),
    MUSIC_INSTR       (new String[] {"msa", "msg", "msd", "Music Instruments"}),
    PHOTO_AND_VIDEO   (new String[] {"pha", "pho", "phd", "Photo & Video"}),
    RVS_AND_CAMP      (new String[] {"rva", "rvs", "rvd", "RVs & Camping"}),
    SPORTING          (new String[] {"sga", "spo", "sgd", "Sporting"}),
    TICKETS           (new String[] {"tia", "tix", "tid", "Tickets"}),
    TOOLS             (new String[] {"tla", "tls", "tld", "Tools"}),
    TOYS_AND_GAMES    (new String[] {"taa", "tag", "tad", "Toys & Games"}),
    VIDEO_GAMING      (new String[] {"vga", "vgm", "vgd", "Video Gaming"}),
    WANTED            (new String[] {"waa", "wan", "wad", "Wanted"});

    private String[] urls;
    CraigslistUrls (String[] _urls){
        this.urls = _urls;
    }

    public String all() {
        return this.urls[0];
    }

    public String owner() {
        return this.urls[1];
    }

    public String dealer() {
        return this.urls[2];
    }

    public boolean contains(String key) {
        return this.urls[0].equals(key) || this.urls[1].equals(key) || this.urls[2].equals(key);
    }

    public static String titleFromKey(String key) {
        for (CraigslistUrls category : CraigslistUrls.values()) {
            if (category.urls[0].equals(key) || category.urls[1].equals(key) || category.urls[2].equals(key)) {
                return category.urls[3];
            }
        }
        return null;
    }
}
