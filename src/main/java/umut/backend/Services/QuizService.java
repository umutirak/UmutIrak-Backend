package umut.backend.Services;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.QuizDTO;
import umut.backend.Entities.Quiz;
import umut.backend.Enums.QuizStatus;
import umut.backend.Mapper.AutoMapper;
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
    private final QuizSongsService quizSongsService;
    private final AutoMapper mapper;

    @Override
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(mapper::toQuizDTO).collect(Collectors.toList());
    }

    @Override
    public QuizDTO addQuiz(RequestAddQuiz request) {
        Quiz quiz = new Quiz();
        quiz.setQuizImageUrl(request.getQuizImageUrl());
        quiz.setQuizName(request.getQuizName());
        quiz.setQuizStatus(QuizStatus.WAITING_FOR_APPROVAL);
        Quiz addedQuiz = quizRepository.save(quiz);

        quizSongsService.addQuizSongs(request.getQuizSongDTOList(), addedQuiz.getId());

        return mapper.toQuizDTO(addedQuiz);
    }

    @Override
    public List<QuizDTO> getPlayableQuizzes() {
        List<Quiz> playableQuizzes = quizRepository.findByQuizStatus(QuizStatus.APPROVED);
        return playableQuizzes.stream().map(mapper::toQuizDTO).collect(Collectors.toList());
    }

    @Override
    public List<QuizDTO> getQuizzesToBeApproved() {
        List<Quiz> quizzesToBeApproved = quizRepository.findByQuizStatus(QuizStatus.WAITING_FOR_APPROVAL);
        return quizzesToBeApproved.stream().map(mapper::toQuizDTO).collect(Collectors.toList());
    }
}
