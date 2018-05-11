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
package org.apache.flink.formats.avro.registry.confluent;

import org.apache.flink.formats.avro.RegistryAvroDeserializationSchema;
import org.apache.flink.formats.avro.SchemaCoder;
import org.apache.flink.formats.avro.SchemaCoderProvider;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;

import javax.annotation.Nullable;

public class ConfluentRegistryAvroDeserializationSchema<T>
	extends RegistryAvroDeserializationSchema<T> {

	private static final int DEFAULT_IDENTITY_MAP_CAPACITY = 1000;

	/**
	 * Creates a Avro deserialization schema.
	 *
	 * @param recordClazz         class to which deserialize. Should be either
	 *                            {@link SpecificRecord} or {@link GenericRecord}.
	 * @param reader              reader's Avro schema. Should be provided if recordClazz is
	 *                            {@link GenericRecord}
	 * @param schemaCoderProvider
	 */
	private ConfluentRegistryAvroDeserializationSchema(
		Class<T> recordClazz,
		@Nullable Schema reader,
		SchemaCoderProvider schemaCoderProvider) {
		super(recordClazz, reader, schemaCoderProvider);
	}

	public static ConfluentRegistryAvroDeserializationSchema<GenericRecord> forGeneric(
		Schema schema, String url) {
		return forGeneric(schema, url, DEFAULT_IDENTITY_MAP_CAPACITY);
	}

	public static ConfluentRegistryAvroDeserializationSchema<GenericRecord> forGeneric(
		Schema schema, String url, int identityMapCapacity) {
		return new ConfluentRegistryAvroDeserializationSchema<>(
			GenericRecord.class,
			schema,
			new CachedSchemaCoderProvider(url, identityMapCapacity));
	}

	public static <T extends SpecificRecord> ConfluentRegistryAvroDeserializationSchema<T> forSpecific(
		Class<T> tClass, String url) {
		return forSpecific(tClass, url, DEFAULT_IDENTITY_MAP_CAPACITY);
	}

	public static <T extends SpecificRecord> ConfluentRegistryAvroDeserializationSchema<T> forSpecific(
		Class<T> tClass, String url, int identityMapCapacity) {
		return new ConfluentRegistryAvroDeserializationSchema<>(
			tClass,
			null,
			new CachedSchemaCoderProvider(url, identityMapCapacity)
		);
	}

	private static class CachedSchemaCoderProvider implements SchemaCoderProvider {

		private final String url;
		private final int identityMapCapacity;

		private CachedSchemaCoderProvider(String url, int identityMapCapacity) {
			this.url = url;
			this.identityMapCapacity = identityMapCapacity;
		}

		@Override
		public SchemaCoder get() {
			return new ConfluentSchemaRegistryCoder(new CachedSchemaRegistryClient(
				url,
				identityMapCapacity));
		}
	}
}
