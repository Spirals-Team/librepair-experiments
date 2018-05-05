package uo.asw.junitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	
	CategoriaTest.class,
	ChatTest.class,
	IncidenciaBDTest.class,
	MensajeTest.class,
	PropiedadTest.class,
	UsuarioBDTest.class,
	ValorLimiteTest.class
})
public class AllTests { }