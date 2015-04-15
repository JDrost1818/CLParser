package data;

public enum CraigslistUrls {

    ALL               (new String[] {"sss", "sso", "ssq"}),
    ANTIQUES          (new String[] {"ata", "atq", "atd"}),
    APPLIANCES        (new String[] {"ppa", "app", "ppo"}),
    ARTS_AND_CRAFTS   (new String[] {"ara", "art", "ard"}),
    ATV_UTVS_SNOW     (new String[] {"sna", "snw", "snd"}),
    AUTO_PARTS        (new String[] {"pta", "pts", "ptd"}),
    BABY_AND_KID      (new String[] {"baa", "bab", "bad"}),
    BARTER            (new String[] {"bar", "bar", "bar"}),
    BEAUTY_AND_HEALTH (new String[] {"haa", "hab", "had"}),
    BIKES             (new String[] {"bia", "bik", "bid"}),
    BIKE_PARTS        (new String[] {"bip", "bop", "bdp"}),
    BOATS             (new String[] {"boo", "boa", "bod"}),
    BOAT_PARTS        (new String[] {"bpa", "bpo", "bpd"}),
    BOOKS             (new String[] {"bka", "bks", "bpd"}),
    BUSINESS          (new String[] {"bfa", "bfs", "bfd"}),
    CARS_AND_TRUCKS   (new String[] {"cta", "cto", "ctd"}),
    CDS_DVD_VHS       (new String[] {"ema", "emd", "emq"}),
    CELL_PHONES       (new String[] {"moa", "mob", "mod"}),
    CLOTHES_AND_ACC   (new String[] {"cla", "clo", "cld"}),
    COLLECTIBLES      (new String[] {"cba", "clt", "cbd"}),
    COMPUTERS         (new String[] {"sya", "sys", "syd"}),
    COMPUTER_PARTS    (new String[] {"syp", "sop", "sdp"}),
    ELECTRONICS       (new String[] {"ela", "ele", "eld"}),
    FARM_AND_GARDEN   (new String[] {"gra", "grd", "grq"}),
    FREE              (new String[] {"zip", "zip", "zip"}),
    FURNITURE         (new String[] {"fua", "fuo", "fud"}),
    GARAGE_SALE       (new String[] {"gms", "gms", "gms"}),
    GENERAL           (new String[] {"foa", "for", "fod"}),
    HEAVY_EQUIP       (new String[] {"hva", "hvo", "hvd"}),
    HOUSEHOLD         (new String[] {"hsa", "hsh", "hsd"}),
    JEWELRY           (new String[] {"jwa", "jwl", "jwd"}),
    MATERIALS         (new String[] {"maa", "mat", "mad"}),
    MOTORCYCLES       (new String[] {"mca", "mcy", "mcd"}),
    MOTORCYCLE_PARTS  (new String[] {"mpa", "mpo", "mpd"}),
    MUSIC_INSTR       (new String[] {"msa", "msg", "msd"}),
    PHOTO_AND_VIDEO   (new String[] {"pha", "pho", "phd"}),
    RVS_AND_CAMP      (new String[] {"rva", "rvs", "rvd"}),
    SPORTING          (new String[] {"sga", "spo", "sgd"}),
    TICKETS           (new String[] {"tia", "tix", "tid"}),
    TOOLS             (new String[] {"tla", "tls", "tld"}),
    TOYS_AND_GAMES    (new String[] {"taa", "tag", "tad"}),
    VIDEO_GAMING      (new String[] {"vga", "vgm", "vgd"}),
    WANTED            (new String[] {"waa", "wan", "wad"});

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
}
