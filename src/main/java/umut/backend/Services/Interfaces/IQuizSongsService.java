package umut.backend.Services.Interfaces;

import umut.backend.DTOs.QuizSongDTO;
import umut.backend.Entities.QuizSong;

import java.util.List;
import java.util.UUID;

public interface IQuizSongsService {
    List<QuizSong> getQuizSongsByQuizId(UUID quizId);

    void addQuizSongs(List<QuizSongDTO> quizSongDTOList, UUID quizId);
}
