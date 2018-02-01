/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.pattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import spoon.SpoonException;
import spoon.compiler.Environment;
import spoon.pattern.matcher.ListMatch;
import spoon.pattern.matcher.TemplatesList;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ParentNotInitializedException;
import spoon.reflect.factory.Factory;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import spoon.reflect.visitor.PrinterHelper;

/**
 *
 */
public class ModelValueResolver implements ValueResolver {
	private ModelValueResolver parent;
	private final Factory factory;
	private final List<CtElement> patternModel;
	private final Map<CtElement, AbstractNodeSubstitutionRequest> patternElementToSubstRequests = new IdentityHashMap<>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	ModelValueResolver(Factory factory, List<? extends CtElement> patternModel) {
		this.factory = factory;
		this.patternModel = Collections.unmodifiableList((List) patternModel);
	}

	public ModelValueResolver getParent() {
		return parent;
	}

	public ModelValueResolver setParent(ModelValueResolver parent) {
		this.parent = parent;
		return this;
	}

	/**
	 * @return a pattern model of this pattern
	 */
	public List<CtElement> getModel() {
		return patternModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void resolveValues(ResultHolder<T> result, ParameterValueProvider parameters) {
		SubstitutionCloner cloner = new SubstitutionCloner(this, parameters);
		List<CtElement> substClone = cloner.clone(patternModel);
		for (CtElement ele : substClone) {
			if (result.getRequiredClass().isInstance(ele) == false) {
				throw new SpoonException("Cannot substitute required value of type " + result.getRequiredClass() + " for item " + ele.getClass());
			}
			result.addResult((T) ele);
		}
	}

//	@Override
//	public boolean matches(ParameterValueProvider parameters, Object target) {
//		if ((target instanceof CtElement) == false) {
//			return false;
//		}
//		if (patternModel.size() != 1) {
//			throw new SpoonException("Cannot match multivalue pattern using matches(parameters, target). Use TODO");
//		}
//		boolean found = PatternMatcher.matchElement(this, parameters, (CtElement) target, patternModel.get(0));
//		return found;
//	}

	@Override
	public ListMatch matches(List<? extends Object> targets, ParameterValueProvider parameters, TemplatesList templates) {
		//first check if this model is matching
		ListMatch match = new TemplatesList(this, false).matchAllWith(targets, parameters);
		if (match.isMatching() == false) {
			return match;
		}
		//if matching then check if next templates are matching
		return templates.matchAllWith(match.getTargets(), match.getParameters());
	}

	public Map<String, ParameterInfo> getParameters() {
		Map<String, ParameterInfo> parameters = new HashMap<>();
		forEachParameterInfo((parameter, valueResolver) -> {
			ParameterInfo existingParameter = parameters.get(parameter.getName());
			if (existingParameter != null) {
				if (existingParameter == parameter) {
					//OK, this parameter is already there
					return;
				}
				throw new SpoonException("There is already a parameter: " + parameter.getName());
			}
			parameters.put(parameter.getName(), parameter);
		});
		return Collections.unmodifiableMap(parameters);
	}

	public void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer) {
		for (AbstractNodeSubstitutionRequest nodeSubstReq : patternElementToSubstRequests.values()) {
			nodeSubstReq.forEachParameterInfo(consumer);
		}
		if (parent != null) {
			forEachSubstitutionRequest((element, nodeSubstReq) -> {
				if (isInModel(element)) {
					nodeSubstReq.forEachParameterInfo(consumer);
				}
			});
		}
	}

	public void forEachSubstitutionRequest(BiConsumer<CtElement, AbstractNodeSubstitutionRequest> consumer) {
		patternElementToSubstRequests.forEach(consumer);
	}

	/**
	 * @param patternModelElement to be checked element of pattern model
	 * @return {@link SubstitutionRequest} linked to the `patternModelElement` or null if this element is not a target for the parameter substitution
	 */
	public AbstractNodeSubstitutionRequest getSubstitutionRequest(Object patternModelElement) {
		AbstractNodeSubstitutionRequest nsr = patternElementToSubstRequests.get(patternModelElement);
		if (nsr == null && parent != null) {
			nsr = parent.getSubstitutionRequest(patternModelElement);
		}
		return nsr;
	}


