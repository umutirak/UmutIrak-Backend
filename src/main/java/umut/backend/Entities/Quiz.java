package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import umut.backend.Enums.QuizStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Quiz {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String quizName;
    private String quizImageUrl;
    @CreationTimestamp
    private Date createDate;
    private int playCount;
    @Enumerated(EnumType.STRING)
    private QuizStatus quizStatus;
}
