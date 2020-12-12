package umut.backend.Repository;

import umut.backend.Entities.Quiz;
import umut.backend.Enums.QuizStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> findByQuizStatus(QuizStatus quizStatus);
}