	/**
	 * Adds request to substitute `element`, by the value of this {@link ModelValueResolver} parameter {@link ParameterInfo} value
	 * @param element to be replaced element
	 */
	NodeSubstitutionRequest addSubstitutionRequest(ValueResolver valueResolver, CtElement element) {
		AbstractNodeSubstitutionRequest existingSR = patternElementToSubstRequests.get(element);
		if (existingSR != null) {
			if (existingSR instanceof NodeSubstitutionRequest && existingSR.getSubstitutedNode() == element) {
				return (NodeSubstitutionRequest) existingSR;
			}
			getFactory().getEnvironment().debugMessage("Attribute substitution request:\n" + existingSR + " was replaced by NodeSubstitutionRequest: \n" + valueResolver);
		}
		NodeSubstitutionRequest rr = new NodeSubstitutionRequest(this, valueResolver, element);
		patternElementToSubstRequests.put(element, rr);
		return rr;
	}

	public void addAttributeSubstRequest(CtElement element, CtRole attributeRole, Consumer<NodeAttributeSubstitutionRequest> attrSubstRequestCreator) {
		AbstractNodeSubstitutionRequest sr = patternElementToSubstRequests.get(element);
		NodeAttributesSubstitionRequest attrSubstReq;
		if (sr != null) {
			if (sr instanceof NodeSubstitutionRequest) {
				//Cannot add attribute substitution request to node, which already contains a NodeSubstitutionRequest
				//NodeSubstitutionRequest has higher priority
				return;
			}
			attrSubstReq = (NodeAttributesSubstitionRequest) sr;
		} else {
			attrSubstReq = new NodeAttributesSubstitionRequest(this, element);
			patternElementToSubstRequests.put(element, attrSubstReq);
		}
		NodeAttributeSubstitutionRequest asr = attrSubstReq.getAttributeSubstititionRequest(attributeRole);
		if (asr == null) {
			asr = new NodeAttributeSubstitutionRequest(attrSubstReq, attributeRole);
			attrSubstReq.addAttributeSubstitutionRequest(asr);
		}
		attrSubstRequestCreator.accept(asr);
	}

	/**
	 * Removes all substitution requests of element
	 * @param element
	 */
	public AbstractNodeSubstitutionRequest removeSubstitutionRequest(CtElement element) {
		return patternElementToSubstRequests.remove(element);
	}

	/**
	 * @param element to be checked element
	 * @return true if element `element` is a template or a child of template
	 */
	public boolean isInModel(CtElement element) {
		if (element != null) {
			for (CtElement patternElement : patternModel) {
				if (element == patternElement || element.hasParent(patternElement)) {
					return true;
				}
			}
		}
		return false;
	}

//	public ValueConvertor getValueConvertor() {
//		return valueConvertor;
//	}
//
//	public void setValueConvertor(ValueConvertor valueConvertor) {
//		this.valueConvertor = valueConvertor;
//	}

	private static class SubstReqOnPosition {
		int sourceStart;
		AbstractNodeSubstitutionRequest substReq;
		SubstReqOnPosition(int sourceStart, AbstractNodeSubstitutionRequest substReq) {
			this.sourceStart = sourceStart;
			this.substReq = substReq;
		}

		@Override
		public String toString() {
			return String.valueOf(sourceStart) + ":" + substReq;
		}
	}

	@Override
	public String toString() {
		Factory f = getFactory();
		Environment env = f.getEnvironment();
		final List<SubstReqOnPosition> allRequestWithSourcePos = new ArrayList<>();
		for (Map.Entry<CtElement, AbstractNodeSubstitutionRequest> e : patternElementToSubstRequests.entrySet()) {
			allRequestWithSourcePos.add(new SubstReqOnPosition(getSourceStart(e.getKey()), e.getValue()));
		}
		allRequestWithSourcePos.sort((a, b) -> a.sourceStart - b.sourceStart);
		class Iter {
			int off = 0;
			List<SubstReqOnPosition> getAndRemoveRequestUntil(int sourcePos) {
				List<SubstReqOnPosition> res = new ArrayList<>();
				while (off < allRequestWithSourcePos.size() && allRequestWithSourcePos.get(off).sourceStart <= sourcePos) {
					res.add(allRequestWithSourcePos.get(off));
					off++;
				}
				return res;
			}
		}
		Iter iter = new Iter();
//		PrinterHelper printerHelper = new PrinterHelper(env);
//		DefaultTokenWriter tokenWriter = new DefaultTokenWriter(printerHelper);
		DefaultJavaPrettyPrinter printer = new DefaultJavaPrettyPrinter(env) {
			protected void enter(CtElement e) {
				int sourceStart = getSourceStart(e);
				List<SubstReqOnPosition> requestOnPos = iter.getAndRemoveRequestUntil(sourceStart);
				if (requestOnPos.size() > 0) {
					getPrinterTokenWriter()
						.writeComment(f.createComment(getSubstitutionRequestsDescription(e, sourceStart, requestOnPos), CommentType.BLOCK))
						.writeln();
				}
			}

		};
		try {
			for (CtElement ele : patternModel) {
				printer.computeImports(ele);
			}
			for (CtElement ele : patternModel) {
				printer.scan(ele);
			}
		} catch (ParentNotInitializedException ignore) {
			return "Failed with: " + ignore.toString();
		}
		// in line-preservation mode, newlines are added at the beginning to matches the lines
		// removing them from the toString() representation
		return printer.toString().replaceFirst("^\\s+", "");
	}

