import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.playce.Result;
import com.example.playce.ResultController;

@RunWith(SpringRunner.class)
public class GetPlayceResultTest {

	@Test
	public void testGetPlayceResult() {
	   ResultController r = new ResultController();
	   String playceName = "San Diego Museum Of Arts";
	   
	   Result expected = new Result("San Diego Museum Of Arts", 0, 0.0, 
	       "San Luis Obispo CA 93401 USA",35.2828, -120.66, "recreation");
	   
	   Assert.assertTrue(r.generatePlayceResult(playceName).equals(expected));
	}
}
