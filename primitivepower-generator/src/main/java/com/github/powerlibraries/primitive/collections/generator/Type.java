package com.github.powerlibraries.primitive.collections.generator;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("PMD")
@Getter @AllArgsConstructor
public enum Type {
	GENERIC(false, false,	"Object",	"<E>",	"E",		"E",			"null") {
		@Override
		public String getArrayType() {
			return "Object";
		}
		
		@Override
		public String getBoxedArrayType() {
			return "Object";
		}
		
		@Override
		public String equal(String a, String b) {
			return "Objects.equals("+a+", "+b+")";
		}
		
		@Override
		public String unequal(String left, String right) {
			return "!"+this.equal(left, right);
		}
		
		@Override
		public String returnOnInvalidValue(String arg, String returnValue, BigDecimal tabs) {
			return "";
		}
		
		@Override
		public String hash(String var) {
			return "Objects.hashCode("+var+")";
		}
		
		@Override
		public String randomValue(String random) {
			return "(E)TimeUnit.values()["+random+".nextInt(100)]";
		}
	},
	INT(true, true,		"Int",		"",		"int",		"Integer",		"0"),
	LONG(true, true,		"Long",		"",		"long",		"Long",			"0L"),
	SHORT(false, true,	"Short",	"",		"short",	"Short",		"((short)0)") {
		@Override
		public String randomValue(String random) {
			return "((short)"+random+".nextInt(100))";
		}
	},
	BYTE(false, true,		"Byte",		"",		"byte",		"Byte",			"0") {
		@Override
		public String randomValue(String random) {
			return "((byte)"+random+".nextInt(100))";
		}
	},
	BOOLEAN(false, false,	"Boolean",	"",		"boolean",	"Boolean",		"false"),
	FLOAT(false, true,	"Float",	"",		"float",	"Float",		"0f"),
	DOUBLE(true, true,	"Double",	"",		"double",	"Double",		"0d"),
	CHAR(false, true,		"Char",		"",		"char",		"Character",	"'\\u0000'") {
		@Override
		public String randomValue(String random) {
			return "((char)"+random+".nextInt(100))";
		}
	};
	
	private boolean streamSupport;
	private boolean bufferSupport;
	private String label;
	private String generic;
	private String type;
	private String boxed;
	private String neutralElement;
	
	
	public String getArrayType() {
		return type;
	}
	
	public String getBoxedArrayType() {
		return getBoxed();
	}
	
	public String returnOnInvalidValue(String arg, String returnValue, BigDecimal tabs) {
		return "if(!("+arg+" instanceof "+boxed+")) {\n"+tabs(tabs)+"\treturn "+returnValue+";\n"+tabs(tabs)+"}";
	}
	
	public String equal(String left, String right) {
		return left+" == "+right;
	}
	
	public String unequal(String left, String right) {
		return left+" != "+right;
	}
	
	public boolean isPrimitive() {
		return !getBoxed().equals(getType());
	}
	
	public boolean isNumeric() {
		return isPrimitive() && this!=BOOLEAN;
	}
	
	public String getExtendedGeneric() {
		if(getGeneric().isEmpty()) {
			return "";
		}
		else {
			return "<? extends "+getBoxed()+">";
		}
	}
	
	public String hash(String var) {
		return this.boxed+".hashCode("+var+")";
	}
	
	public String tabs(BigDecimal number) {
		return IntStream
			.range(0, number.intValueExact())
			.mapToObj(i->"\t")
			.collect(Collectors.joining());
	}
	
	public String randomValue(String random) {
		return random+".next"+this.getLabel()+"()";
	}
}
