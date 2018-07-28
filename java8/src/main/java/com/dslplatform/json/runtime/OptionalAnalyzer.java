package com.dslplatform.json.runtime;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonReader;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.Nullable;

import java.lang.reflect.*;
import java.util.Optional;

public abstract class OptionalAnalyzer {

	public static final DslJson.ConverterFactory<OptionalDecoder> READER = new DslJson.ConverterFactory<OptionalDecoder>() {
		@Nullable
		@Override
		public OptionalDecoder tryCreate(Type manifest, DslJson dslJson) {
			if (manifest instanceof ParameterizedType) {
				final ParameterizedType pt = (ParameterizedType) manifest;
				if (pt.getActualTypeArguments().length == 1) {
					return analyzeDecoding(manifest, pt.getActualTypeArguments()[0], (Class<?>) pt.getRawType(), dslJson);
				}
			}
			if (manifest == Optional.class) {
				return analyzeDecoding(manifest, Object.class, Optional.class, dslJson);
			}
			return null;
		}
	};

	public static final DslJson.ConverterFactory<OptionalEncoder> WRITER = new DslJson.ConverterFactory<OptionalEncoder>() {
		@Nullable
		@Override
		public OptionalEncoder tryCreate(Type manifest, DslJson dslJson) {
			if (manifest instanceof ParameterizedType) {
				final ParameterizedType pt = (ParameterizedType) manifest;
				if (pt.getActualTypeArguments().length == 1) {
					return analyzeEncoding(manifest, pt.getActualTypeArguments()[0], (Class<?>) pt.getRawType(), dslJson);
				}
			}
			if (manifest == Optional.class) {
				return analyzeEncoding(manifest, Object.class, Optional.class, dslJson);
			}
			return null;
		}
	};

	@Nullable
	private static OptionalDecoder analyzeDecoding(final Type manifest, final Type content, final Class<?> raw, final DslJson json) {
		if (raw != Optional.class) {
			return null;
		} else if (content == Optional.class) {
			final OptionalDecoder nested = analyzeDecoding(content, Object.class, Optional.class, json);
			final OptionalDecoder outer = new OptionalDecoder<>(nested);
			json.registerReader(manifest, outer);
			return outer;
		}
		final JsonReader.ReadObject<?> reader = json.tryFindReader(content);
		if (reader == null) {
			return null;
		}
		final OptionalDecoder decoder = new OptionalDecoder<>(reader);
		json.registerReader(manifest, decoder);
		return decoder;
	}

	@Nullable
	private static OptionalEncoder analyzeEncoding(final Type manifest, final Type content, final Class<?> raw, final DslJson json) {
		if (raw != Optional.class) {
			return null;
		} else if (content == Optional.class) {
			final OptionalEncoder nested = analyzeEncoding(content, Object.class, Optional.class, json);
			json.registerWriter(manifest, nested);
			return nested;
		}
		final JsonWriter.WriteObject<?> writer = Object.class == content ? null : json.tryFindWriter(content);
		if (Object.class != content && writer == null) {
			return null;
		}
		final OptionalEncoder encoder = new OptionalEncoder<>(json, Object.class == content ? null : writer);
		json.registerWriter(manifest, encoder);
		return encoder;
	}
}
