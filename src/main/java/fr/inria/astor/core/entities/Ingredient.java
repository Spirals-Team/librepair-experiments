package fr.inria.astor.core.entities;

import fr.inria.astor.core.loop.spaces.ingredients.scopes.IngredientSpaceScope;
import spoon.reflect.declaration.CtElement;

/**
 * 
 * @author Matias Martinez matias.martinez@inria.fr
 *
 */
public class Ingredient {

	protected CtElement code;
	protected IngredientSpaceScope scope;
	protected CtElement derivedFrom;
	// Store the value of code var
	protected String cacheString = null;

	public Ingredient(CtElement code, IngredientSpaceScope scope, CtElement derivedFrom) {
		super();
		this.code = code;
		this.derivedFrom = derivedFrom;
		this.scope = scope;
	}

	public Ingredient(CtElement element) {
		super();
		this.code = element;
	}

	public Ingredient(CtElement element, IngredientSpaceScope scope) {
		super();
		this.code = element;
		this.scope = scope;
	}

	public CtElement getCode() {
		return code;
	}

	public void setCode(CtElement element) {
		this.code = element;
	}

	public IngredientSpaceScope getScope() {
		return scope;
	}

	public void setScope(IngredientSpaceScope scope) {
		this.scope = scope;
	}

	public CtElement getDerivedFrom() {
		return derivedFrom;
	}

	public void setDerivedFrom(CtElement derivedFrom) {
		this.derivedFrom = derivedFrom;
	}

	@Override
	public String toString() {
		return "Ingredient [code=" + getCode() + "]";
	}

	/**
	 * Stores the value of code value. The first invocation it calculate it.
	 * 
	 * @return
	 */
	public String getChacheCodeString() {
		if (cacheString == null && this.code != null) {
			cacheString = this.code.toString();
		}
		return cacheString;
	}

}
