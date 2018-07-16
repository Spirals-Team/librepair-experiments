package dev.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Question;
import dev.entites.Quizz;
import dev.repositories.quizz.QuizzRepositoryMemoire;

// Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { QuizzRepositoryMemoire.class, RepositoryTestConfig.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test
@RunWith(SpringRunner.class)
public class QuizzRepositoryMemoireTest {

	@Autowired
	private QuizzRepositoryMemoire service;

	@Test
	public void testFindAll() {
		List<Quizz> quizzes = service.findAll();
		assertThat(quizzes.get(0).getTitre()).isEqualTo("Animaux");
		assertThat(quizzes.get(0).getQuestions().get(0).getTitre()).isEqualTo("Quel animal court le plus vite?");
		assertThat(quizzes.get(1).getTitre()).isEqualTo("Géographie");
		assertThat(quizzes.get(1).getQuestions().get(0).getTitre()).isEqualTo("Quel est la capital du Mexique?");
		assertThat(quizzes.get(2).getTitre()).isEqualTo("Mathématiques");
		assertThat(quizzes.get(2).getQuestions().get(0).getTitre()).isEqualTo("Combien font 2+2?");
	}

	@Test
	public void testSave() {
		List<Quizz> quizzes = service.findAll();
		service.save(new Quizz("Chimie", new ArrayList<Question>()));
		assertThat(quizzes.get(quizzes.size() - 1).getTitre()).isEqualTo("Chimie");
	}

	@Test
	public void testUpdate() {
		List<Quizz> quizzes = service.findAll();
		Quizz newQuizz = new Quizz("Chimie", new ArrayList<Question>());
		newQuizz.setId(0L);
		service.update(newQuizz);
		assertThat(quizzes.get(0).getTitre()).isEqualTo("Chimie");
	}

	@Test
	public void testDelete() {
		List<Quizz> quizzes = service.findAll();
		Question q1 = new Question("Quel animal court le plus vite?");
		List<Question> questions = new ArrayList<Question>();
		questions.add(q1);
		Quizz quizz = new Quizz("Animaux", questions);
		service.delete(quizz);
		assertThat(!quizzes.contains(quizz));
	}
}