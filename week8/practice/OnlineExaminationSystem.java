import java.util.*;

abstract class Question {
    private final int id;
    private final String text;

    public Question(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public abstract boolean checkAnswer(String studentAnswer);
}

class MultipleChoiceQuestion extends Question {
    private final String correctAnswer;

    public MultipleChoiceQuestion(int id, String text, String correctAnswer) {
        super(id, text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(String studentAnswer) {
        return correctAnswer != null && correctAnswer.equalsIgnoreCase(studentAnswer != null ? studentAnswer.trim() : "");
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(int id, String text, boolean correctAnswer) {
        super(id, text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(String studentAnswer) {
        return Boolean.parseBoolean(studentAnswer) == correctAnswer;
    }
}

class Examination {
    private final String title;
    private final List<Question> questions;

    public Examination(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}

class Student {
    private final String studentId;
    private final String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }
}

class Attempt {
    private final Student student;
    private final Examination examination;
    private final Map<Integer, String> studentAnswers;
    private boolean isSubmitted;
    private int score;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        this.studentAnswers = new LinkedHashMap<>();
        this.isSubmitted = false;
        this.score = 0;
    }

    public Student getStudent() {
        return student;
    }

    public Examination getExamination() {
        return examination;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void answerQuestion(int questionId, String answer) {
        if (isSubmitted) {
            System.out.println("Cannot change answer: Attempt has already been submitted.");
            return;
        }
        studentAnswers.put(questionId, answer);
        System.out.println("Question " + questionId + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (isSubmitted) {
            System.out.println("Attempt already submitted.");
            return;
        }
        this.isSubmitted = true;
        this.score = 0;
        for (Question q : examination.getQuestions()) {
            String ans = studentAnswers.get(q.getId());
            if (ans != null && q.checkAnswer(ans)) {
                score++;
            }
        }
        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + examination.getTitle() + "' attempt: " + score + "/" + examination.getQuestions().size() + " correct.");
    }

    public int getScore() {
        return score;
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");
        exam.addQuestion(new MultipleChoiceQuestion(1, "What is 2 + 2?", "A"));
        exam.addQuestion(new MultipleChoiceQuestion(2, "What is 3 * 3?", "B"));

        Student student = new Student("S101", "Student");
        Attempt attempt = new Attempt(student, exam);

        System.out.println("Examination '" + exam.getTitle() + "' started by " + student.getName() + ".");
        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");
        attempt.submit();
    }
}
