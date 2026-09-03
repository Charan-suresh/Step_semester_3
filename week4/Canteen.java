public class Canteen {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public int compareTo(Canteen other) {
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }
        int codeComparison = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeComparison != 0) {
            return codeComparison;
        }
        return Integer.compare(this.canteenName.length(), other.canteenName.length());
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null) {
            return null;
        }
        Canteen[] sorted = canteens.clone();
        for (int i = 0; i < sorted.length - 1; i++) {
            for (int j = 0; j < sorted.length - 1 - i; j++) {
                if (sorted[j].compareTo(sorted[j + 1]) > 0) {
                    Canteen temp = sorted[j];
                    sorted[j] = sorted[j + 1];
                    sorted[j + 1] = temp;
                }
            }
        }
        return sorted;
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public int getTrustScore() {
        return trustScore;
    }

    public static void main(String[] args) {
        Canteen[] list = new Canteen[] {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = rankCanteens(list);
        System.out.print("[");
        for (int i = 0; i < ranked.length; i++) {
            System.out.print("\"" + ranked[i].getCanteenCode() + "\"");
            if (i < ranked.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
