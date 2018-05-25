package recommender.evaluation;

import java.util.Iterator;

import cc.kave.commons.model.events.completionevents.IProposal;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.codeelements.IParameterName;
import cc.kave.commons.model.naming.codeelements.IPropertyName;
import cc.kave.commons.model.naming.types.ITypeName;

public class NameUtility {
	
	public static String getName(IProposal actualSelection) {
		if(actualSelection == null) {
			return null;
		}
		if(actualSelection.getName() instanceof IMethodName) {
			return getName((IMethodName) actualSelection.getName());
		}
		else if(actualSelection.getName() instanceof ITypeName) {
			return getName((ITypeName) actualSelection.getName());
		}
		else if(actualSelection.getName() instanceof IPropertyName) {
			return getName((IPropertyName) actualSelection.getName());
		}
		else {
			return null;
		}
	}

	private static String getName(IMethodName method) {
		String simpleName = method.getDeclaringType().getFullName();
		if (!method.isConstructor()) {
			simpleName += "." + method.getName();
		}
		simpleName += "(";
		Iterator<IParameterName> iter = method.getParameters().iterator();
		while (iter.hasNext()) {
			IParameterName param = iter.next();
			simpleName += param.getValueType().getFullName() + " " + param.getName();
			if (iter.hasNext()) {
				simpleName += ", ";
			}
		}
		simpleName += ")";
		return simpleName;
	}

	private static String getName(ITypeName typeName) {
		String name = "(" + typeName.getFullName() + ") object";
		return name;
	}

	private static String getName(IPropertyName propertyName) {
		return propertyName.getDeclaringType().getFullName() + "." + propertyName.getFullName();
	}

}
