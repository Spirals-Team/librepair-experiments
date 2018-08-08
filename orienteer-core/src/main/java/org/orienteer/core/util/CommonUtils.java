package org.orienteer.core.util;

import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;
import org.orienteer.core.OrienteerWebApplication;
import org.orienteer.core.OrienteerWebSession;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * Common for Orienteer utility methods
 */
public class CommonUtils {
	
	private CommonUtils() {
		
	}

	/**
	 * Converts given objects into map.
	 * Uses pairs of object.
	 * Call toMap("key1", "value1", "key2", "value2") will returns this map:
	 * { "key1": "value1", "key2": "value2" }
	 * Call method with not pair arguments will throw {@link IllegalStateException}.
	 * For example: toMap("key1", "value1", "key2") - throws {@link IllegalStateException}
	 * @param objects {@link Object[]} array of objects which will be used for create new map
	 * @param <K> type of map key
	 * @param <V> type of map value
	 * @return {@link Map<K, V>} created from objects
	 * @throws IllegalStateException if objects are not pair
	 */
	public static final <K, V> Map<K, V> toMap(Object... objects) {
		if(objects==null || objects.length % 2 !=0) throw new IllegalArgumentException("Illegal arguments provided to construct a map");
		Map<K, V> ret = new HashMap<K, V>();
		for(int i=0; i<objects.length; i+=2) {
			ret.put((K)objects[i], (V)objects[i+1]);
		}
		return ret;
	}
	
	public static final Object localizeByMap(Map<String, ?> map, boolean returnFirstIfNoMatch, String... languages) {
		if(map==null) return null;
		for(int i=0; i<languages.length;i++) {
			if(map.containsKey(languages[i])) return map.get(languages[i]);
		}
		if(returnFirstIfNoMatch && !map.isEmpty()) return map.values().iterator().next();
		else return null;
	}

	/**
	 * Converts value to string. See {@link CommonUtils#objectToString(Object)}
	 * @param value {@link String} value which need convert to string
	 * @return {@link String} converted value to string or empty string if can't convert value to string or value is null
	 */
	public static final String objectToString(Object value) {
		return objectToString(value, "");
	}

	/**
	 * Convert given object to localized string
	 * Uses Wicket {@link IConverter}
	 * @param value {@link Object} value which need convert to string
	 * @param defaultValue {@link String} default value if can't convert value to string or value is null
	 * @return {@link String} value converted into string
	 */
	public static final String objectToString(Object value, String defaultValue) {
		String ret = null;
		if(value!=null) {
			final Class<?> objectClass = value.getClass();
			final IConverter converter = OrienteerWebApplication.get().getConverterLocator().getConverter(objectClass);
			ret = converter.convertToString(value, OrienteerWebSession.get().getLocale());
		}
		return ret!=null?ret:defaultValue;
	}

	/**
	 * Convert content to JavaScript string.
	 * Added '"' at start and end of content
	 * Replaces all '\n' by '\\n"'
	 * @param content {@link CharSequence} content
	 * @return {@link CharSequence} JavaScript string or "null" if content is null
	 */
	public static final CharSequence escapeAndWrapAsJavaScriptString(CharSequence content) {
		if(content==null) return "null";
		else {
			content = JavaScriptUtils.escapeQuotes(content);
			content = "\"" + content + "\""; 
			content = Strings.replaceAll(content, "\n", "\" + \n\"");
			return content;
		}
	}

	/**
	 * Convert content to JSON string.
	 * Replace all '\"' by '\\\"'.
	 * Replace all '\n' by '\\n\"'
	 * @param content {@link CharSequence} content
	 * @return {@link CharSequence} JSON string or empty string "" if content is null
	 */
	public static final CharSequence escapeStringForJSON(CharSequence content) {
		if(content==null) return "";
		else {
			content = Strings.replaceAll(content, "\"", "\\\"");
			content = Strings.replaceAll(content, "\n", "\" + \n\"");
			return content;
		}
	}

