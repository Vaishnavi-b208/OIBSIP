import java.util.ArrayList;

public class QuestionBank {

    public static ArrayList<Question> getQuestions() {

        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "Which keyword is used to create a class in Java?",
                new String[]{
                        "class",
                        "Class",
                        "create",
                        "new"
                },
                0
        ));

        questions.add(new Question(
                "Which data type is used to store whole numbers?",
                new String[]{
                        "float",
                        "int",
                        "double",
                        "char"
                },
                1
        ));

        questions.add(new Question(
                "Which symbol is used for single-line comments in Java?",
                new String[]{
                        "/*",
                        "#",
                        "//",
                        "<!--"
                },
                2
        ));

        questions.add(new Question(
                "Which method is the starting point of a Java program?",
                new String[]{
                        "start()",
                        "run()",
                        "main()",
                        "execute()"
                },
                2
        ));

        questions.add(new Question(
                "Which keyword is used to inherit a class?",
                new String[]{
                        "implements",
                        "extends",
                        "inherits",
                        "super"
                },
                1
        ));

        questions.add(new Question(
                "Which loop executes at least once?",
                new String[]{
                        "for",
                        "while",
                        "do-while",
                        "foreach"
                },
                2
        ));

        questions.add(new Question(
                "Which keyword is used to handle an exception?",
                new String[]{
                        "try",
                        "catch",
                        "throw",
                        "All of these"
                },
                3
        ));

        questions.add(new Question(
                "Which keyword is used to create an object?",
                new String[]{
                        "object",
                        "create",
                        "new",
                        "class"
                },
                2
        ));

        questions.add(new Question(
                "Which package contains Swing components?",
                new String[]{
                        "java.io",
                        "java.sql",
                        "javax.swing",
                        "java.util"
                },
                2
        ));

        questions.add(new Question(
                "Which keyword prevents a class from being inherited?",
                new String[]{
                        "static",
                        "private",
                        "final",
                        "protected"
                },
                2
        ));

        return questions;
    }
}