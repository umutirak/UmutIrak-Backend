package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "QUIZ_SONGS")
@Getter
@Setter
public class QuizSong extends BaseEntity {
    private String songName;
    private String songArtist;
    private String songUrl;
    private Date createDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;
}
