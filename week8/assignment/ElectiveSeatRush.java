import java.util.*;

abstract class Student {
    private final String id;
    private final String name;
    private int currentCredits;

    public Student(String id, String name, int currentCredits) {
        this.id = id;
        this.name = name;
        this.currentCredits = currentCredits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public void addCredits(int credits) {
        this.currentCredits += credits;
    }

    public void deductCredits(int credits) {
        this.currentCredits -= credits;
    }

    public abstract int getCreditLimit();
    public abstract String getTypeName();
}

class RegularStudent extends Student {
    public RegularStudent(String id, String name, int currentCredits) {
        super(id, name, currentCredits);
    }

    @Override
    public int getCreditLimit() {
        return 24;
    }

    @Override
    public String getTypeName() {
        return "Regular";
    }
}

class HonorsStudent extends Student {
    public HonorsStudent(String id, String name, int currentCredits) {
        super(id, name, currentCredits);
    }

    @Override
    public int getCreditLimit() {
        return 28;
    }

    @Override
    public String getTypeName() {
        return "Honors";
    }
}

class ExchangeStudent extends Student {
    public ExchangeStudent(String id, String name, int currentCredits) {
        super(id, name, currentCredits);
    }

    @Override
    public int getCreditLimit() {
        return 20;
    }

    @Override
    public String getTypeName() {
        return "Exchange";
    }
}

class Elective {
    private final String name;
    private final int creditValue;
    private final int capacity;
    private final Set<Student> enrolledStudents;
    private final Queue<Student> waitlist;

    public Elective(String name, int creditValue, int capacity) {
        this.name = name;
        this.creditValue = creditValue;
        this.capacity = capacity;
        this.enrolledStudents = new LinkedHashSet<>();
        this.waitlist = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getCreditValue() {
        return creditValue;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean enroll(Student student) {
        if (enrolledStudents.contains(student) || waitlist.contains(student)) {
            System.out.println("Enrollment failed: " + student.getName() + " is already enrolled or on the waitlist.");
            return false;
        }

        // Check credit limit before seat availability
        if (student.getCurrentCredits() + creditValue > student.getCreditLimit()) {
            System.out.printf("Enrollment failed: %s would exceed the %s credit limit (%d/%d).%n",
                    student.getName(), student.getTypeName(),
                    student.getCurrentCredits() + creditValue, student.getCreditLimit());
            return false;
        }

        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            student.addCredits(creditValue);
            System.out.printf("%s enrolled in %s (credits: %d/%d).%n",
                    student.getName(), name, student.getCurrentCredits(), student.getCreditLimit());
            return true;
        } else {
            System.out.println(name + " is full.");
            waitlist.offer(student);
            System.out.printf("%s added to waitlist (position %d).%n", student.getName(), waitlist.size());
            return false;
        }
    }

    public boolean drop(Student student) {
        if (!enrolledStudents.contains(student)) {
            System.out.println(student.getName() + " is not enrolled in " + name + ".");
            return false;
        }

        enrolledStudents.remove(student);
        student.deductCredits(creditValue);
        System.out.printf("%s dropped %s (credits: %d/%d).%n",
                student.getName(), name, student.getCurrentCredits(), student.getCreditLimit());

        // Auto-promote first eligible student on waitlist
        while (!waitlist.isEmpty()) {
            Student nextStudent = waitlist.poll();
            if (nextStudent.getCurrentCredits() + creditValue <= nextStudent.getCreditLimit()) {
                enrolledStudents.add(nextStudent);
                nextStudent.addCredits(creditValue);
                System.out.printf("%s promoted from waitlist and enrolled in %s (credits: %d/%d).%n",
                        nextStudent.getName(), name, nextStudent.getCurrentCredits(), nextStudent.getCreditLimit());
                break;
            }
        }

        return true;
    }
}

public class ElectiveSeatRush {
    public static void main(String[] args) {
        Elective cloudComputing = new Elective("Cloud Computing", 4, 2);

        Student asha = new RegularStudent("S1", "Asha", 20);
        Student ravi = new HonorsStudent("S2", "Ravi", 22);
        Student neha = new ExchangeStudent("S3", "Neha", 12);
        Student kiran = new RegularStudent("S4", "Kiran", 22);

        cloudComputing.enroll(asha);
        cloudComputing.enroll(ravi);
        cloudComputing.enroll(neha);
        cloudComputing.enroll(kiran);

        cloudComputing.drop(asha);
    }
}
