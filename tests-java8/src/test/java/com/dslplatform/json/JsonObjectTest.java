package com.dslplatform.json;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonObjectTest {

	@CompiledJson
	public static class Model {
		public JsonObjectReference jsonObject;
	}

	public static class JsonObjectReference implements JsonObject {
		private final String x;
		public JsonObjectReference(String x) {
			this.x = x;
		}
		public void serialize(JsonWriter writer, boolean minimal) {
			writer.writeAscii("\"x\":");
			writer.writeString(x);
		}

		public static final JsonReader.ReadJsonObject<JsonObjectReference> JSON_READER = reader -> {
			reader.getNextToken();
			reader.fillName();
			String x1 = reader.readString();
			reader.getNextToken();
			return new JsonObjectReference(x1);
		};

		@Override
		public boolean equals(Object obj) {
			return ((JsonObjectReference)obj).x.equals(this.x);
		}
	}

	private final DslJson<Object> dslJson = new DslJson<>();

	@Test
	public void simpleTest() throws IOException {
		Model m = new Model();
		m.jsonObject = new JsonObjectReference("test");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		dslJson.serialize(m, os);
		Assert.assertEquals("{\"jsonObject\":{\"x\":\"test\"}}", os.toString());
		Model res = dslJson.deserialize(Model.class, os.toByteArray(), os.size());
		Assert.assertEquals(m.jsonObject, res.jsonObject);
	}
}
