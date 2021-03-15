package umut.backend.Facades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umut.backend.DTOs.QuizDTO;
import umut.backend.Facades.Interfaces.IQuizFacade;
import umut.backend.Services.Interfaces.IQuizService;
import umut.backend.Services.Interfaces.IQuizSongsService;

@RequiredArgsConstructor
@Component
public class QuizFacade implements IQuizFacade {
    private final IQuizService quizService;
    private final IQuizSongsService quizSongsService;

    @Override
    public void createQuiz(QuizDTO quizDTO) {
        var quiz = quizService.addQuiz(quizDTO);

        var quizSongs = quizDTO.getQuizSongs();
        quizSongs.forEach(i -> i.setQuiz(quiz));
        quizSongsService.addQuizSongs(quizSongs);
    }
}
