/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.formats.avro;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.formats.avro.generated.Address;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link AvroDeserializationSchema}.
 */
public class AvroDeserializationSchemaTest {

	@Test
	public void testGenericRecordWithNoSchemaEmbedded() throws Exception {
		Schema schema = SchemaBuilder.record("simpleRecord")
			.fields()
			.requiredString("name")
			.requiredInt("age")
			.endRecord();
		DeserializationSchema<GenericRecord> deserializationSchema =
			AvroDeserializationSchema.forGeneric(
				schema
			);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
		new GenericDatumWriter<>(schema)
			.write(
				new GenericRecordBuilder(schema)
					.set("name", "Anna")
					.set("age", 25)
					.build(),
				encoder
			);

		encoder.flush();
		byte[] bytes = stream.toByteArray();

		GenericRecord anna = deserializationSchema.deserialize(bytes);
		assertEquals("Anna", anna.get("name").toString());
		assertEquals(25, anna.get("age"));
	}

	@Test
	public void testSpecificRecordWithConfluentSchemaRegistry() throws Exception {
		DeserializationSchema<Address> deserializer = AvroDeserializationSchema.forSpecific(Address.class);

		Address add = Address.newBuilder().setNum(1).setStreet("street")
			.setCity("city").setState("State").setZip("zip")
			.build();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
		new SpecificDatumWriter<>(Address.class)
			.write(add, encoder);

		encoder.flush();
		byte[] bytes = stream.toByteArray();

		GenericRecord genericRecord = deserializer.deserialize(bytes);
		assertEquals(add.getCity(), genericRecord.get("city").toString());
		assertEquals(add.getNum(), genericRecord.get("num"));
		assertEquals(add.getState(), genericRecord.get("state").toString());
	}

	

//	@Test
//	public void testGenericRecordWithConfluentSchemaRegistry() throws Exception {
//		MockSchemaRegistryClient client = new MockSchemaRegistryClient();
//		DeserializationSchema<GenericRecord> deserializer = AvroSerDe
//			.deserialize(GenericRecord.class)
//
//		AvroDeserializationSchema.geneirc()
//
//			.withSchema(SchemaBuilder.record("simpleRecord")
//				.fields()
//				.requiredString("name")
//				.requiredInt("age")
//				.optionalString("favouriteColor")
//				.endRecord())
////			.usingSchemaCoder(
////				confluentSchemaRegistry()
////					.customRegistryClient(() -> new ConfluentSchemaRegistryCoder(client)))
//			.build();
//
//		int schemaId = client.register("testTopic", SchemaBuilder.record("simpleRecord")
//			.fields()
//			.requiredString("name")
//			.requiredInt("age")
//			.endRecord()
//		);
//
//		ConsumerRecordMetaInfo record = mock(ConsumerRecordMetaInfo.class);
//		when(record.getTopic()).thenReturn("testTopic");
//		when(record.getMessage()).then((Answer<byte[]>) invocationOnMock -> {
//			ByteArrayOutputStream stream = new ByteArrayOutputStream();
//			DataOutputStream dataOutputStream = new DataOutputStream(stream);
//
//			dataOutputStream.writeByte(0);
//			dataOutputStream.writeInt(schemaId);
//
//			BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
//			new ReflectDatumWriter<>(SimplePojo.class)
//				.write(
//					new SimplePojo("Anna", 25),
//					encoder
//				);
//
//			encoder.flush();
//			return stream.toByteArray();
//		});
//
//		GenericRecord genericRecord = deserializer.deserialize(record);
//		assertEquals("Anna", genericRecord.get("name").toString());
//		assertEquals(25, genericRecord.get("age"));
//		assertNull(genericRecord.get("favouriteColor"));
//	}
//
//	@Test
//	public void testSpecificRecordWithConfluentSchemaRegistry() throws Exception {
//		MockSchemaRegistryClient client = new MockSchemaRegistryClient();
//		DeserializationSchema<GenericRecord> deserializer =
//			AvroSerDe.deserialize(GenericRecord.class)
//				.withSchema(Address.SCHEMA$)
//				.usingSchemaCoder(
//					AvroSerDe.confluentSchemaRegistry()
//						.customRegistryClient(() -> new ConfluentSchemaRegistryCoder(client)))
//				.build();
//
//		int schemaId = client.register("testTopic", Address.getClassSchema());
//
//		Address add = Address.newBuilder().setNum(1).setStreet("street")
//			.setCity("city").setState("State").setZip("zip")
//			.build();
//		ConsumerRecordMetaInfo record = mock(ConsumerRecordMetaInfo.class);
//		when(record.getTopic()).thenReturn("testTopic");
//		when(record.getMessage()).then((Answer<byte[]>) invocationOnMock -> {
//			ByteArrayOutputStream stream = new ByteArrayOutputStream();
//			DataOutputStream dataOutputStream = new DataOutputStream(stream);
//
//			dataOutputStream.writeByte(0);
//			dataOutputStream.writeInt(schemaId);
//
//			BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(stream, null);
//			new SpecificDatumWriter<>(Address.class)
//				.write(
//					add,
//					encoder
//				);
//
//			encoder.flush();
//			return stream.toByteArray();
//		});
//
//		GenericRecord genericRecord = deserializer.deserialize(record);
//		assertEquals(add.getCity(), genericRecord.get("city").toString());
//		assertEquals(add.getNum(), genericRecord.get("num"));
//		assertEquals(add.getState(), genericRecord.get("state").toString());
//	}
}
