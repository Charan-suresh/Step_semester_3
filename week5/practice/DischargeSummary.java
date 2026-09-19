public class DischargeSummary {
    private static String facilityCode;

    static {
        facilityCode = "MEDITRACK-MAIN";
    }

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid patientId");
        }
        if (medicationCodes == null) {
            throw new IllegalArgumentException("Medication codes cannot be null");
        }
        for (String code : medicationCodes) {
            if (!isValidMedicationCode(code)) {
                throw new IllegalArgumentException("Invalid medication code: " + code);
            }
        }
        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    private static boolean isValidMedicationCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (!code.startsWith("MED-")) {
            return false;
        }
        char ch = code.charAt(4);
        return ch >= 'A' && ch <= 'Z';
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (!isValidMedicationCode(newCode)) {
            throw new IllegalArgumentException("Invalid medication code: " + newCode);
        }
        String[] newCodes = medicationCodes.clone();
        newCodes[index] = newCode;
        return new DischargeSummary(this.patientId, newCodes);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        if (summaries == null) {
            return "0 processed | 0 null skipped | 0 critical-care | 0 routine";
        }
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                nullSkipped++;
                continue;
            }
            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
            processed++;
        }

        return processed + " processed | " + nullSkipped + " null skipped | " +
               criticalCare + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(d.getMedicationCodes()[0]);

        DischargeSummary[] batch = new DischargeSummary[]{
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };
        System.out.println(processNightlyBatch(batch));
    }
}
