package nc.noumea.mairie.bilan.energie.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nc.noumea.mairie.bilan.energie.core.utils.EnumUtils;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Moteur de conversion des beans d'un format à un autre
 * 
 * @author Greg Dujardin
 *
 */
@Service("convertManager")
@Scope(value="singleton")
public class DefaultConvertManager implements ConvertManager {

	/** Fichier de mapping par défaut */
	public final static String DEFAULT_MAPPING = "app-mapping.xml";

	/**
	 * Moteur de mapping
	 */
	private transient Mapper mapper;

	private static ConvertManager cm = null;

	/**
	 * Récupération de l'instance du ConvertManager
	 * 
	 * @return ConverManager
	 */
	public static ConvertManager getInstance() {

		if (cm == null)
			synchronized (DefaultConvertManager.class) {
				if (cm == null)
					cm = new DefaultConvertManager();
			}

		return cm;
	}

	/**
	 * Constructeur par défaut
	 */
	private DefaultConvertManager() {

		final List<String> mappingFiles = new ArrayList<String>(2);

		mappingFiles.add(DEFAULT_MAPPING);

		DozerBeanMapper dozerMapper = new DozerBeanMapper(mappingFiles); 
		this.mapper = dozerMapper;
		
		dozerMapper.setEventListeners(Collections.singletonList(new ConverterEventListener()));
	}

	/**
	 * Conversion d'un bean
	 * 
	 * @param src Objet source
	 * @param dst objet destination
	 * @return T
	 */
	public <T> T convert(final Object src, final Class<T> dst) {

		// correction du type de sortie si type natif
		final T res;

		if (src == null) {
			res = null;
		} else if (dst.isInstance(src)) {
			res = dst.cast(src);
		} else if (Enum.class.isAssignableFrom(dst)) {

			if (src instanceof String) {
				final String enumValue = (String) src;
				res = EnumUtils.parseEnum(dst, enumValue);
			} else {
				final int ordinal = this.convert(src, int.class);
				res = EnumUtils.parseEnum(dst, ordinal);
			}

		} else {
			res = this.mapper.map(src, dst);
		}

		return res;
	}

	/**
	 * Conversion d'un bean
	 * 
	 * @param src Objet source
	 * @param dst objet destination
	 * @return T
	 */
	public <T> T convert(final Object src, final T dst) {

		if (src == null) {
			return null;
		} else {
			this.mapper.map(src, dst);
		}

		return dst;
	}

	/**
	 * Conversion d'un liste de bean 
	 * 
	 * @param src liste d'objet source
	 * @param dst liste d'objet destination
	 * @return Liste convertie
	 */
	public <T> List<T> convertList(final List<?> src, final Class<T> dst) {

		final List<T> res;
		if (src == null) {
			res = null;
		} else {
			res = new ArrayList<T>(src.size());

			for (final Object o : src) {
				res.add(this.convert(o, dst));
			}
		}

		return res;
	}
}
