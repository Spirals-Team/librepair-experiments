package dev.repositories.classe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.entites.Classe;
import dev.repositories.classe.ClasseRepositoryMemoire;

@ContextConfiguration(classes = { ClasseRepositoryMemoire.class })
@RunWith(SpringRunner.class)
public class ClasseRepositoryMemoireTest {

	@Autowired
	public ClasseRepositoryMemoire classeServiceMemoire = new ClasseRepositoryMemoire();

	@Test
	public void TestfindAll() {
		// TODO Auto-generated method stub
		List<Classe> classe = classeServiceMemoire.findAll();

		assertThat(classe).isNotEmpty();
		assertThat(classe.size()).isEqualTo(3);
		assertThat(classe.get(0).getStagiaires().get(0).getPrenom()).isEqualTo("Maxime");
	}

	@Test
	public void TestSave() {
		// TODO Auto-generated method stub
		List<Classe> classe = classeServiceMemoire.findAll();
		Classe c = classe.get(0);
		c.setId(4L);
		int size = classe.size();

		classe.add(c);

		assertThat(classe.size()).isEqualTo(size + 1);
	}

	@Test
	public void TestUpdate() {
		// TODO Auto-generated method stub
		List<Classe> classe = classeServiceMemoire.findAll();
		Classe c = classe.get(0);

		c.setNom("D21");

		classeServiceMemoire.update(c);

		assertThat(classe.get(0).getNom()).isEqualTo("D21");
	}

	@Test
	public void TestDelete() {
		// TODO Auto-generated method stub
		List<Classe> classe = classeServiceMemoire.findAll();
		Classe c = classe.get(3);
		int size = classe.size();

		classe.remove(c);

		assertThat(classe.size()).isEqualTo(size - 1);

	}

}
