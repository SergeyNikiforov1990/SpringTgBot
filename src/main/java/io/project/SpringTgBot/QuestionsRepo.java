package io.project.SpringTgBot;
import io.project.SpringTgBot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepo extends JpaRepository<Question, Integer>{
}
