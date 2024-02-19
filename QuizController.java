package engine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class QuizController {

    private final List<Quiz> quizzes = List.of(
            new Quiz("The Java Logo",
                    "What is depicted on the Java logo?",
                    Arrays.asList("Robot","Tea leaf","Cup of coffee","Bug"))
    );

    private final List<QuizAnswer> quizAnswers = List.of(
            new QuizAnswer(true, "Congratulations, you're right!"),
            new QuizAnswer(false, "Wrong answer! Please, try again.")
    );

    @GetMapping("/api/quiz")
    public Quiz getQuiz() {
        return quizzes.get(0);
    }

    @PostMapping("/api/quiz")
    public QuizAnswer returnAppropriateAnswer(@RequestParam int answer) {
        if (answer == 2) {
            return quizAnswers.get(0);
        } else {
            return quizAnswers.get(1);
        }
    }
}
