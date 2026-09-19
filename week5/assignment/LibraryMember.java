public class LibraryMember {
    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;
    private boolean premiumMember;
    private String securityAnswer;

    public LibraryMember() {
        this(null, null);
    }

    public LibraryMember(String displayName) {
        this(null, displayName);
    }

    public LibraryMember(String membershipId, String displayName) {
        this.membershipId = membershipId;
        this.displayName = displayName;
        this.branchCode = "MAIN";
        this.finesOwed = 0.0;
        this.premiumMember = false;
        this.securityAnswer = null;
    }

    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid membershipId");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
        this.premiumMember = false;
        this.securityAnswer = null;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public double getFinesOwed() {
        return finesOwed;
    }

    public void setFinesOwed(double finesOwed) {
        this.finesOwed = finesOwed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {
        this.securityAnswer = answer;
    }

    public static void main(String[] args) {
        LibraryMember member = new LibraryMember("LB94", "BR1", 0, "Priya Nair");
        System.out.println(member.getMembershipId() + " created.");
        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        System.out.println(new LibraryMember("Priya Nair").getMembershipId());
        System.out.println(new LibraryMember("LIB-8841", "Priya Nair").getMembershipId());

        LibraryMember m = new LibraryMember();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000");
        System.out.println(m.getMembershipId());
    }
}
