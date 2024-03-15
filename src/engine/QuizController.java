package engine;


import engine.user.AppUser;
import engine.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.*;

@RequiredArgsConstructor
@RestController
@ComponentScan
public class QuizController {
    @Autowired
    private QuizRepo quizRepo;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

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
    public ResponseEntity<Quiz> createAndReturnUserQuiz(@RequestBody @Valid Quiz quiz,
                                                        @AuthenticationPrincipal UserDetails details) {
        if (quiz.getAnswer() == null) {
            List<Integer> emptyAnswer = new ArrayList<>();
            quiz.setAnswer(emptyAnswer);
        }

        quiz.setUsername(details.getUsername());
        Quiz quizObj = quizRepo.save(quiz);
        return new ResponseEntity<>(quizObj, HttpStatus.OK);
    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable int id) {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.addAll(quizRepo.findAll());

        for (Quiz quiz : quizzes) {
            if (quiz.getId() == id) {
                return new ResponseEntity<>(quiz, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("api/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        try {
            List<Quiz> quizzes = new ArrayList<>(quizRepo.findAll());
            if (quizzes.isEmpty()) {
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("api/quizzes/{id}/solve")
    public ResponseEntity<QuizAnswer> returnAnswer(@PathVariable Long id, @RequestBody UserAnswer answer) {
        //List<Quiz> quizzes = new ArrayList<>(quizRepo.findAll());
        Optional<Quiz> quizObj = quizRepo.findById(id);

        if (quizObj.isPresent()) {
            System.out.println("quizObj answer: " + quizObj.get().getAnswer().getClass());
            System.out.println("answer answer: " + answer.getAnswer().getClass());
//                if(quizObj.get().getAnswer().equals(answer.getAnswer()))
            if(Objects.equals(new ArrayList<>(quizObj.get().getAnswer()), answer.getAnswer()))
            {
                System.out.println("hhe you are right!");
                return new ResponseEntity<>(QuizAnswer.rightAnswer, HttpStatus.OK);
            } else {
                System.out.println("hhe you are wrong!");
                return new ResponseEntity<>(QuizAnswer.wrongAnswer, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("api/register")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationRequest registrationRequest) {
        var user = new AppUser();
        //String regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$";
//        if (!registrationRequest.getEmail().matches(regexp)) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        if (appUserRepository.findAppUserByUsername(registrationRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setUsername(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        appUserRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

   //record RegistrationRequest(String email, String password) {}

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuizById(@AuthenticationPrincipal UserDetails details, @PathVariable Long id) {
        Optional<Quiz> quizObj = quizRepo.findById(id);
        if (quizObj.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (quizObj.get().getUsername().equals(details.getUsername())) {
            quizRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (!quizObj.get().getUsername().equals(details.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
