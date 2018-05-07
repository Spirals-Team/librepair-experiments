package uo.asw.incidashboard.services;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Categoria;
import uo.asw.dbManagement.model.Incidencia;
import uo.asw.dbManagement.model.Propiedad;
import uo.asw.dbManagement.model.Usuario;
import uo.asw.dbManagement.model.ValorLimite;
import uo.asw.dbManagement.tipos.CategoriaTipos;
import uo.asw.dbManagement.tipos.PerfilTipos;
import uo.asw.dbManagement.tipos.PropiedadTipos;

@Service
public class InsertSampleDataService {

	@Autowired
	private IncidenciasService incidenciaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CategoriaService c;

	@Autowired
	private PropiedadesService p;
	
	@Autowired
	private ValorLimiteService valorLimiteService;

	//@PostConstruct
	public void init() {
		Usuario op1 = new Usuario("nombre1", "apellido1", "operario1@prueba.es", "Id1", "123456", PerfilTipos.OPERARIO);
		Usuario op2 = new Usuario("nombre2", "apellido2", "operario2@prueba.es", "Id2", "123456", PerfilTipos.OPERARIO);
		Usuario op3 = new Usuario("nombre3", "apellido3", "operario3@prueba.es", "Id3", "123456", PerfilTipos.OPERARIO);
		Usuario op4 = new Usuario("nombre4", "apellido4", "operario4@prueba.es", "Id4", "123456", PerfilTipos.ADMIN_CM);
		Usuario op5 = new Usuario("nombre5", "apellido5", "operario5@prueba.es", "Id5", "123456",
				PerfilTipos.ANALISIS_DATOS);

		// ID's agentes
		String idAgente1 = "Id1";
		String idAgente2 = "Id2";
		String idAgente3 = "Id3";
		String idAgente4 = "Id4";

		// Creación de propiedades
		Propiedad p1 = new Propiedad(PropiedadTipos.TEMPERATURA, 100.0); /* ¿UNIDADES? */
		
		Propiedad p2 = new Propiedad(PropiedadTipos.HUMEDAD, 90.0);
		
		Propiedad p3 = new Propiedad(PropiedadTipos.PRESION, 1.1);
		
		Propiedad p4 = new Propiedad(PropiedadTipos.VELOCIDAD_CIRCULACION, 110.0);
		
		Propiedad p5 = new Propiedad(PropiedadTipos.VELOCIDAD_VIENTO, 120.0);
		
		Propiedad p6 = new Propiedad(PropiedadTipos.VALOR_NO_ASIGNADO, 0.0);
	
		// Categorias
		Categoria c1 = new Categoria(CategoriaTipos.ACCIDENTE_AEREO);
		Categoria c2 = new Categoria(CategoriaTipos.ACCIDENTE_CARRETERA);
		Categoria c3 = new Categoria(CategoriaTipos.FUEGO);
		Categoria c4 = new Categoria(CategoriaTipos.INUNDACION);
		Categoria c5 = new Categoria(CategoriaTipos.METEOROLOGICA);
		Categoria c6 = new Categoria(CategoriaTipos.VALOR_NO_ASIGNADO);

		// Creación de fechas
		Calendar Choy = Calendar.getInstance();
		Calendar CunaSemana = Calendar.getInstance();
		CunaSemana.add(Calendar.DAY_OF_MONTH, 7);

		// Set de propiedades
		Set<Propiedad> propiedades1 = new HashSet<Propiedad>();
		propiedades1.add(p1);
		propiedades1.add(p2);
		Set<Propiedad> propiedades2 = new HashSet<Propiedad>();
		propiedades1.add(p3);
		propiedades1.add(p4);
		Set<Propiedad> propiedades3 = new HashSet<Propiedad>();
		propiedades1.add(p5);
		Set<Propiedad> propiedades4 = new HashSet<Propiedad>();
		propiedades2.add(p6);

		// Set de categorias
		Set<Categoria> categorias1 = new HashSet<Categoria>();
		categorias1.add(c1);
		categorias1.add(c2);
		Set<Categoria> categorias2 = new HashSet<Categoria>();
		categorias1.add(c3);
		categorias1.add(c4);
		Set<Categoria> categorias3 = new HashSet<Categoria>();
		categorias1.add(c5);
		Set<Categoria> categorias4 = new HashSet<Categoria>();
		categorias2.add(c6);

		// Incidencias
		/*
		 * PROPIEDADES = TEMPERATURA, HUMEDAD CATEGORIAS = ACCIDENTE_AEREO,
		 * ACCIDENTE_CARRETERA ESTADO = ABIERTA - SIN OPERARIO
		 */
		Incidencia inci1 = new Incidencia("Inci1", "descripcion1", "Lat1", "Lon1", Choy.getTime(), CunaSemana.getTime(),
				idAgente1, propiedades1, categorias1);
		inci1.anularIncidencia();

		/*
		 * PROPIEDADES = PRESION, VELOCIDAD_CIRCULACION CATEGORIAS = FUEGO,
		 * INUNDACION ESTADO = ABIERTA - SIN OPERARIO
		 */
		Incidencia inci2 = new Incidencia("Inci2", "descripcion2", "Lat2", "Lon2", Choy.getTime(), CunaSemana.getTime(),
				idAgente2, propiedades2, categorias2);

		/*
		 * PROPIEDADES = VELOCIDAD_VIENTO CATEGORIAS = METEOROLOGICA ESTADO =
		 * ABIERTA - SIN OPERARIO
		 */
		Incidencia inci3 = new Incidencia("Inci3", "descripcion3", "Lat3", "Lon3", Choy.getTime(), CunaSemana.getTime(),
				idAgente3, propiedades3, categorias3);

		/*
		 * PROPIEDADES = VALOR_NO_ASIGNADO CATEGORIAS = VALOR_NO_ASIGNADO ESTADO
		 * = ABIERTA - SIN OPERARIO
		 */
		Incidencia inci4 = new Incidencia("Inci4", "descripcion4", "Lat4", "Lon4", Choy.getTime(), CunaSemana.getTime(),
				idAgente4, propiedades4, categorias4);

//		p.addPropiedad(p1);
//		p.addPropiedad(p2);
//		p.addPropiedad(p3);
//		p.addPropiedad(p4);
//		p.addPropiedad(p5);
//		p.addPropiedad(p5);
//		c.addCategoria(c1);
//		c.addCategoria(c2);
//		c.addCategoria(c3);
//		c.addCategoria(c4);
//		c.addCategoria(c5);
//		c.addCategoria(c6);
		inci1.setOperario(usuarioService.getUsuarioByIdentificador("Id1"));
		inci2.setOperario(usuarioService.getUsuarioByIdentificador("Id1"));
		inci3.setOperario(usuarioService.getUsuarioByIdentificador("Id1"));
		inci4.setOperario(usuarioService.getUsuarioByIdentificador("Id1"));
		incidenciaService.addIncidencia(inci1);
		incidenciaService.addIncidencia(inci2);
		incidenciaService.addIncidencia(inci3);
		incidenciaService.addIncidencia(inci4);
//		usuarioService.addUsuario(op1);
//		usuarioService.addUsuario(op2);
//		usuarioService.addUsuario(op3);
//		usuarioService.addUsuario(op4);
//		usuarioService.addUsuario(op5);
//		incidenciaService.init();
		
//		ValorLimite valorLimite1 = new ValorLimite(100, 10, "TEMPERATURA", true, false);
//		ValorLimite valorLimite2 = new ValorLimite(90, 10, "HUMEDAD", false, false);
//		ValorLimite valorLimite3 = new ValorLimite(110, 5, "PRESION", false, false);
//		ValorLimite valorLimite4 = new ValorLimite(320, 30, "VELOCIDAD_CIRCULACION", true, true);
//		ValorLimite valorLimite5 = new ValorLimite(400, 1,"VELOCIDAD_VIENTO", true, false);
//		ValorLimite valorLimite6 = new ValorLimite(200, 10, "NIVEL_POLUCION", true, false);
//		ValorLimite valorLimite7 = new ValorLimite(100, 0, "CALIDAD_AIRE", true, false);
//		
//		valorLimiteService.addValorLimite(valorLimite1);
//		valorLimiteService.addValorLimite(valorLimite2);
//		valorLimiteService.addValorLimite(valorLimite3);
//		valorLimiteService.addValorLimite(valorLimite4);
//		valorLimiteService.addValorLimite(valorLimite5);
//		valorLimiteService.addValorLimite(valorLimite6);
//		valorLimiteService.addValorLimite(valorLimite7);
	}
	
	//@PreDestroy
	public void end() {
		c.deleteAll();
		incidenciaService.deleteAll();
		p.deleteAll();
		usuarioService.deleteAll();
	}
}
