package uniovi.es;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uniovi.es.entities.Incidence;
import uniovi.es.repositories.IncidencesRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DatabaseTest {

	@Autowired
	private IncidencesRepository incidentsRepository;

	@Test
	public void testDatabase() throws Exception {

//		Incidence m = new Incidence();
//
//		m.setName("esteban");
//		m.setKind(2);
//		m.setAditionalInfo("aditional info");
//		m.setLocation("canada");
//		m.setMessage("mesage");
//		m.setTitle("tutulo");
//		m.setState(1);
//		String[] s1 = { "tag1", "tag2", "tag3", "tag4" };
//		m.setTags(s1);
//
//		incidentsRepository.save(m);
//		List<Incidence> messages = incidentsRepository.getIncidentsByAgentName("esteban");
//
//		assertThat(messages.get(0).getName(), equalTo(m.getName()));
//		assertThat(messages.get(0).getKind(), equalTo(m.getKind()));
//		assertThat(messages.get(0).getAditionalInfo(), equalTo(m.getAditionalInfo()));
//		assertThat(messages.get(0).getMessage(), equalTo(m.getMessage()));
//		assertThat(messages.get(0).getTitle(), equalTo(m.getTitle()));
//		assertThat(messages.get(0).getState(), equalTo(m.getState()));
//		assertThat(messages.get(0).getTags(), equalTo(m.getTags()));
//
//		incidentsRepository.delete(m);
	}

}