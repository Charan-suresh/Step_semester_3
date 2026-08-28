class CompanyEmployee {
    private String empId;
    private String empName;
    private double salary;

    public CompanyEmployee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public double getEffectivePay() {
        return salary;
    }
}

class CompanyManager extends CompanyEmployee {
    private double teamBonus;

    public CompanyManager(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    @Override
    public double getEffectivePay() {
        return getSalary() + teamBonus;
    }
}

class AssignedParkingSlot {
    private String slotNo;

    public AssignedParkingSlot(String slotNo) {
        this.slotNo = slotNo;
    }

    public String getSlotNo() {
        return slotNo;
    }
}

public class CompanyEmployeeRecord {
    public static int totalRecords = 0;

    private String name;
    private String empId;
    private CompanyEmployee employee;
    private AssignedParkingSlot slot;

    public CompanyEmployeeRecord(String name, String empId, CompanyEmployee employee, AssignedParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    public String fullProfile() {
        String slotStr = (slot != null) ? slot.getSlotNo() : "no parking assigned";
        return name + " | Pay: Rs " + employee.getEffectivePay() + " | Slot: " + slotStr;
    }

    public static void main(String[] args) {
        CompanyManager m1 = new CompanyManager("M101", "Divya", 70000.0, 8000.0);
        CompanyEmployee e2 = new CompanyEmployee("E102", "Karan", 40000.0);
        CompanyEmployee e3 = new CompanyEmployee("E103", "Meera", 10000.0);

        AssignedParkingSlot slotA1 = new AssignedParkingSlot("A1");
        AssignedParkingSlot slotA2 = new AssignedParkingSlot("A2");

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "M101", m1, slotA1);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E102", e2, slotA2);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "E103", e3, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
