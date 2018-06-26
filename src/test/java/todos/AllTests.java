package todos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	
	bbdd.BaseDatosTest.class,
	dominio.AgenteTest.class,
	dominio.IncidenciaTest.class,
	cucumber_manager.CucumberTest.class
})
public class AllTests 
{
	
}
