import java.util.*;

interface ScoringStrategy {
    String getTrackName();
    double calculateFinalScore(double idea, double execution, double presentation);
}

class InnovationTrack implements ScoringStrategy {
    @Override
    public String getTrackName() {
        return "Innovation track";
    }

    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
    }
}

class OpenTrack implements ScoringStrategy {
    @Override
    public String getTrackName() {
        return "Open track";
    }

    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    private final String id;
    private final String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Project {
    private final String title;
    private final Team team;
    private Double finalScore;

    public Project(String title, Team team) {
        this.title = title;
        this.team = team;
        this.finalScore = null;
    }

    public String getTitle() {
        return title;
    }

    public Team getTeam() {
        return team;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }
}

class Team {
    private final String name;
    private final List<Student> members;
    private final ScoringStrategy track;
    private Project project;

    public Team(String name, List<Student> members, ScoringStrategy track) {
        this.name = name;
        this.members = new ArrayList<>(members);
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return members;
    }

    public ScoringStrategy getTrack() {
        return track;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

class Hackathon {
    private final List<Team> teams = new ArrayList<>();
    private final Set<String> registeredStudentIds = new HashSet<>();
    private boolean resultsPublished = false;

    public Team registerTeam(String teamName, List<Student> members, ScoringStrategy track) {
        if (members == null || members.size() < 2 || members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return null;
        }

        for (Student s : members) {
            if (registeredStudentIds.contains(s.getId())) {
                System.out.println("Registration failed: Student " + s.getName() + " is already in another team.");
                return null;
            }
        }

        for (Student s : members) {
            registeredStudentIds.add(s.getId());
        }

        Team team = new Team(teamName, members, track);
        teams.add(team);
        System.out.printf("Team %s registered (%d members, %s).%n", teamName, members.size(), track.getTrackName());
        return team;
    }

    public Project submitProject(Team team, String projectTitle) {
        if (team == null) {
            return null;
        }
        if (team.getProject() != null) {
            System.out.println("Submission failed: Team already submitted a project.");
            return null;
        }
        Project project = new Project(projectTitle, team);
        team.setProject(project);
        System.out.println("Project '" + projectTitle + "' submitted by " + team.getName() + ".");
        return project;
    }

    public boolean scoreProject(Project project, double idea, double execution, double presentation) {
        if (resultsPublished) {
            System.out.println("Rescore rejected: Results have already been published.");
            return false;
        }
        if (project == null) {
            return false;
        }

        double score = project.getTeam().getTrack().calculateFinalScore(idea, execution, presentation);
        project.setFinalScore(score);
        System.out.println("Score recorded for '" + project.getTitle() + "'. Final score: " + String.format("%.2f", score) + ".");
        return true;
    }

    public void publishResults() {
        resultsPublished = true;
        System.out.println("Results published.");
    }
}

public class CodeSprintJudgingDesk {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon();

        // 1. Team 'ByteBusters' registers with 3 members in Innovation track
        List<Student> byteBustersMembers = Arrays.asList(
                new Student("S1", "Asha"),
                new Student("S2", "Ravi"),
                new Student("S3", "Neha")
        );
        Team byteBusters = hackathon.registerTeam("ByteBusters", byteBustersMembers, new InnovationTrack());

        // 2. Team 'SoloCoder' registers with 1 member in Open track (fails)
        List<Student> soloCoderMembers = Collections.singletonList(
                new Student("S4", "Kiran")
        );
        hackathon.registerTeam("SoloCoder", soloCoderMembers, new OpenTrack());

        // 3. ByteBusters submits project 'SmartAttend'
        Project smartAttend = hackathon.submitProject(byteBusters, "SmartAttend");

        // 4. Judge scores 'SmartAttend': idea 8, execution 7, presentation 9
        hackathon.scoreProject(smartAttend, 8, 7, 9);

        // 5. Organiser publishes results
        hackathon.publishResults();

        // 6. Judge attempts to change idea score to 10 (fails)
        hackathon.scoreProject(smartAttend, 10, 7, 9);
    }
}
