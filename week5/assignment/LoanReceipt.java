public class LoanReceipt {
    private static String systemVersion;

    static {
        systemVersion = "PAGE-TURNER-V1";
    }

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid memberId");
        }
        if (bookIds == null) {
            throw new IllegalArgumentException("bookIds cannot be null");
        }
        for (String id : bookIds) {
            if (!isValidBookId(id)) {
                throw new IllegalArgumentException("Invalid bookId: " + id);
            }
        }
        this.memberId = memberId;
        this.bookIds = bookIds.clone();
    }

    private static boolean isValidBookId(String id) {
        if (id == null || id.length() != 6) {
            return false;
        }
        if (!id.startsWith("BK-")) {
            return false;
        }
        for (int i = 3; i < 6; i++) {
            char ch = id.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (!isValidBookId(newId)) {
            throw new IllegalArgumentException("Invalid bookId: " + newId);
        }
        String[] newBookIds = bookIds.clone();
        newBookIds[index] = newId;
        return new LoanReceipt(this.memberId, newBookIds);
    }

    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
            processed++;
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
               referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]);

        LoanReceipt[] batch = new LoanReceipt[]{
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(batch));
    }
}
