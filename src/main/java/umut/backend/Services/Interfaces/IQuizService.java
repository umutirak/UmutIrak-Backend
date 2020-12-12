package umut.backend.Services.Interfaces;

import umut.backend.DTOs.QuizDTO;
import umut.backend.Requests.RequestAddQuiz;

import java.util.List;

public interface IQuizService {
    List<QuizDTO> getAllQuizzes();

    QuizDTO addQuiz(RequestAddQuiz request);

    List<QuizDTO> getPlayableQuizzes();

    List<QuizDTO> getQuizzesToBeApproved();
}
