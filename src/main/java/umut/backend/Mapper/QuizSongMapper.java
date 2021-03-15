package umut.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import umut.backend.DTOs.QuizSongDTO;
import umut.backend.Entities.QuizSong;

@Mapper(componentModel = "spring")
public interface QuizSongMapper {
    @Named(value = "defaultQuiz")
    QuizSong toQuizSong(QuizSongDTO dto);

    @Mapping(target = "quiz.quizSongs", ignore = true)
    QuizSong toQuizSongWithoutQuiz(QuizSongDTO dto);
}
