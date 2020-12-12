package umut.backend.Repository;

import umut.backend.Entities.QuizSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizSongsRepository extends JpaRepository<QuizSong, UUID> {
    List<QuizSong> findByQuizId(UUID quizId);
}
