package umut.backend.Services;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.QuizSongDTO;
import umut.backend.Entities.QuizSong;
import umut.backend.Mapper.AutoMapper;
import umut.backend.Repository.QuizSongsRepository;
import org.springframework.stereotype.Service;
import umut.backend.Services.Interfaces.IQuizSongsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuizSongsService implements IQuizSongsService {

    private final QuizSongsRepository quizSongsRepository;
    private final AutoMapper mapper;

    @Override
    public List<QuizSong> getQuizSongsByQuizId(UUID quizId) {
        return quizSongsRepository.findByQuizId(quizId);
    }

    @Override
    public void addQuizSongs(List<QuizSongDTO> quizSongDTOList, UUID quizId) {
        List<QuizSong> songList = new ArrayList<>();
        for (QuizSongDTO item : quizSongDTOList) {
            QuizSong song = mapper.toQuizSong(item, quizId);
            songList.add(song);
        }

        quizSongsRepository.saveAll(songList);
    }
}
