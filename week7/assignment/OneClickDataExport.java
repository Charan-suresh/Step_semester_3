class ExportCounter {
    static int totalExports = 0;
}

interface Exportable {
    String exportData();

    static int getTotalExports() {
        return ExportCounter.totalExports;
    }

    static void incrementExportCount() {
        ExportCounter.totalExports++;
    }

    static void exportAll(Exportable[] items) {
        if (items == null) {
            return;
        }
        for (Exportable item : items) {
            if (item != null) {
                System.out.println(item.exportData());
            }
        }
    }
}

class ReportGenerator implements Exportable {
    private final String reportName;

    public ReportGenerator(String reportName) {
        if (reportName == null || reportName.trim().isEmpty()) {
            throw new IllegalArgumentException("Report name cannot be blank");
        }
        this.reportName = reportName.trim();
    }

    public String getReportName() {
        return reportName;
    }

    @Override
    public String exportData() {
        Exportable.incrementExportCount();
        return "Exported report: " + reportName;
    }

    public static int getTotalExports() {
        return Exportable.getTotalExports();
    }
}

class UserProfile implements Exportable {
    private final String username;

    public UserProfile(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        this.username = username.trim();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String exportData() {
        Exportable.incrementExportCount();
        return "Exported profile: " + username;
    }

    public static int getTotalExports() {
        return Exportable.getTotalExports();
    }
}

public class OneClickDataExport {
    public static int getTotalExports() {
        return Exportable.getTotalExports();
    }

    public static void exportAll(Exportable[] items) {
        Exportable.exportAll(items);
    }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        System.out.println(r.exportData());

        UserProfile u = new UserProfile("jane_doe");
        System.out.println(u.exportData());

        // upcasting: ReportGenerator stored as the Exportable interface type
        Exportable ref = r;
        exportAll(new Exportable[]{ ref, u });

        System.out.println("Total Exports: " + getTotalExports());
    }
}
