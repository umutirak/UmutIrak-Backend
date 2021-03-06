package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizSongDTO extends BaseDTO {
    private String songName;
    private String songArtist;
    private String songUrl;
    private QuizDTO quiz;
}
