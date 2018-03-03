/**
 * Junit test class
 */
package featuresTransformationsTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.geotools.data.DataUtilities;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.GeometryBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import featuresTransformations.FeatureTransformer;
import geometrictransformations.AbstractTransformation;
import geometrictransformations.Translation;

/**
 * @author Iesseddik
 * On souhaite tester unitairement un objet instancié par la classe (singleton) 
 * FeatureTransformer : instanciation, lecture de log, setters et getters, transformer des features et les exporter en shape. 
 * 
 *
 */
public class FeatureTransformerTest {
	//private String pathIn = "src/test/ressources/test_ressources.csv";
	private String pathOut= "src/test/ressources/test_result.shp";;
	private List <Point> lp; 
	private List<SimpleFeature> features;
	private GeometryBuilder builder;
	private FeatureTransformer featureTransformer;
	private AbstractTransformation translation;

	/**
	 * Avant chaque test, on initialise une list de feature 
	 * @throws SchemaException 
	 */
	@Before
	public void setUp() throws SchemaException {
		
		GeometryFactory gf = new GeometryFactory();        
		lp = new ArrayList<Point>();// creation d'une liste de points

		lp.add(gf.createPoint( new Coordinate(  48.55, -123.57 ) ));
		lp.add(gf.createPoint( new Coordinate(  48.66, -123.67 ) ));
		lp.add(gf.createPoint( new Coordinate(  48.77, -123.77 ) ));
		lp.add(gf.createPoint( new Coordinate(  48.88, -123.87 ) ));

		SimpleFeatureType sft = DataUtilities.createType("Location", "geom:Point,name:String");
		features = new ArrayList<SimpleFeature>();
		for (Point p :lp){
			SimpleFeatureBuilder sfb = new SimpleFeatureBuilder(sft);
			sfb.add(p);
			SimpleFeature sf = sfb.buildFeature(null);
			features.add(sf);
		}
		
		translation = Mockito.mock(Translation.class);
		featureTransformer = new FeatureTransformer(features, translation);
		
	}
	
	/**
	* Test d'instanciation d'un objet FeatureTransformer 
	*/
	@Test
	public void TestInstanciation() {
		assertEquals(featureTransformer.getInstance(), FeatureTransformer.getInstance());
		//pour savoir si l'instance est bien unique 
	}
	
	/**
	* Test de l'affichage de son log
	*/
	@Test
	public void TestTransformLog() {
		Mockito.when(translation.getDetailLog()).thenReturn("Type:Translation, precision:0.5");
		featureTransformer.getTransformationType();
		assertEquals(featureTransformer.getTransformationType().getDetailLog(), "Type:Translation, precision:0.5");
	}
	
	/**
	* Test du getter sur l'attribut features
	*
	*/
	@Test
	public void TestFeatureGetter() {
		assertEquals(featureTransformer.getFeatures() , features);
	}
	
	/**
	* Test du setter sur l'attribut features.
	*
	*/
	/*@Test
	public void TestFeatureSetter() {
		fail("not implemented yet");
	}*/
	
	/**
	* Test de la transformation sur l'attribut features
	*
	*/
	/*@Test
	public void TestTransform() {
		fail("not implemented yet");
	}*/
	
	/**
	 * Test de l'export en shp
	 * 
	*/
	/*@Test
	public void TestExportToShp() {
		fail("not implemented yet");
	}*/
	
}
