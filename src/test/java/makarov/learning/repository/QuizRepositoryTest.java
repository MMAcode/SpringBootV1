package makarov.learning.repository;

import makarov.learning.model.Choice;
import makarov.learning.model.Question;
import makarov.learning.model.Quiz;
import makarov.learning.model.QuizProjection_NoAns;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //to connect repo with database for testing (H2) -> makes @autowire work
class QuizRepositoryTest {

    @Autowired
    QuizRepository quizRepository;

    //we don't have to test those methods we did not write -> write only tests for those methods I have written myself
    @BeforeEach
    void setUp() {
        Choice c1 = Choice.builder().title("ch1").build();
        Choice c2 = Choice.builder().title("ch2").correctAnswer(true).build();

        Question qu1 = Question.builder().title("questionA").choices(new ArrayList<>()).build();
        qu1.associateExternalChoices(c1, c2);

        Quiz q1 = new Quiz("q1");
        q1.associateQuestion(qu1);

        Quiz q2 = new Quiz("q2");
        Quiz q3 = new Quiz("q3");

        quizRepository.save(q1);
        quizRepository.save(q2);
        quizRepository.save(q3);
    }

    @AfterEach
    void tearDown() {
        quizRepository.deleteAll();
        assertThat(quizRepository.findAll().size()).isEqualTo(0);
    }


    @Test
    void containsThreeCreatedQuizess() {
        assertThat(quizRepository.findAll_WithoutAnswers().size()).isEqualTo(3);
        assertThat(quizRepository.findAll_WithoutAnswers().stream().map(q -> q.getTitle()))
            .contains("q1")
            .contains("q2")
            .contains("q3");
    }

    @Test
    void answersAreNotIncluded() {
        Collection<QuizProjection_NoAns> quizzes = quizRepository.findAll_WithoutAnswers();

        // assertThat(quizRepository
        //     .findAll_WithoutAnswers()
        //     .stream()
        //     .filter(q -> "q1".equals(q.getTitle()))
        //     .findFirst().get()
        //     .getQuestions().get(0)
        //     .getChoices().get(0)
        // // ).hasOnlyFields("title");
        // ).getClass().getSimpleName().equals("Choice2");

        // System.out.println(
        //     quizRepository
        //         .findAll_WithoutAnswers()
        //         .stream()
        //         .filter(q -> "q1".equals(q.getTitle()))
        //         .findFirst().get()
        //         .getQuestions().get(0)
        //         .getChoices().get(0).getClass().getSimpleName()+"abcd"
        // );


    }
    //
    // @Test
    // void findAll_WithoutAnswers() {
    // }
    //
    // @Test
    // void getQuizById() {
    // }
    //
    // @Test
    // void getQuizWithoutQuestionOptionsById() {
    // }
}