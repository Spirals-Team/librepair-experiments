package dev.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Concours;
import dev.repositories.concours.ConcoursRepositoryMemoire;

@ContextConfiguration(classes = { ConcoursRepositoryMemoire.class })

@RunWith(SpringRunner.class)
public class ConcoursRepositoryMemoireTest {

	@Autowired
	private ConcoursRepositoryMemoire concoursRepositoryMemoire;

	@Test
	public void testFindAll() {
		List<Concours> concours = concoursRepositoryMemoire.findAll();
		assertThat(concours.size()).isEqualTo(1);

	}

	@Test
	public void testSave() {
		assertThat(concoursRepositoryMemoire.findAll().size()).isEqualTo(1);
		concoursRepositoryMemoire.save(new Concours());
		assertThat(concoursRepositoryMemoire.findAll().size()).isEqualTo(2);
	}

	@Test
	public void testUpdate() {

		List<Concours> concours = concoursRepositoryMemoire.findAll();
		Concours s = concoursRepositoryMemoire.findAll().get(0);
		s.setTitre("le Grand Jeu");
		concoursRepositoryMemoire.update(s);
		assertThat(concours.get(0).getTitre()).isEqualTo("le Grand Jeu");
	}

	@Test
	public void testDelete() {
		assertThat(concoursRepositoryMemoire.findAll().size()).isEqualTo(2);
		Concours s = concoursRepositoryMemoire.findAll().get(0);
		concoursRepositoryMemoire.delete(s);
		assertThat(concoursRepositoryMemoire.findAll().size()).isEqualTo(1);
	}

}
