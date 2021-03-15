package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.QuizDTO;
import umut.backend.Entities.QuizSong;
import umut.backend.Requests.RequestAddQuiz;
import umut.backend.Services.Interfaces.IQuizService;
import umut.backend.Services.Interfaces.IQuizSongsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
@AllArgsConstructor
public class QuizController {

    private final IQuizService quizService;
    private final IQuizSongsService quizSongsService;

    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        return new ResponseEntity<>(quizService.getAllQuizzes(), HttpStatus.OK);
    }

    @GetMapping("/playableQuizzes")
    public ResponseEntity<List<QuizDTO>> getPlayableQuizzes() {
        return new ResponseEntity<>(quizService.getPlayableQuizzes(), HttpStatus.OK);
    }

    @GetMapping("/quizzesToBeApproved")
    public ResponseEntity<List<QuizDTO>> getQuizzesToBeApproved() {
        return new ResponseEntity<>(quizService.getQuizzesToBeApproved(), HttpStatus.OK);
    }

    @PostMapping("/addQuiz")
    public ResponseEntity<QuizDTO> addQuiz(@RequestBody RequestAddQuiz request) {
        QuizDTO dto = new QuizDTO();
        dto.setQuizName(request.getQuizName());
        dto.setQuizImageUrl(request.getQuizImageUrl());

        QuizDTO savedQuiz = quizService.addQuiz(dto);
        return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
    }

    @GetMapping("/quizSongs/{quizId}")
    public ResponseEntity<List<QuizSong>> getQuizSongsByQuizId(@PathVariable UUID quizId) {
        List<QuizSong> songList = quizSongsService.getQuizSongsByQuizId(quizId);
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }
}
