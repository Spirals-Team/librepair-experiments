package com.dslplatform.json.processor;

import com.dslplatform.json.CompiledJson;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.dslplatform.json.processor.CompiledJsonAnnotationProcessor.findConverterName;
import static com.dslplatform.json.processor.Context.nonGenericObject;
import static com.dslplatform.json.processor.Context.sortedAttributes;
import static com.dslplatform.json.processor.Context.typeOrClass;

class ConverterTemplate {

	private final Writer code;
	private final Context context;

	ConverterTemplate(Context context) {
		this.code = context.code;
		this.context = context;
	}

	private void asFormatConverter(final StructInfo si, final String name, final String className, final boolean binding) throws IOException {
		code.append("\tpublic final static class ").append(name);
		code.append(" implements com.dslplatform.json.runtime.FormatConverter<");
		if (binding) {
			code.append(className).append(">, com.dslplatform.json.JsonReader.BindObject<");
		}
		code.append(className).append("> {\n");
		code.append("\t\tprivate final boolean alwaysSerialize;\n");
		code.append("\t\tprivate final com.dslplatform.json.DslJson json;\n");
		for (AttributeInfo attr : si.attributes.values()) {
			String typeName = attr.type.toString();
			boolean hasConverter = context.inlinedConverters.containsKey(typeName);
			if (attr.converter == null && !hasConverter && !attr.isEnum(context.structs)) {
				String content = attr.collectionContent(context.knownTypes);
				if (content != null) {
					code.append("\t\tprivate final com.dslplatform.json.JsonReader.ReadObject<").append(content).append("> reader_").append(attr.name).append(";\n");
					code.append("\t\tprivate final com.dslplatform.json.JsonWriter.WriteObject<").append(content).append("> writer_").append(attr.name).append(";\n");
				} else {
					String type = typeOrClass(nonGenericObject(typeName), typeName);
					code.append("\t\tprivate com.dslplatform.json.JsonReader.ReadObject<").append(typeName).append("> reader_").append(attr.name).append(";\n");
					code.append("\t\tprivate com.dslplatform.json.JsonReader.ReadObject<").append(typeName).append("> reader_").append(attr.name).append("() {\n");
					code.append("\t\t\tif (reader_").append(attr.name).append(" == null) { reader_").append(attr.name).append(" = json.tryFindReader(");
					code.append(type).append("); if (reader_").append(attr.name);
					code.append(" == null) throw new com.dslplatform.json.SerializationException(\"Unable to find reader for ").append(typeName).append("\"); }\n");
					code.append("\t\t\treturn reader_").append(attr.name).append(";\n");
					code.append("\t\t}\n");
					code.append("\t\tprivate com.dslplatform.json.JsonWriter.WriteObject<").append(typeName).append("> writer_").append(attr.name).append(";\n");
					code.append("\t\tprivate com.dslplatform.json.JsonWriter.WriteObject<").append(typeName).append("> writer_").append(attr.name).append("() {\n");
					code.append("\t\t\tif (writer_").append(attr.name).append(" == null) { writer_").append(attr.name).append(" = json.tryFindWriter(");
					code.append(type).append("); if (writer_").append(attr.name);
					code.append(" == null) throw new com.dslplatform.json.SerializationException(\"Unable to find writer for ").append(typeName).append("\"); }\n");
					code.append("\t\t\treturn writer_").append(attr.name).append(";\n");
					code.append("\t\t}\n");
				}
			}
		}
		code.append("\t\t").append(name).append("(com.dslplatform.json.DslJson json) {\n");
		code.append("\t\t\tthis.alwaysSerialize = !json.omitDefaults;\n");
		code.append("\t\t\tthis.json = json;\n");
		for (AttributeInfo attr : si.attributes.values()) {
			String typeName = attr.type.toString();
			boolean hasConverter = context.inlinedConverters.containsKey(typeName);
			String content = attr.collectionContent(context.knownTypes);
			if (attr.converter == null && !hasConverter && !attr.isEnum(context.structs) && content != null) {
				String type = typeOrClass(nonGenericObject(content), content);
				code.append("\t\t\tthis.reader_").append(attr.name).append(" = json.tryFindReader(").append(type).append(");\n");
				code.append("\t\t\tthis.writer_").append(attr.name).append(" = json.tryFindWriter(").append(type).append(");\n");
			}
		}
		code.append("\t\t}\n");
		if (binding) {
			code.append("\t\tpublic ").append(className).append(" read(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {\n");
			code.append("\t\t\tif (reader.wasNull()) return null;\n");
			code.append("\t\t\treturn bind(reader, ");
			if (si.factory != null) {
				code.append(className).append(".").append(si.factory.getSimpleName()).append("());\n");
			} else {
				code.append("new ").append(className).append("());\n");
			}
			code.append("\t\t}\n");
		}
	}

