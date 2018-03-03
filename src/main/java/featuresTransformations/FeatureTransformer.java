package featuresTransformations;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import geometrictransformations.AbstractTransformation;
import geometrictransformations.TransformationType;
/**
 * 
 * @author Iesseddik
 * Classe  Singleton (source : wikipedia)
 */
public final class FeatureTransformer {
	private static FeatureTransformer instance = null;
	// attribut de class, null avant la premier instanciation
	private List<SimpleFeature> features ;
	private AbstractTransformation transformationType;
	private FeatureTransformer(){
		super();
	}
	
	/**
	 * Constructeur
	 * @param features liste d'objets feature Geotools
	 * @param AbstractTransformation objet heritant de la classe abstraite AbstractTransformation
	 */
	public FeatureTransformer(List<SimpleFeature> features, AbstractTransformation transformationType) {
		this.features = features;
		this.transformationType = transformationType;
		
	}
	
	/**
	 * @return retourne l'unique instance 
	 */
	public final static FeatureTransformer getInstance() {
        if (FeatureTransformer.instance == null) {
           // pour empêcher toute instanciation multiple
           synchronized(FeatureTransformer.class) {
             if (FeatureTransformer.instance == null) {
            	 FeatureTransformer.instance = new FeatureTransformer();
             }
           }
        }
        return FeatureTransformer.instance;
    }

	/**
	 * @return retourne l'attribut désignant la transformation utilisé 
	 */
	public AbstractTransformation getTransformationType() {
		return transformationType;
	}
	
	
	//----------------------------Main-------------------------------
	
	public static void main(String[] args) {
		for(TransformationType TF : TransformationType.values()){
		      if(TransformationType.Translation.equals(TF))
		        System.out.println("initialisation d'une " + TF);
		      else
		        System.out.println(TF + "Non implémenté");
		    }
	}

	public List<SimpleFeature> getFeatures() {
		return features;
	}

}

