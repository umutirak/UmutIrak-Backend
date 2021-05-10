package umut.backend.Mapper;

import org.mapstruct.Mapper;
import umut.backend.DTOs.QuizDTO;
import umut.backend.Entities.Quiz;

@Mapper(componentModel = "spring")
public interface QuizMapper extends BaseMapper<QuizDTO, Quiz> {

}
