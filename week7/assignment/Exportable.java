class ExportCounter {
    static int totalExports = 0;
}

public interface Exportable {
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

    static void main(String[] args) {
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
