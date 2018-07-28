package com.dslplatform.jsonb;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Example {

	public static class Model {
		//when @CompiledJson is not used, @JsonbCreator can be used to tag which class will get compile-time databinding
		@JsonbCreator
		public Model() {}
		@JsonbProperty(nillable = false) //indicate that field can't be null
		public String string;
		public List<Integer> integers;
		@JsonbProperty(value = "guids") //use alternative name in JSON
		public UUID[] uuids;
		public Set<BigDecimal> decimals;
		public Vector<Long> longs;
		public int number;
		public List<Nested> nested;
		public Interface iface;//interfaces without deserializedAs will also include $type attribute in JSON by default
		public ParentClass inheritance;
		public List<State> states;
		@JsonbTransient
		public GregorianCalendar ignored;
		public ArrayList<Integer> intList; //most collections are supported through runtime converters
		public Map<String, Object> map; //even unknown stuff can be used. If it fails it will throw SerializationException
		public ImmutablePerson person; //immutable objects are supported via builder pattern

		//explicitly referenced classes don't require @CompiledJson annotation
		public static class Nested {
			public long x;
			public double y;
			public float z;
		}

		public interface Interface {
			void x(int v);
			int x();
		}

		public static class WithCustomCtor implements Interface {
			private int x;
			private int y;

			public WithCustomCtor(int x) {
				this.x = x;
				this.y = x;
			}

			@JsonbCreator
			public WithCustomCtor(int x, int y) {
				this.x = x;
				this.y = y;
			}

			public void x(int v) { x = v; }
			public int x() { return x; }
			public void setY(int v) { y = v; }
			public int getY() { return y; }
		}

		public static class BaseClass {
			public int a;
		}

		public static class ParentClass extends BaseClass {
			public long b;
		}

		public enum State {
			LOW(0),
			MID(1),
			HI(2);

			private final int value;

			State(int value) {
				this.value = value;
			}
		}
	}

	public static void main(String[] args) throws IOException {

		Jsonb jsonb = JsonbBuilder.create();

		Model instance = new Model();
		instance.string = "Hello World!";
		instance.number = 42;
		instance.integers = Arrays.asList(1, 2, 3);
		instance.decimals = new HashSet<>(Arrays.asList(BigDecimal.ONE, BigDecimal.ZERO));
		instance.uuids = new UUID[]{new UUID(1L, 2L), new UUID(3L, 4L)};
		instance.longs = new Vector<>(Arrays.asList(1L, 2L));
		instance.nested = Arrays.asList(new Model.Nested(), null);
		instance.inheritance = new Model.ParentClass();
		instance.inheritance.a = 5;
		instance.inheritance.b = 6;
		instance.iface = new Model.WithCustomCtor(5, 6);
		instance.person = new ImmutablePerson("first name", "last name", 35);
		instance.states = Arrays.asList(Model.State.HI, Model.State.LOW);
		instance.intList = new ArrayList<>(Arrays.asList(123, 456));
		instance.map = new HashMap<>();
		instance.map.put("abc", 678);
		instance.map.put("array", new int[] { 2, 4, 8});

		//TODO while string API is supported, it should be avoided in favor of stream API
		String result = jsonb.toJson(instance);

		//TODO while string API is supported, it should be avoided in favor of stream API
		Model deser = jsonb.fromJson(result, Model.class);

		System.out.println(deser.string);
	}
}