	/**
	 * Map given list of {@link OIdentifiable} uses given mapping function mapFunc
	 * Before apply mapFunc loads record and cast it to {@link ODocument} from identifiable, by calling {@link OIdentifiable#getRecord()}
	 * If record is null, so discard this identifiable and doesn't apply mapFunc to it.
	 * @param identifiables {@link OIdentifiable} list of {@link OIdentifiable} for map to value
	 * @param mapFunc {@link Function<ODocument, T>} map function
	 * @param <T> - type of return value in map function
	 * @return {@link List<T>} mapped list of {@link OIdentifiable} or empty list if identifiables is null
	 */
	public static <T> List<T> mapIdentifiables(List<OIdentifiable> identifiables, Function<ODocument, T> mapFunc) {
		if (identifiables == null) {
			return Collections.emptyList();
		}
		return identifiables.stream()
				.map(i -> (ODocument) i.getRecord())
				.filter(Objects::nonNull)
				.map(mapFunc)
				.collect(Collectors.toList());
	}

	/**
	 * Get list of documents from list of identifiables.
	 * Uses {@link CommonUtils#mapIdentifiables(List, Function)}
	 * @param identifiables {@link List<OIdentifiable>} identifiables
	 * @return {@link List<ODocument>} list of documents
	 */
	public static List<ODocument> getDocuments(List<OIdentifiable> identifiables) {
		return mapIdentifiables(identifiables, d -> d);
	}

	/**
	 * Get first item in identifiables and apply mapFunc
	 * First {@link OIdentifiable} from identifiables load record and cast it to {@link ODocument}.
	 * If record is null, so mapFunc doesn't apply to it and returns null.
	 * @param identifiables {@link List<OIdentifiable>} identifiables
	 * @param mapFunc {@link Function<ODocument, T>} map function which will by apply for record from first item in identifiables
	 * @param <T> type of return value by mapFunc
	 * @return {@link Optional<T>} mapped record from first item in identifiables or {@link Optional#empty()}
	 * if identifiable is empty or can't load record from first identifiable
	 */
	public static <T> Optional<T> getFromIdentifiables(List<OIdentifiable> identifiables, Function<ODocument, T> mapFunc) {
		return isNotEmpty(identifiables) ? getFromIdentifiable(identifiables.get(0), mapFunc) : empty();
	}

	/**
	 * Map record from identifiable, using map function
	 * If can't load record and cast it to {@link ODocument} from identifiable, so returns null
	 * @param identifiable {@link OIdentifiable} identifiable for map
	 * @param mapFunc {@link Function<ODocument, T>} map function which will be apply for record loaded from identifiable
	 * @param <T> type of return value by mapFunc
	 * @return {@link Optional<T>} mapped record or {@link Optional#empty()} if identifiable is null, or can't load record
	 */
	public static <T> Optional<T> getFromIdentifiable(OIdentifiable identifiable, Function<ODocument, T> mapFunc) {
		if (identifiable != null) {
			ODocument doc = identifiable.getRecord();
			return doc != null ? ofNullable(mapFunc.apply(doc)) : empty();
		}
		return empty();
	}

	/**
	 * Check if given collection is not empty
	 * @param collection {@link Collection<T>} collection
	 * @param <T> type of collection
	 * @return true if collection is not empty
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection) {
		return collection != null && !collection.isEmpty();
	}

	/**
	 * Load record and cast it to {@link ODocument} from first ite in identifiables
	 * @param identifiables {@link List <OIdentifiable>} identifiables
	 * @return {@link Optional<ODocument>} get first document or {@link Optional#empty()}
	 */
	public static Optional<ODocument> getDocument(List<OIdentifiable> identifiables) {
		return isNotEmpty(identifiables) ? ofNullable(identifiables.get(0).getRecord()) : empty();
	}
}
