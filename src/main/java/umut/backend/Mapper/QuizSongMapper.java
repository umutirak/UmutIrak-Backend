package umut.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import umut.backend.DTOs.QuizSongDTO;
import umut.backend.Entities.QuizSong;

@Mapper(componentModel = "spring")
public interface QuizSongMapper extends BaseMapper<QuizSongDTO, QuizSong> {
    @Mapping(target = "quiz.quizSongs", ignore = true)
    QuizSong toQuizSongWithoutQuiz(QuizSongDTO dto);
}
