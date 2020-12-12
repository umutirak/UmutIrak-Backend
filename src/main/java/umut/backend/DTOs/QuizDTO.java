package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umut.backend.Enums.QuizStatus;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class QuizDTO {
    private UUID id;
    private String quizName;
    private String quizImageUrl;
    private int playCount;
    private QuizStatus quizStatus;
}