	private int getSourceStart(CtElement ele) {
		while (true) {
			SourcePosition sp = ele.getPosition();
			if (sp != null && sp.getSourceStart() >= 0) {
				//we have found a element with source position
				return sp.getSourceStart();
			}
			if (ele.isParentInitialized() == false) {
				return -1;
			}
			ele = ele.getParent();
		}
	}

	private String getSubstitutionRequestsDescription(CtElement ele, int sourceStart, List<SubstReqOnPosition> requestsOnPos) {
		//sort requestsOnPos by their path
		Map<String, SubstReqOnPosition> reqByPath = new TreeMap<>();
		StringBuilder sb = new StringBuilder();
		for (SubstReqOnPosition reqPos : requestsOnPos) {
			sb.setLength(0);
			appendPathIn(sb, reqPos.substReq.getSubstitutedNode(), ele);
			String path = sb.toString();
			reqByPath.put(path, reqPos);
		}

		PrinterHelper printer = new PrinterHelper(getFactory().getEnvironment());
		//all comments in Spoon are using \n as separator
		printer.setLineSeparator("\n");
		printer.write(getElementTypeName(ele)).incTab();
		for (Map.Entry<String, SubstReqOnPosition> e : reqByPath.entrySet()) {
			printer.writeln();
			boolean isLate = e.getValue().sourceStart != sourceStart;
			if (isLate) {
				printer.write("!").write(String.valueOf(e.getValue().sourceStart)).write("!=").write(String.valueOf(sourceStart)).write("!");
			}
			printer.write(e.getKey()).write('/');
			e.getValue().substReq.appendDescription(printer);
		}
		return printer.toString();
	}

	private boolean appendPathIn(StringBuilder sb, CtElement element, CtElement parent) {
		if (element != parent && element != null) {
			CtRole roleInParent = element.getRoleInParent();
			if (roleInParent == null) {
				return false;
			}
			if (appendPathIn(sb, element.getParent(), parent)) {
				sb.append("/").append(getElementTypeName(element.getParent()));
			}
			sb.append(".").append(roleInParent.getCamelCaseName());
			return true;
		}
		return false;
	};

	static String getElementTypeName(CtElement element) {
		String name = element.getClass().getSimpleName();
		if (name.endsWith("Impl")) {
			return name.substring(0, name.length() - 4);
		}
		return name;
	}


	public Factory getFactory() {
		return factory;
	}

	public List<ValueResolver> getValueResolversOfParameter(ParameterInfo pi) {
		List<ValueResolver> vrs = new ArrayList<>();
		forEachParameterInfo((paramInfo, vr) -> {
			if (paramInfo == pi) {
				vrs.add(vr);
			}
		});
		return vrs;
	}
	/**
	 * @param patternModelElement
	 * @return assigned {@link ValueResolver} if this template element is substituted, else return null.
	 */
	public ValueResolver getNodeValueResolver(CtElement patternModelElement) {
		AbstractNodeSubstitutionRequest substReq = getSubstitutionRequest(patternModelElement);
		if (substReq != null) {
			return substReq.getValueResolver();
		}
		return null;
	}
	public NodeAttributesSubstitionRequest getNodeAttributesSubstitionRequest(CtElement patternModelElement) {
		AbstractNodeSubstitutionRequest substReq = getSubstitutionRequest(patternModelElement);
		if (substReq instanceof NodeAttributesSubstitionRequest) {
			return (NodeAttributesSubstitionRequest) substReq;
		}
		return null;
	}
}
