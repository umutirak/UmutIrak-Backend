package umut.backend.Requests;

import lombok.Getter;
import lombok.Setter;
import umut.backend.DTOs.QuizSongDTO;

import java.util.List;

@Getter
@Setter
public class RequestAddQuiz {
    private String quizName;
    private String quizImageUrl;
    private List<QuizSongDTO> quizSongDTOList;
}
