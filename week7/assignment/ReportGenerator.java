public class ReportGenerator implements Exportable {
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
