package umut.backend.Services;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.QuizDTO;
import umut.backend.Entities.Quiz;
import umut.backend.Enums.QuizStatus;
import umut.backend.Mapper.QuizMapper;
import umut.backend.Repository.QuizRepository;
import umut.backend.Requests.RequestAddQuiz;
import org.springframework.stereotype.Service;
import umut.backend.Services.Interfaces.IQuizService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizService implements IQuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper mapper;

    @Override
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(mapper::fromQuiz).collect(Collectors.toList());
    }

    @Override
    public QuizDTO addQuiz(QuizDTO dto) {
        Quiz quiz = new Quiz();
        quiz.setQuizImageUrl(dto.getQuizImageUrl());
        quiz.setQuizName(dto.getQuizName());
        quiz.setQuizStatus(QuizStatus.WAITING_FOR_APPROVAL);
        Quiz savedQuiz = quizRepository.save(quiz);

        return mapper.fromQuiz(savedQuiz);
    }

    @Override
    public List<QuizDTO> getPlayableQuizzes() {
        List<Quiz> playableQuizzes = quizRepository.findByQuizStatus(QuizStatus.APPROVED);
        return playableQuizzes.stream().map(mapper::fromQuiz).collect(Collectors.toList());
    }

    @Override
    public List<QuizDTO> getQuizzesToBeApproved() {
        List<Quiz> quizzesToBeApproved = quizRepository.findByQuizStatus(QuizStatus.WAITING_FOR_APPROVAL);
        return quizzesToBeApproved.stream().map(mapper::fromQuiz).collect(Collectors.toList());
    }
}
