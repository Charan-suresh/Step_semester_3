import java.time.LocalDate;
import java.util.*;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

abstract class Employee {
    private final String id;
    private final String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getEmployeeType();
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String id, String name) {
        super(id, name);
    }

    @Override
    public String getEmployeeType() {
        return "Full-time";
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String id, String name) {
        super(id, name);
    }

    @Override
    public String getEmployeeType() {
        return "Part-time";
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String id, String name) {
        super(id, name);
    }

    @Override
    public String getEmployeeType() {
        return "Contract";
    }
}

class LeaveRequest {
    private final String requestId;
    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(String requestId, Employee employee, LocalDate startDate, LocalDate endDate) {
        this.requestId = requestId;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public String getRequestId() {
        return requestId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public boolean approve() {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot approve: Request is already " + status + ".");
            return false;
        }
        this.status = LeaveStatus.APPROVED;
        System.out.println("Leave request for " + employee.getName() + " approved. Status: " + status + ".");
        return true;
    }

    public boolean reject() {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot reject: Request is already " + status + ".");
            return false;
        }
        this.status = LeaveStatus.REJECTED;
        System.out.println("Leave request for " + employee.getName() + " rejected. Status: " + status + ".");
        return true;
    }

    public boolean setPending() {
        if (status == LeaveStatus.APPROVED || status == LeaveStatus.REJECTED) {
            System.out.println("Cannot change status: " + (status == LeaveStatus.APPROVED ? "Approved" : "Rejected") + " request cannot revert to Pending.");
            return false;
        }
        this.status = LeaveStatus.PENDING;
        return true;
    }
}

class LeaveManager {
    private int counter = 1;

    public LeaveRequest submitRequest(Employee employee, LocalDate start, LocalDate end) {
        LeaveRequest req = new LeaveRequest("LR-" + (counter++), employee, start, end);
        System.out.println("Leave request submitted by " + employee.getName() + " for " + start + " to " + end + ". Status: " + req.getStatus() + ".");
        return req;
    }

    public void approveRequest(LeaveRequest req) {
        if (req != null) {
            req.approve();
        }
    }

    public void rejectRequest(LeaveRequest req) {
        if (req != null) {
            req.reject();
        }
    }

    public void revertToPending(LeaveRequest req) {
        if (req != null) {
            req.setPending();
        }
    }
}

public class EmployeeLeaveManagement {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("E1", "John Doe");
        Employee jane = new PartTimeEmployee("E2", "Jane Smith");

        LeaveRequest req1 = manager.submitRequest(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.approveRequest(req1);

        LeaveRequest req2 = manager.submitRequest(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        manager.revertToPending(req1);
    }
}
