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
import spoon.pattern.matcher.Matchers;
import spoon.pattern.matcher.ChainOfMatchersImpl;
import spoon.pattern.matcher.TobeMatched;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ParentNotInitializedException;
import spoon.reflect.factory.Factory;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import spoon.reflect.visitor.PrinterHelper;

/**
 * The AST model based parameterized model, which can generate or match other AST models.
 *
 * The instance is created by {@link PatternBuilder}
 */
public class ModelValueResolver implements ValueResolver, SubstitutionRequestProvider, Matchers {

	/**
	 * optional parent {@link ModelValueResolver}
	 */
	private final ModelValueResolver parent;
	private final Factory factory;
	private final List<CtElement> patternModel;
	private final Map<CtElement, ValueResolver> patternElementToSubstRequests = new IdentityHashMap<>();

	private final Map<String, ParameterInfo> parameterInfos = new HashMap<>();
	private ValueConvertor defaultValueConvertor;

	ModelValueResolver(Factory factory, List<? extends CtElement> patternModel, ValueConvertor defaultValueConvertor) {
		this(null, factory, patternModel, defaultValueConvertor);
	}
	ModelValueResolver(ModelValueResolver parent, List<? extends CtElement> patternModel) {
		this(parent, parent.factory, patternModel, null);
	}
	private ModelValueResolver(ModelValueResolver parent, Factory factory, List<? extends CtElement> patternModel, ValueConvertor defaultValueConvertor) {
		this.parent = parent;
		this.factory = factory;
		this.patternModel = Collections.unmodifiableList((List) patternModel);
		if (parent == null && defaultValueConvertor == null) {
			throw new SpoonException("Default ValueConvertor must be defined");
		}
		this.defaultValueConvertor = defaultValueConvertor;
	}

	/**
	 * @return parent {@link ModelValueResolver} or null, if this is not a child
	 */
	public ModelValueResolver getParent() {
		return parent;
	}

	/**
	 * @return a {@link List} of AST model nodes of this {@link ModelValueResolver}
	 */
	public List<CtElement> getModel() {
		return patternModel;
	}

	public ParameterInfo getParameterInfo(String parameterName, boolean createIfNotExist) {
		ParameterInfo pi = parameterInfos.get(parameterName);
		if (pi == null) {
			pi = new ParameterInfo(getDefaultValueConvertor(), parameterName);
			parameterInfos.put(parameterName, pi);
		}
		return pi;
	}

	public boolean hasParameterInfo(String parameterName) {
		return parameterInfos.containsKey(parameterName);
	}

	public ValueConvertor getDefaultValueConvertor() {
		if (defaultValueConvertor == null) {
			return parent.getDefaultValueConvertor();
		}
		return defaultValueConvertor;
	}

