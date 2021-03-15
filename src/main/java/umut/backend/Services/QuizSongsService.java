package umut.backend.Services;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.QuizSongDTO;
import umut.backend.Entities.QuizSong;
import umut.backend.Mapper.QuizSongMapper;
import umut.backend.Repository.QuizRepository;
import umut.backend.Repository.QuizSongsRepository;
import org.springframework.stereotype.Service;
import umut.backend.Services.Interfaces.IQuizSongsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizSongsService implements IQuizSongsService {

    private final QuizSongsRepository quizSongsRepository;
    private final QuizSongMapper mapper;

    @Override
    public List<QuizSong> getQuizSongsByQuizId(UUID quizId) {
        return quizSongsRepository.findByQuizId(quizId);
    }

    @Override
    public void addQuizSongs(List<QuizSongDTO> quizSongDTOList) {
        var songList = quizSongDTOList.stream().map(mapper::toQuizSongWithoutQuiz).collect(Collectors.toList());
        quizSongsRepository.saveAll(songList);
    }
}
