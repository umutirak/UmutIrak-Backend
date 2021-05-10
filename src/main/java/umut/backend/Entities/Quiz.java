package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import umut.backend.Enums.QuizStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Quiz extends BaseEntity {
    private String quizName;
    private String quizImageUrl;
    @CreationTimestamp
    private Date createDate;
    private int playCount;
    @Enumerated(EnumType.STRING)
    private QuizStatus quizStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizSong> quizSongs;
}
