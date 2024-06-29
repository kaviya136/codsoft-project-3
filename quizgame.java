import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication {
    
    static class Question {
        String questionText;
        String[] options;
        int correctAnswer;

        Question(String questionText, String[] options, int correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    static Question[] questions = new Question[] {
        new Question("What is the capital of France?", new String[] {"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3),
        new Question("Which planet is known as the Red Planet?", new String[] {"1. Earth", "2. Mars", "3. Jupiter", "4. Saturn"}, 2),
        new Question("What is the largest ocean on Earth?", new String[] {"1. Atlantic Ocean", "2. Indian Ocean", "3. Arctic Ocean", "4. Pacific Ocean"}, 4)
    };

    static int score = 0;
    static int currentQuestionIndex = 0;
    static boolean answered = false;
    static Timer timer = new Timer();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz Application!");
        startQuiz();
    }

    public static void startQuiz() {
        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            answered = false;
            displayQuestion(questions[currentQuestionIndex]);
            waitForAnswer();
        }
        displayResult();
        scanner.close();
    }

    public static void displayQuestion(Question question) {
        System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + question.questionText);
        for (String option : question.options) {
            System.out.println(option);
        }
        startTimer();
    }

    public static void waitForAnswer() {
        while (!answered) {
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                if (answer >= 1 && answer <= 4) {
                    checkAnswer(answer);
                    answered = true;
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.next(); // clear invalid input
            }
        }
    }

    public static void checkAnswer(int answer) {
        timer.cancel();
        if (answer == questions[currentQuestionIndex].correctAnswer) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect! The correct answer was " + questions[currentQuestionIndex].correctAnswer + ".");
        }
    }

    public static void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answered) {
                    System.out.println("\nTime's up! Moving to the next question.");
                    answered = true;
                }
            }
        }, 10000); // 10 seconds timer for each question
    }

    public static void displayResult() {
        System.out.println("\nQuiz Finished!");
        System.out.println("Your score: " + score + " out of " + questions.length);
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i].questionText);
            System.out.println("Correct answer: " + questions[i].correctAnswer);
        }
    }
}
