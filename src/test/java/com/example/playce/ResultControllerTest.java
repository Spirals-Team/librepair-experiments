import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.playce.Result;
import com.example.playce.ResultController;

@RunWith(SpringRunner.class)
public class ResultControllerTest {

	@Test
	public void testGetPlayceResult() {
	   ResultController r = new ResultController();
	   String playceName = "Urbane Cafe";
	   
	   Result expected = new Result("Urbane Cafe", 1, 4.5, 
	       "952 Higuera St San Luis Obispo CA 93401",35.2809, -120.662, "restaurant");
	   
	   Assert.assertTrue(r.generatePlayceResult(playceName).equals(expected));
	}
	
	@Test
	public void testGetPlayceResultDefault() {
	   ResultController r = new ResultController();
	   String playceName = "Raku Ramen";
   
	   Result expected = new Result("Raku Ramen", 2, 3.5, 
	       "1308 Monterey St San Luis Obispo CA 93401",35.2846, -120.657, "restaurant");
	   
	   Assert.assertTrue(r.generatePlayceResult(playceName).equals(expected));
	}
}
