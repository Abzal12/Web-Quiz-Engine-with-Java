package engine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.*;

@RestController
public class QuizController {
    int counter = 1;
    List<Quiz> quizzes = new ArrayList<>();

    @GetMapping("/api/quiz")
    public Quiz getQuiz() {
        return Quiz.quizQuestion;
    }

    @PostMapping("/api/quiz")
    public QuizAnswer returnAppropriateAnswer(@RequestParam int answer) {
        if (answer == 2) {
            return QuizAnswer.rightAnswer;
        } else {
            return QuizAnswer.wrongAnswer;
        }
    }
    @PostMapping("/api/quizzes")
    public Quiz createAndReturnUserQuiz(@RequestBody @Valid Quiz quiz) {
        quiz.setId(counter);
        counter++;
        if (quiz.getAnswer() == null) {
            List<Integer> emptyAnswer = new ArrayList<>();
            quiz.setAnswer(emptyAnswer);
        }
        quizzes.add(quiz);
        return quiz;
    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable int id) {
        for (Quiz quiz : quizzes) {
            if (quiz.getId() == id) {
                return ResponseEntity.ok(quiz);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("api/quizzes")
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("api/quizzes/{id}/solve")
    public ResponseEntity<?> returnAnswer(@PathVariable int id, @RequestBody UserAnswer answer) {
        for (Quiz quiz : quizzes) {
            if (quiz.getId() == id) {
                if (Objects.equals(quiz.getAnswer(), answer.getAnswer())) {
                    return ResponseEntity.ok(QuizAnswer.rightAnswer);
                } else {
                    return ResponseEntity.ok(QuizAnswer.wrongAnswer);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
}
