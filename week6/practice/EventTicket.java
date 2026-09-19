public class EventTicket {
    private static int counter = 1000;
    protected String attendeeId;
    protected double basePrice;
    protected double amountPaid;
    private double[] lateFeeHistory;
    private int lateFeeCount;
    public final String ticketId;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId");
        }
        this.attendeeId = attendeeId.trim();
        this.basePrice = basePrice;
        this.amountPaid = 0.0;
        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;
        counter++;
        this.ticketId = "TCK-" + counter;
    }

    public EventTicket(double basePrice) {
        this("ATTENDEE", basePrice);
    }

    public void pay(double amount) {
        this.amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    protected void applyLateFee(double amount) {
        this.basePrice += amount;
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

    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + getBalanceDue();
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        if (attendeeIds == null) {
            return "Registered: 0 | Rejected: 0";
        }
        int registered = 0;
        int rejected = 0;
        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        if (ticket instanceof WorkshopTicket) {
            return "Single inheritance child";
        }
        return "Base class";
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        if (tickets == null) {
            return 0.0;
        }
        double total = 0.0;
        for (EventTicket ticket : tickets) {
            if (ticket != null) {
                total += ticket.getBalanceDue();
            }
        }
        return total;
    }

    public static String batchPrint(EventTicket[] tickets) {
        if (tickets == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                continue;
            }
            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket wt = (WorkshopTicket) ticket;
                sb.append("Workshop | Track: ").append(wt.getTrack())
                  .append(" | Balance: ").append(wt.getBalanceDue())
                  .append(" [Track via downcast: ").append(wt.getTrack()).append("] | ");
            } else {
                sb.append("Standard | Balance: ").append(ticket.getBalanceDue()).append(" | ");
            }
        }
        return sb.toString();
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static int getTicketsIssued() {
        return counter - 1000;
    }

    public static String processNightlySettlement(EventTicket[] tickets) {
        if (tickets == null) {
            return "0 processed | 0 null skipped | 0 group | 0 individual";
        }
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                nullSkipped++;
                continue;
            }
            if (ticket instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
            processed++;
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
               group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        System.out.println(registerBatch(new String[]{"STU1", "ST1", "STU2", " ", "STU3"}, 500));
        System.out.println(isValidPromoCode("F123A"));
        System.out.println(isValidPromoCode("F12A"));
        System.out.println(isValidPromoCode("X123A"));
    }
}
