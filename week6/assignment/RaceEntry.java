public class RaceEntry {
    private static int bibCounter = 0;
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;
    private double[] lateFeeHistory;
    private int lateFeeCount;
    public final String entryCode;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber");
        }
        this.bibNumber = bibNumber.trim();
        this.entryFee = entryFee;
        this.amountPaid = 0.0;
        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;
        bibCounter++;
        this.entryCode = "ENTRY-" + bibCounter;
    }

    public void pay(double amount) {
        this.amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    protected void applyLateFee(double amount) {
        this.entryFee += amount;
        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount++] = amount;
        }
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[lateFeeCount];
        for (int i = 0; i < lateFeeCount; i++) {
            copy[i] = lateFeeHistory[i];
        }
        return copy;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        if (bibNumbers == null) {
            return "Registered: 0 | Rejected: 0";
        }
        int registered = 0;
        int rejected = 0;
        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        if (entry instanceof RunnerEntry) {
            return "Single inheritance child";
        }
        return "Base class";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        if (entries == null) {
            return 0.0;
        }
        double total = 0.0;
        for (RaceEntry entry : entries) {
            if (entry != null) {
                total += entry.getBalanceDue();
            }
        }
        return total;
    }

    public static String announceAll(RaceEntry[] entries) {
        if (entries == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (RaceEntry entry : entries) {
            if (entry == null) {
                continue;
            }
            sb.append(entry.announce());
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry rt = (RelayTeamEntry) entry;
                sb.append(" [Team size via downcast: ").append(rt.getTeamSize()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'M') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static String settleNight(RaceEntry[] entries) {
        if (entries == null) {
            return "0 processed | 0 null skipped | 0 relay | 0 individual";
        }
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }
            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
            processed++;
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
               relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        System.out.println(registerBatch(new String[]{"BIB1", "B1", "BIB2"}, 80));
        System.out.println(isValidDiscountCode("M123A"));
        System.out.println(isValidDiscountCode("M12A"));
        System.out.println(isValidDiscountCode("X123A"));
    }
}
