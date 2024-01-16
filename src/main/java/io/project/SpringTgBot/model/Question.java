package io.project.SpringTgBot.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number_question")
    private Long id;

    @Column(name = "question", nullable = false)
    private String questionName;

    @Column(name = "var_1", nullable = false)
    private String firstAnswer;

    @Column(name = "var_2", nullable = false)
    private String secondAnswer;

    @Column(name = "var_3", nullable = false)
    private String thirdAnswer;

    @Column(name = "var_4", nullable = false)
    private String forthAnswer;

    @Column(name = "answer", nullable = false)
    private String rightAnswer;
}
