class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    public BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }

    public void printName() {
        System.out.println(name);
    }
}

public class LibraryMember {
    private static String libraryName = "City Central Library";
    private static int memberCount = 1000;

    private String name;
    private String memberId;
    private int booksIssued;

    public LibraryMember(String name, int booksIssued) {
        memberCount++;
        this.name = name;
        this.memberId = "LM-" + memberCount;
        this.booksIssued = booksIssued;
    }

    public void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    public static void printTotalMembers() {
        int total = memberCount - 1000;
        System.out.println("Total members: " + total);
    }

    public static void main(String[] args) {
        System.out.println("Broken version:");
        BrokenLibraryMember bm1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember bm2 = new BrokenLibraryMember("Rohan", "LM-1002", 5);
        bm1.printName();
        bm2.printName();

        System.out.println("\nFixed version:");
        LibraryMember m1 = new LibraryMember("Aditi", 2);
        LibraryMember m2 = new LibraryMember("Rohan", 5);
        m1.printMemberCard();
        m2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
