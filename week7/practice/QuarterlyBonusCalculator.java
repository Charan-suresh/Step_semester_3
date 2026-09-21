abstract class StaffMember {
    private double baseSalary;
    protected double bonusRate;

    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }

    public StaffMember(double baseSalary, double bonusRate) {
        this.baseSalary = (baseSalary >= 0) ? baseSalary : 0.0;
        this.bonusRate = bonusRate;
    }

    public abstract double calculateBonus();

    public double getSalary() {
        return baseSalary;
    }

    public void setSalary(double baseSalary) {
        if (baseSalary >= 0) {
            this.baseSalary = baseSalary;
        }
    }

    public static String getAuditIfApplicable(StaffMember s) {
        if (s instanceof Auditable) {
            Auditable auditable = (Auditable) s;
            return auditable.auditRecord();
        }
        return "No audit required";
    }
}

interface Auditable {
    String auditRecord();
}

class TeamLead extends StaffMember implements Auditable {
    private final int teamSize;

    public TeamLead(double baseSalary, int teamSize) {
        super(baseSalary);
        this.teamSize = teamSize;
    }

    public TeamLead(double baseSalary, double bonusRate, int teamSize) {
        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }

    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }

    @Override
    public String auditRecord() {
        return "TeamLead audit: " + teamSize + " team members, salary $" + getSalary();
    }
}

public class QuarterlyBonusCalculator {
    public static String getAuditIfApplicable(StaffMember s) {
        return StaffMember.getAuditIfApplicable(s);
    }

    public static void main(String[] args) {
        TeamLead t = new TeamLead(60000, 5);
        System.out.println(t.calculateBonus());

        TeamLead t2 = new TeamLead(60000, 0.20, 5);
        System.out.println(t2.calculateBonus());

        t.setSalary(-5000); // rejected, salary unchanged
        System.out.println("Salary after rejecting negative: " + t.getSalary());

        // upcasting: TeamLead stored as its parent type
        StaffMember ref = t;
        System.out.println(getAuditIfApplicable(ref));
    }
}
