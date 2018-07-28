package com.dslplatform.json.runtime;

import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.Nullable;

import java.nio.charset.Charset;

class AttributeObjectNonDefaultEncoder<T, R> implements JsonWriter.WriteObject<T> {

	private static final Charset utf8 = Charset.forName("UTF-8");

	private final Settings.Function<T, R> read;
	private final byte[] quotedName;
	private final JsonWriter.WriteObject<R> encoder;
	private final R defaultValue;

	AttributeObjectNonDefaultEncoder(
			final Settings.Function<T, R> read,
			final String name,
			final JsonWriter.WriteObject<R> encoder,
			@Nullable final R defaultValue) {
		if (read == null) throw new IllegalArgumentException("read can't be null");
		if (name == null || name.isEmpty()) throw new IllegalArgumentException("name can't be null");
		if (encoder == null) throw new IllegalArgumentException("encoder can't be null");
		this.read = read;
		quotedName = ("\"" + name + "\":").getBytes(utf8);
		this.defaultValue = defaultValue;
		this.encoder = encoder;
	}

	@Override
	public void write(final JsonWriter writer, @Nullable final T value) {
		final R attr = read.apply(value);
		if (attr != null && attr != defaultValue) {
			writer.writeAscii(quotedName);
			encoder.write(writer, attr);
		}
	}
}
