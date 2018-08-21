//$Id: BasicContext.java 10438 2009-04-17 21:33:42Z norman.richards@jboss.com $
package org.jboss.seam.contexts;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.Seam;
import org.jboss.seam.core.Events;

/**
 * A basic implementation of Context that keeps the state in a Map.
 * 
 * @author Gavin King
 */
public class BasicContext implements Context {

	private final Map<String, Object> map;
	private final ScopeType scope;

	public BasicContext(ScopeType scope) {
		this(scope, new HashMap<String, Object>());
	}

	protected BasicContext(ScopeType scope, Map<String, Object> map) {
		this.scope = Objects.requireNonNull(scope, "scope can not be null");
		this.map = Objects.requireNonNull(map, "map can not be null");
	}

	public ScopeType getType() {
		return scope;
	}

	public Object get(Class<?> clazz) {
		return get(Component.getComponentName(clazz));
	}

	public Object get(String name) {
		return map.get(name);
	}

	public String[] getNames() {
		return map.keySet().toArray(new String[0]);
	}

	public boolean isSet(String name) {
		return map.containsKey(name);
	}

	public void remove(String name) {
		if (Events.exists()) {
			Events.instance().raiseEvent("org.jboss.seam.preRemoveVariable." + name);
		}
		map.remove(name);
		if (Events.exists()) {
			Events.instance().raiseEvent("org.jboss.seam.postRemoveVariable." + name);
		}
	}

	public void set(String name, Object value) {
		// We can't raise a preSetVariable event for Events itself because it doesn't
		// exist yet...
		if (!Seam.getComponentName(Events.class).equals(name) && Events.exists()) {
			Events.instance().raiseEvent("org.jboss.seam.preSetVariable." + name);
		}
		map.put(name, value);
		if (Events.exists())
			Events.instance().raiseEvent("org.jboss.seam.postSetVariable." + name);
	}

	public void flush() {
	}

	@Override
	public String toString() {
		return "BasicContext(" + scope + ")";
	}

}