	void emptyObject(final StructInfo si, final String className) throws IOException {
		asFormatConverter(si, "ObjectFormatConverter", className, true);
		List<AttributeInfo> sortedAttributes = sortedAttributes(si);
		writeObject(className, sortedAttributes);
		code.append("\t\tpublic ").append(className).append(" bind(final com.dslplatform.json.JsonReader reader, final ");
		code.append(className).append(" instance) throws java.io.IOException {\n");
		code.append("\t\t\tif (reader.last() != '{') throw new java.io.IOException(\"Expecting '{' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		code.append("\t\t\treader.getNextToken();\n");
		code.append("\t\t\tbindContent(reader, instance);\n");
		code.append("\t\t\treturn instance;\n");
		code.append("\t\t}\n");
		code.append("\t\tpublic ").append(className).append(" readContent(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {\n");
		code.append("\t\t\t").append(className).append(" instance = ");
		if (si.factory != null) {
			code.append(className).append(".").append(si.factory.getSimpleName()).append("();\n ");
		} else {
			code.append("new ").append(className).append("();\n ");
		}
		code.append("\t\t\tbindContent(reader, instance);\n");
		code.append("\t\t\treturn instance;\n");
		code.append("\t\t}\n");
		code.append("\t\tpublic void bindContent(final com.dslplatform.json.JsonReader reader, final ");
		code.append(className).append(" instance) throws java.io.IOException {\n");
		code.append("\t\t\tif (reader.last() == '}')");
		checkMandatory(sortedAttributes, 0);
		int i = 0;
		for (AttributeInfo attr : sortedAttributes) {
			String mn = si.minifiedNames.get(attr.id);
			if (i > 0) {
				code.append("\t\t\tif (reader.getNextToken() == '}') ");
				checkMandatory(sortedAttributes, i);
				code.append("\t\t\tif (reader.last() != ',') throw new java.io.IOException(\"Expecting ',' \"");
				code.append(" + reader.positionDescription() + \". Found: \" + (char)reader.last()); else reader.getNextToken();\n");
			}
			code.append("\t\t\tif (reader.fillNameWeakHash() != ").append(Integer.toString(calcWeakHash(mn != null ? mn : attr.id)));
			code.append(" || !reader.wasLastName(name_").append(attr.name).append(")) { bindSlow(reader, instance, ");
			code.append(Integer.toString(i)).append("); return; }\n");
			code.append("\t\t\treader.getNextToken();\n");
			setPropertyValue(attr, "\t");
			i += 1;
		}
		if (si.onUnknown == CompiledJson.Behavior.FAIL) {
			code.append("\t\t\tif (reader.getNextToken() != '}') throw new java.io.IOException(\"Expecting '}' \" + reader.positionDescription() + \" since unknown properties are not allowed on ");
			code.append(className).append(". Found \" + (char) reader.last());\n");
		} else {
			code.append("\t\t\tif (reader.getNextToken() != '}') {\n");
			code.append("\t\t\t\tif (reader.last() == ',') {\n");
			code.append("\t\t\t\t\treader.getNextToken();\n");
			code.append("\t\t\t\t\treader.fillNameWeakHash();\n");
			code.append("\t\t\t\t\tbindSlow(reader, instance, ").append(Integer.toString(sortedAttributes.size())).append(");\n");
			code.append("\t\t\t\t}\n");
			code.append("\t\t\t\tif (reader.last() != '}') throw new java.io.IOException(\"Expecting '}' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
			code.append("\t\t\t}\n");
		}
		code.append("\t\t}\n");
		code.append("\t\tprivate void bindSlow(final com.dslplatform.json.JsonReader reader, final ");
		code.append(className).append(" instance, int index) throws java.io.IOException {\n");
		i = 0;
		for (AttributeInfo attr : sortedAttributes) {
			if (attr.mandatory) {
				code.append("\t\t\tboolean __detected_").append(attr.name).append("__ = index > ").append(Integer.toString(i)).append(";\n");
				i += 1;
			}
		}
		code.append("\t\t\tswitch(reader.getLastHash()) {\n");
		handleSwitch(si, "\t\t\t", false);
		code.append("\t\t\t}\n");
		code.append("\t\t\twhile (reader.last() == ','){\n");
		code.append("\t\t\t\treader.getNextToken();\n");
		code.append("\t\t\t\tswitch(reader.fillName()) {\n");
		handleSwitch(si, "\t\t\t\t", false);
		code.append("\t\t\t\t}\n");
		code.append("\t\t\t}\n");
		code.append("\t\t\tif (reader.last() != '}') throw new java.io.IOException(\"Expecting '}' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		for (AttributeInfo attr : sortedAttributes) {
			if (attr.mandatory) {
				code.append("\t\t\tif (!__detected_").append(attr.name).append("__) throw new java.io.IOException(\"Property '").append(attr.name);
				code.append("' is mandatory but was not found in JSON \" + reader.positionDescription());\n");
			}
		}
		code.append("\t\t}\n");
		code.append("\t}\n");
	}

	void fromObject(final StructInfo si, final String className) throws IOException {
		asFormatConverter(si, "ObjectFormatConverter", className, false);
		List<AttributeInfo> sortedAttributes = sortedAttributes(si);
		writeObject(className, sortedAttributes);
		code.append("\t\tpublic ").append(className).append(" read(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {\n");
		code.append("\t\t\tif (reader.wasNull()) return null;\n");
		code.append("\t\t\telse if (reader.last() != '{') throw new java.io.IOException(\"Expecting '{' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		code.append("\t\t\treader.getNextToken();\n");
		code.append("\t\t\treturn readContent(reader);\n");
		code.append("\t\t}\n");
		code.append("\t\tpublic ").append(className).append(" readContent(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {\n");
		for (AttributeInfo attr : sortedAttributes) {
			String typeName = attr.type.toString();
			code.append("\t\t\t").append(typeName).append(" _").append(attr.name).append("_ = ");
			String defaultValue = context.getDefault(typeName);
			code.append(defaultValue).append(";\n");
			if (attr.mandatory) {
				code.append("\t\t\tboolean __detected_").append(attr.name).append("__ = false;\n");
			}
		}
		code.append("\t\t\tif (reader.last() == '}') {\n");
		checkMandatory(sortedAttributes);
		returnInstance("\t\t\t\t", si.constructor, si.factory, className);
		code.append("\t\t\t}\n");
		code.append("\t\t\tswitch(reader.fillName()) {\n");
		handleSwitch(si, "\t\t\t", true);
		code.append("\t\t\t}\n");
		code.append("\t\t\twhile (reader.last() == ','){\n");
		code.append("\t\t\t\treader.getNextToken();\n");
		code.append("\t\t\t\tswitch(reader.fillName()) {\n");
		handleSwitch(si, "\t\t\t\t", true);
		code.append("\t\t\t\t}\n");
		code.append("\t\t\t}\n");
		code.append("\t\t\tif (reader.last() != '}') throw new java.io.IOException(\"Expecting '}' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		checkMandatory(sortedAttributes);
		returnInstance("\t\t\t", si.constructor, si.factory, className);
		code.append("\t\t}\n");
		code.append("\t}\n");
	}

	private void writeObject(final String className, List<AttributeInfo> sortedAttributes) throws IOException {
		boolean isFirst = true;
		for (AttributeInfo attr : sortedAttributes) {
			String prefix = isFirst ? "" : ",";
			isFirst = false;
			code.append("\t\tprivate static final byte[] quoted_").append(attr.name).append(" = \"").append(prefix);
			code.append("\\\"").append(attr.id).append("\\\":\".getBytes(utf8);\n");
			code.append("\t\tprivate static final byte[] name_").append(attr.name).append(" = \"").append(attr.id).append("\".getBytes(utf8);\n");
		}
		code.append("\t\tpublic final void write(final com.dslplatform.json.JsonWriter writer, final ");
		code.append(className).append(" instance) {\n");
		code.append("\t\t\tif (instance == null) writer.writeNull();\n");
		code.append("\t\t\telse {\n");
		code.append("\t\t\t\twriter.writeByte((byte)'{');\n");
		code.append("\t\t\t\tif (alwaysSerialize) { writeContentFull(writer, instance); writer.writeByte((byte)'}'); }\n");
		code.append("\t\t\t\telse if (writeContentMinimal(writer, instance)) writer.getByteBuffer()[writer.size() - 1] = '}';\n");
		code.append("\t\t\t\telse writer.writeByte((byte)'}');\n");
		code.append("\t\t\t}\n");
		code.append("\t\t}\n");
		code.append("\t\tpublic void writeContentFull(final com.dslplatform.json.JsonWriter writer, final ");
		code.append(className).append(" instance) {\n");
		for (AttributeInfo attr : sortedAttributes) {
			code.append("\t\t\twriter.writeAscii(quoted_").append(attr.name).append(");\n");
			writeProperty(attr, false);
		}
		code.append("\t\t}\n");
		code.append("\t\tpublic boolean writeContentMinimal(final com.dslplatform.json.JsonWriter writer, final ");
		code.append(className).append(" instance) {\n");
		code.append("\t\t\tboolean hasWritten = false;\n");
		for (AttributeInfo attr : sortedAttributes) {
			String typeName = attr.type.toString();
			String defaultValue = context.getDefault(typeName);
			code.append("\t\t\tif (");
			String readValue = "instance." + attr.readProperty;
			if ("null".equals(defaultValue) || typeName.indexOf('<') == -1) {
				code.append(readValue).append(" != ").append(defaultValue);
			} else {
				code.append(readValue).append(" != null && !").append(defaultValue).append(".equals(").append(readValue).append(")");
			}
			code.append(") {\n");
			code.append("\t\t\t\twriter.writeByte((byte)'\"'); writer.writeAscii(name_").append(attr.name).append("); writer.writeByte((byte)'\"'); writer.writeByte((byte)':');\n");
			writeProperty(attr, true);
			code.append("\t\t\t\twriter.writeByte((byte)','); hasWritten = true;\n");
			code.append("\t\t\t}");
			if (attr.notNull && "null".equals(defaultValue)) {
				code.append(" else throw new com.dslplatform.json.SerializationException(\"Property '");
				code.append(attr.name).append("' is not allowed to be null\");\n");
			} else code.append("\n");
		}
		code.append("\t\t\treturn hasWritten;\n");
		code.append("\t\t}\n");
	}

	private void checkMandatory(final List<AttributeInfo> attributes, final int start) throws IOException {
		for (int i = start; i < attributes.size(); i++) {
			AttributeInfo attr = attributes.get(i);
			if (attr.mandatory) {
				code.append(" throw new java.io.IOException(\"Property '").append(attr.name);
				code.append("' is mandatory but was not found in JSON \" + reader.positionDescription());\n");
				return;
			}
		}
		code.append(" return;\n");
	}

	private void checkMandatory(final List<AttributeInfo> attributes) throws IOException {
		for (AttributeInfo attr : attributes) {
			if (attr.mandatory) {
				code.append("\t\t\tif (!__detected_").append(attr.name).append("__) throw new java.io.IOException(\"Property '").append(attr.name);
				code.append("' is mandatory but was not found in JSON \" + reader.positionDescription());\n");
			}
		}
	}

	void emptyArray(final StructInfo si, final String className) throws IOException {
		asFormatConverter(si,"ArrayFormatConverter", className, true);
		List<AttributeInfo> sortedAttributes = sortedAttributes(si);
		writeArray(className, sortedAttributes);
		code.append("\t\tpublic ").append(className).append(" readContent(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {\n");
		code.append("\t\t\t").append(className).append(" instance = ");
		if (si.factory != null) {
			code.append(className).append(".").append(si.factory.getSimpleName()).append("();\n ");
		} else {
			code.append("new ").append(className).append("();\n ");
		}
		code.append("\t\t\tbind(reader, instance);\n");
		code.append("\t\t\treturn instance;\n");
		code.append("\t\t}\n");
		code.append("\t\tpublic ").append(className).append(" bind(final com.dslplatform.json.JsonReader reader, final ");
		code.append(className).append(" instance) throws java.io.IOException {\n");
		code.append("\t\t\tif (reader.last() != '[') throw new java.io.IOException(\"Expecting '[' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		int i = sortedAttributes.size();
		for (AttributeInfo attr : sortedAttributes) {
			code.append("\t\t\treader.getNextToken();\n");
			setPropertyValue(attr, "\t");
			i--;
			if (i > 0) code.append("\t\t\tif (reader.getNextToken() != ',') throw new java.io.IOException(\"Expecting ',' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		}
		code.append("\t\t\tif (reader.getNextToken() != ']') throw new java.io.IOException(\"Expecting ']' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		code.append("\t\t\treturn instance;\n");
		code.append("\t\t}\n");
		code.append("\t}\n");
	}

	void fromArray(final StructInfo si, final String className) throws IOException {
		asFormatConverter(si,"ArrayFormatConverter", className, false);
		List<AttributeInfo> sortedAttributes = sortedAttributes(si);
		writeArray(className, sortedAttributes);
		code.append("\t\tpublic ").append(className).append(" read(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {\n");
		code.append("\t\t\tif (reader.wasNull()) return null;\n");
		code.append("\t\t\telse if (reader.last() != '[') throw new java.io.IOException(\"Expecting '[' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		code.append("\t\t\treturn readContent(reader);\n");
		code.append("\t\t}\n");
		code.append("\t\tpublic ").append(className).append(" readContent(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {\n");
		int i = sortedAttributes.size();
		for (AttributeInfo attr : sortedAttributes) {
			code.append("\t\t\tfinal ").append(attr.type.toString()).append(" _").append(attr.name).append("_;\n");
			code.append("\t\t\treader.getNextToken();\n");
			readPropertyValue(attr, "\t");
			i--;
			if (i > 0) code.append("\t\t\tif (reader.getNextToken() != ',') throw new java.io.IOException(\"Expecting ',' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		}
		code.append("\t\t\tif (reader.getNextToken() != ']') throw new java.io.IOException(\"Expecting ']' \" + reader.positionDescription() + \". Found \" + (char) reader.last());\n");
		returnInstance("\t\t\t", si.constructor, si.factory, className);
		code.append("\t\t}\n");
		code.append("\t}\n");
	}

	private void returnInstance(final String alignment, final ExecutableElement constructor, final ExecutableElement factory, final String className) throws IOException {
		code.append(alignment).append("return ");
		final List<? extends VariableElement> params;
		if (factory != null) {
			code.append(className).append(".").append(factory.getSimpleName()).append("(");
			params = factory.getParameters();
		} else {
			code.append("new ").append(className).append("(");
			params = constructor.getParameters();
		}
		int i = params.size();
		for (VariableElement p : params) {
			code.append("_").append(p.getSimpleName()).append("_");
			i--;
			if (i > 0) code.append(", ");
		}
		code.append(");\n");
	}

	private void writeArray(final String className, List<AttributeInfo> sortedAttributes) throws IOException {
		code.append("\t\tpublic final void write(final com.dslplatform.json.JsonWriter writer, final ");
		code.append(className).append(" instance) {\n");
		code.append("\t\t\tif (instance == null) writer.writeNull();\n");
		code.append("\t\t\telse {\n");
		code.append("\t\t\t\twriter.writeByte((byte)'[');\n");
		code.append("\t\t\t\twriteContentFull(writer, instance);\n");
		code.append("\t\t\t\twriter.writeByte((byte)']');\n");
		code.append("\t\t\t}\n");
		code.append("\t\t}\n");
		code.append("\t\tpublic boolean writeContentMinimal(final com.dslplatform.json.JsonWriter writer, final ");
		code.append(className).append(" instance) {\n");
		if (sortedAttributes.isEmpty()) {
			code.append("\t\t\treturn false;\n");
			code.append("\t\t}\n");
		} else {
			code.append("\t\t\twriteContentFull(writer, instance);\n");
			code.append("\t\t\treturn true;\n");
			code.append("\t\t}\n");
		}
		code.append("\t\tpublic void writeContentFull(final com.dslplatform.json.JsonWriter writer, final ");
		code.append(className).append(" instance) {\n");
		int i = sortedAttributes.size();
		for (AttributeInfo attr : sortedAttributes) {
			writeProperty(attr, false);
			i--;
			if (i > 0) code.append("\t\t\twriter.writeByte((byte)',');\n");
		}
		code.append("\t\t}\n");
	}

	private void writeProperty(AttributeInfo attr, boolean checkedDefault) throws IOException {
		String typeName = attr.type.toString();
		String readValue = "instance." + attr.readProperty;
		OptimizedConverter converter = context.inlinedConverters.get(typeName);
		boolean canBeNull = !checkedDefault && (converter == null || converter.defaultValue == null);
		if (attr.notNull && canBeNull) {
			code.append("\t\t\tif (").append(readValue);
			code.append(" == null) throw new com.dslplatform.json.SerializationException(\"Property '").append(attr.name).append("' is not allowed to be null\");\n");
			code.append("\t\t\t");
		} else if (canBeNull) {
			code.append("\t\t\tif (").append(readValue).append(" == null) writer.writeNull();\n");
			code.append("\t\t\telse ");
		} else {
			code.append("\t\t\t");
		}
		if (checkedDefault) code.append("\t");
		if (attr.converter != null) {
			code.append(attr.converter.fullName).append(".").append(attr.converter.writer).append(".write(writer, ").append(readValue).append(")");
		} else {
			if (converter != null) {
				code.append(converter.nonNullableEncoder("writer", readValue));
			} else if (attr.isEnum(context.structs)) {
				EnumTemplate.writeName(context, attr, readValue);
			} else if (attr.collectionContent(context.knownTypes) != null) {
				code.append("writer.serialize(").append(readValue).append(", writer_").append(attr.name).append(")");
			} else {
				code.append("writer_").append(attr.name).append("().write(writer, ").append(readValue).append(")");
			}
		}
		code.append(";\n");
	}

	private void handleSwitch(StructInfo si, String alignment, boolean localNames) throws IOException {
		for (AttributeInfo attr : si.attributes.values()) {
			String mn = si.minifiedNames.get(attr.id);
			code.append(alignment).append("\tcase ").append(Integer.toString(StructInfo.calcHash(mn != null ? mn : attr.id))).append(":\n");
			for(String an : attr.alternativeNames) {
				code.append(alignment).append("\tcase ").append(Integer.toString(StructInfo.calcHash(an))).append(":\n");
			}
			if (attr.fullMatch) {
				code.append(alignment).append("\t\tif (!reader.wasLastName(name_").append(attr.name).append(")) {\n");
				if (si.onUnknown == CompiledJson.Behavior.FAIL) {
					code.append(alignment).append("\t\tthrow new java.io.IOException(\"Unknown property detected: '\" + reader.getLastName()");
					code.append(" + \"' \" + reader.positionDescription(reader.getLastName().length() + 3));\n");
				} else {
					code.append(alignment).append("\t\treader.getNextToken(); reader.skip(); break;\n");
				}
				code.append(alignment).append("\t\t}\n");
			}
			if (attr.mandatory) {
				code.append(alignment).append("\t\t__detected_").append(attr.name).append("__ = true;\n");
			}
			code.append(alignment).append("\t\treader.getNextToken();\n");
			if (localNames) readPropertyValue(attr, alignment);
			else setPropertyValue(attr, alignment);
			code.append(alignment).append("\t\treader.getNextToken();\n");
			code.append(alignment).append("\t\tbreak;\n");
		}
		code.append(alignment).append("\tdefault:\n");
		if (si.onUnknown == CompiledJson.Behavior.FAIL) {
			code.append(alignment).append("\t\tString lastName = reader.getLastName();\n");
			code.append(alignment).append("\t\tthrow new java.io.IOException(\"Unknown property detected: '\" + lastName");
			code.append(" + \"' \" + reader.positionDescription(lastName.length() + 3));\n");
		} else {
			code.append(alignment).append("\t\treader.getNextToken();\n");
			code.append(alignment).append("\t\treader.skip();\n");
		}
	}

	private void setPropertyValue(AttributeInfo attr, String alignment) throws IOException {
		if (attr.notNull) {
			code.append(alignment).append("\t\tif (reader.wasNull()) throw new java.io.IOException(\"Property '").append(attr.name).append("' is not allowed to be null.");
			code.append(" Null value found \" + reader.positionDescription());\n");
		}
		String typeName = attr.type.toString();
		OptimizedConverter converter = context.inlinedConverters.get(typeName);
		if (attr.converter == null && converter != null && converter.defaultValue == null && !attr.notNull && converter.hasNonNullableMethod()) {
			code.append(alignment).append("\t\tif (reader.wasNull()) instance.");
			if (attr.field != null) code.append(attr.field.getSimpleName()).append(" = null;\n");
			else code.append(attr.writeMethod.getSimpleName()).append("(null);\n");
			code.append(alignment).append("\t\telse instance.");
			if (attr.field != null) code.append(attr.field.getSimpleName()).append(" = ");
			else code.append(attr.writeMethod.getSimpleName()).append("(");
			code.append(converter.nonNullableDecoder()).append("(reader)");
		} else {
			code.append(alignment).append("\t\tinstance.");
			if (attr.field != null) code.append(attr.field.getSimpleName()).append(" = ");
			else code.append(attr.writeMethod.getSimpleName()).append("(");
			if (attr.converter != null) {
				code.append(attr.converter.fullName).append(".").append(attr.converter.reader).append(".read(reader)");
			} else if (converter != null) {
				code.append(converter.nonNullableDecoder()).append("(reader)");
			} else if (attr.isEnum(context.structs)) {
				if (!attr.notNull) code.append("reader.wasNull() ? null : ");
				StructInfo target = context.structs.get(attr.typeName);
				code.append(findConverterName(target)).append(".EnumConverter.readStatic(reader)");
			} else if (attr.collectionContent(context.knownTypes) != null) {
				if (attr.isArray) {
					String content = attr.typeName.substring(0, attr.typeName.length() - 2);
					int ind = content.indexOf('<');
					if (ind != -1) content = content.substring(0, ind);
					code.append("(").append(content).append("[])reader.readArray(reader_").append(attr.name);
					code.append(", new ").append(content).append("[0])");
				} else {
					code.append("reader.readCollection(reader_").append(attr.name).append(")");
				}
			} else {
				code.append("reader_").append(attr.name).append("().read(reader)");
			}
		}
		if (attr.field == null) code.append(")");
		code.append(";\n");
	}

	private void readPropertyValue(AttributeInfo attr, String alignment) throws IOException {
		if (attr.notNull) {
			code.append(alignment).append("\t\tif (reader.wasNull()) throw new java.io.IOException(\"Property '").append(attr.name).append("' is not allowed to be null.");
			code.append(" Null value found \" + reader.positionDescription());\n");
		}
		String typeName = attr.type.toString();
		OptimizedConverter converter = context.inlinedConverters.get(typeName);
		if (attr.converter == null && converter != null && converter.defaultValue == null && !attr.notNull && converter.hasNonNullableMethod()) {
			code.append(alignment).append("\t\t_").append(attr.name).append("_ = reader.wasNull() ? null : ");
			code.append(converter.nonNullableDecoder());
			code.append("(reader);\n");
		} else {
			code.append(alignment).append("\t\t_").append(attr.name).append("_ = ");
			if (attr.converter != null) {
				code.append(attr.converter.fullName).append(".").append(attr.converter.reader).append(".read(reader);\n");
			} else if (converter != null) {
				code.append(converter.nonNullableDecoder()).append("(reader);\n");
			} else if (attr.isEnum(context.structs)) {
				if (!attr.notNull) code.append("reader.wasNull() ? null : ");
				StructInfo target = context.structs.get(attr.typeName);
				code.append(findConverterName(target)).append(".EnumConverter.readStatic(reader);\n");
			} else if (attr.collectionContent(context.knownTypes) != null) {
				if (attr.isArray) {
					String content = attr.typeName.substring(0, attr.typeName.length() - 2);
					int ind = content.indexOf('<');
					if (ind != -1) content = content.substring(0, ind);
					code.append("(").append(content).append("[])reader.readArray(reader_").append(attr.name);
					code.append(", new ").append(content).append("[0]);\n");
				} else {
					code.append("reader.readCollection(reader_").append(attr.name).append(");\n");
				}
			} else {
				code.append("reader_").append(attr.name).append("().read(reader);\n");
			}
		}
	}

	private static int calcWeakHash(String name) {
		int hash = 0;
		for (int i = 0; i < name.length(); i++) {
			hash += (byte) name.charAt(i);
		}
		return hash;
	}
}