	public void setDefaultValueConvertor(ValueConvertor defaultValueConvertor) {
		this.defaultValueConvertor = defaultValueConvertor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void generateTargets(ResultHolder<T> result, ParameterValueProvider parameters) {
		SubstitutionCloner cloner = new SubstitutionCloner(this, parameters);
		List<CtElement> substClone = cloner.clone(patternModel);
		for (CtElement ele : substClone) {
			if (result.getRequiredClass().isInstance(ele) == false) {
				throw new SpoonException("Cannot substitute required value of type " + result.getRequiredClass() + " for item " + ele.getClass());
			}
			result.addResult((T) ele);
		}
	}

	public void forEachParameterInfo(BiConsumer<ParameterInfo, Parameterized> consumer) {
		for (ValueResolver vr : patternElementToSubstRequests.values()) {
			vr.forEachParameterInfo(consumer);
		}
		if (parent != null) {
			forEachSubstitutionRequest((element, nodeSubstReq) -> {
				if (isInModel(element)) {
					nodeSubstReq.forEachParameterInfo(consumer);
				}
			});
		}
	}

	/**
	 * Calls `consumer` once for each {@link Parameterized} element which uses `parameter`
	 * @param parameter to be checked {@link ParameterInfo}
	 * @param consumer receiver of calls
	 */
	public void forEachParameterizedOf(ParameterInfo parameter, Consumer<Parameterized> consumer) {
		forEachParameterInfo((paramInfo, vr) -> {
			if (paramInfo == parameter) {
				consumer.accept(vr);
			}
		});
	}

	public void forEachSubstitutionRequest(BiConsumer<CtElement, ValueResolver> consumer) {
		patternElementToSubstRequests.forEach(consumer);
	}

	/**
	 * Adds request to substitute `element`, by the value of this {@link ModelValueResolver} parameter {@link ParameterInfo} value
	 * @param element to be replaced element
	 */
	void addSubstitutionRequest(ValueResolver valueResolver, CtElement element) {
		ValueResolver existingVR = patternElementToSubstRequests.get(element);
		if (existingVR != null) {
			if (existingVR == valueResolver) {
				return;
			}
			if (existingVR instanceof NodeMatcher) {
				getFactory().getEnvironment().debugMessage("Attribute substitution request:\n" + existingVR + " was replaced by NodeSubstitutionRequest: \n" + valueResolver);
			} else {
				throw new SpoonException("ValueResolver cannot be replaced by another one");
			}
		}
		patternElementToSubstRequests.put(element, valueResolver);
	}

	public void addAttributeSubstRequest(CtElement element, CtRole attributeRole, Consumer<NodeAttributeSubstitutionRequest> attrSubstRequestCreator) {
		ValueResolver vr = patternElementToSubstRequests.get(element);
		NodeMatcher attrSubstReq;
		if (vr != null) {
			if (vr instanceof NodeMatcher) {
				attrSubstReq = (NodeMatcher) vr;
			} else {
				//Cannot add attribute substitution request to node, which already contains another ValueResolver
				//Another ValueResolver has higher priority
				//TODO explain when it happens
				return;
			}
		} else {
			attrSubstReq = new NodeMatcher(element, this);
			patternElementToSubstRequests.put(element, attrSubstReq);
		}
		NodeAttributeSubstitutionRequest asr = attrSubstReq.getAttributeSubstititionRequest(attributeRole);
		if (asr == null) {
			asr = new NodeAttributeSubstitutionRequest(element, attributeRole);
			attrSubstReq.addAttributeSubstitutionRequest(asr);
		}
		attrSubstRequestCreator.accept(asr);
	}

	/**
	 * Removes all substitution requests of element
	 * @param element
	 */
	public ValueResolver removeSubstitutionRequest(CtElement element) {
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
		final int sourceStart;
		final CtElement sourceElement;
		final ValueResolver valueResolver;
		SubstReqOnPosition(CtElement sourceElement, ValueResolver substReq) {
			this.sourceElement = sourceElement;
			this.sourceStart = getSourceStart(sourceElement);
			this.valueResolver = substReq;
		}

		@Override
		public String toString() {
			return String.valueOf(sourceStart) + ":" + valueResolver;
		}
	}


	@Override
	public String toString() {
		Factory f = getFactory();
		Environment env = f.getEnvironment();
		final List<SubstReqOnPosition> allRequestWithSourcePos = new ArrayList<>();
		for (Map.Entry<CtElement, ValueResolver> e : patternElementToSubstRequests.entrySet()) {
			allRequestWithSourcePos.add(new SubstReqOnPosition(e.getKey(), e.getValue()));
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

	private static int getSourceStart(CtElement ele) {
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
			appendPathIn(sb, reqPos.sourceElement, ele);
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
			printer.write(" <= ").write(e.getValue().valueResolver.toString());
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

	@Override
	public TobeMatched matchAllWith(TobeMatched targets) {
		return ChainOfMatchersImpl.create(getModelMatchers(), false).matchAllWith(targets);
	}

	@Override
	public TobeMatched matchTargets(TobeMatched targets, Matchers nextMatchers) {
		return ChainOfMatchersImpl.create(getModelMatchers(), nextMatchers).matchAllWith(targets);
	}

	private List<Matcher> getModelMatchers() {
		List<Matcher> matchers = new ArrayList<>(this.patternModel.size());
		for (CtElement element : this.patternModel) {
			matchers.add(getTemplateValueResolver(element));
		}
		return matchers;
	}

	public ValueResolver getTemplateValueResolver(Object object) {
		ValueResolver vr = getExplicitValueResolver(object);
		if (vr != null) {
			return vr;
		}
		//there is no ValueResolver for this object. Create a implicit one
		if (object instanceof CtElement) {
			return new NodeMatcher((CtElement) object, this);
		}
		return new ConstantMatcher<>(object);
	}

	private ValueResolver getExplicitValueResolver(Object patternModelElement) {
		ValueResolver nsr = patternElementToSubstRequests.get(patternModelElement);
		if (nsr == null && parent != null) {
			nsr = parent.getExplicitValueResolver(patternModelElement);
		}
		return nsr;
	}
}
