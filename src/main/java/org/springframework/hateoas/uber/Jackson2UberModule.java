/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hateoas.uber;

import static org.springframework.hateoas.JacksonHelper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.support.PropertyUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * @author Greg Turnquist
 */
public class Jackson2UberModule extends SimpleModule {

	public Jackson2UberModule() {
		super("uber-module", new Version(1, 0, 0, null, "org.springframework.hateoas", "spring-hateoas"));
		
		setMixInAnnotation(ResourceSupport.class, ResourceSupportMixin.class);
		setMixInAnnotation(Resource.class, ResourceMixin.class);
		setMixInAnnotation(Resources.class, ResourcesMixin.class);
	}

	static class UberResourceSupportSerializer extends ContainerSerializer<ResourceSupport> implements ContextualSerializer {

		private final BeanProperty property;

		UberResourceSupportSerializer(BeanProperty property) {

			super(ResourceSupport.class, false);
			this.property = property;
		}

		UberResourceSupportSerializer() {
			this(null);
		}

		@Override
		public void serialize(ResourceSupport value, JsonGenerator gen, SerializerProvider provider) throws IOException {

			ObjectMapper mapper = (ObjectMapper) gen.getCodec();

//			UberDocument uber = new UberDocument(uberDocument()
//				.version("1.0")
//				.data(UberData.toUberData(value, mapper).getData())
//				.build());

			List<NewUberData> data = NewUberData.toUberData(value, mapper).getData();

			NewUber newUber = new NewUber()
				.withVersion("1.0")
				.withData(data);

			NewUberDocument doc = new NewUberDocument()
				.withUber(newUber);

			provider
				.findValueSerializer(NewUberDocument.class, property)
				.serialize(doc, gen, provider);
		}

		@Override
		public JavaType getContentType() {
			return null;
		}

		@Override
		public JsonSerializer<?> getContentSerializer() {
			return null;
		}

		@Override
		public boolean hasSingleElement(ResourceSupport value) {
			return false;
		}

		@Override
		protected ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
			return null;
		}

		@Override
		public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
			return new UberResourceSupportSerializer(property);
		}
	}

	static class UberResourceSerializer extends ContainerSerializer<Resource<?>> implements ContextualSerializer {

		private final BeanProperty property;

		UberResourceSerializer(BeanProperty property) {

			super(Resource.class, false);
			this.property = property;
		}

		UberResourceSerializer() {
			this(null);
		}

		@Override
		public void serialize(Resource<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {

			ObjectMapper mapper = (ObjectMapper) gen.getCodec();

//			UberDocument uber = new UberDocument(uberDocument()
//				.version("1.0")
//				.data(UberData.toUberData(value, mapper).getData())
//				.build());

			NewUber newUber = new NewUber()
				.withVersion("1.0")
				.withData(NewUberData.toUberData(value, mapper).getData());

			NewUberDocument doc = new NewUberDocument()
				.withUber(newUber);

			provider
				.findValueSerializer(NewUberDocument.class, property)
				.serialize(doc, gen, provider);
		}

		@Override
		public JavaType getContentType() {
			return null;
		}

		@Override
		public JsonSerializer<?> getContentSerializer() {
			return null;
		}

		@Override
		public boolean hasSingleElement(Resource<?> value) {
			return false;
		}

		@Override
		protected ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
			return null;
		}

		@Override
		public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
			return new UberResourceSerializer(property);
		}
	}

	static class UberResourcesSerializer extends ContainerSerializer<Resources<?>> implements ContextualSerializer {

		private BeanProperty property;

		UberResourcesSerializer(BeanProperty property) {

			super(Resources.class, false);
			this.property = property;
		}

		UberResourcesSerializer() {
			this(null);
		}
		
		@Override
		public void serialize(Resources<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {

			ObjectMapper mapper = (ObjectMapper) gen.getCodec();

//			UberDocument uber = new UberDocument(uberDocument()
//				.version("1.0")
//				.data(UberData.toUberData(value, mapper).getData())
//				.build());

			NewUber newUber = new NewUber()
				.withVersion("1.0")
				.withData(NewUberData.toUberData(value, mapper));

			NewUberDocument doc = new NewUberDocument()
				.withUber(newUber);

			provider
				.findValueSerializer(NewUberDocument.class, property)
				.serialize(doc, gen, provider);
		}

		@Override
		public JavaType getContentType() {
			return null;
		}

		@Override
		public JsonSerializer<?> getContentSerializer() {
			return null;
		}

		@Override
		public boolean hasSingleElement(Resources<?> value) {
			return false;
		}

		@Override
		protected ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
			return null;
		}

		@Override
		public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
			return new UberResourcesSerializer(property);
		}
	}

	static class UberActionSerializer extends StdSerializer<UberAction> {

		UberActionSerializer() {
			super(UberAction.class);
		}

		@Override
		public void serialize(UberAction value, JsonGenerator gen, SerializerProvider provider) throws IOException {

			gen.writeStartObject();
			gen.writeString(value.toString());
			gen.writeEndObject();
		}
	}

	static class UberResourceSupportDeserializer extends ContainerDeserializerBase<ResourceSupport> implements ContextualDeserializer {

		private JavaType contentType;

		UberResourceSupportDeserializer(JavaType contentType) {

			super(contentType);
			this.contentType = contentType;
		}

		UberResourceSupportDeserializer() {
			this(TypeFactory.defaultInstance().constructSimpleType(UberDocument.class, new JavaType[0]));
		}
		

		@Override
		public ResourceSupport deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

			NewUberDocument doc = p.getCodec().readValue(p, NewUberDocument.class);

			return doc.getUber().getData().stream()
				.filter(uberData -> !StringUtils.isEmpty(uberData.getLabel()))
				.findFirst()
				.map(uberData -> {
					try {
						ObjectMapper mapper = (ObjectMapper) p.getCodec();

						Map<String, Object> fooMap = mapper.readValue(uberData.getValue().toString(),
							TypeFactory.defaultInstance().constructParametricType(Map.class, String.class, Object.class));

						ResourceSupport obj = (ResourceSupport) PropertyUtils.createObjectFromProperties(this.getContentType().getRawClass(), fooMap);
						obj.add(doc.getUber().getLinks());
						return obj;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				})
				.orElseGet(() -> {
					ResourceSupport resourceSupport = new ResourceSupport();
					resourceSupport.add(doc.getUber().getLinks());
					return resourceSupport;
				});

//			for (UberData uberData : uber.getUber().getData()) {
//				if (!StringUtils.isEmpty(uberData.getLabel())) {
//					try {
//						Class<?> rawClass = this.getContentType().getRawClass();
//						ResourceSupport foo = (ResourceSupport) rawClass.newInstance();
//						Map<String, Object> fooMap = mapper.readValue(uberData.getValue().toString(),
//							TypeFactory.defaultInstance().constructParametricType(Map.class, String.class, Object.class));
//
//						for (Map.Entry<String, Object> entry : fooMap.entrySet()) {
//							Field field = ReflectionUtils.findField(rawClass, entry.getKey());
//							ReflectionUtils.makeAccessible(field);
//							ReflectionUtils.setField(field, foo, entry.getValue());
//						}
//
//						foo.add(links);
//
//						return foo;
//					} catch (Exception e) {
//						throw new RuntimeException(e);
//					}
//				}
//			}
//			ResourceSupport resourceSupport = new ResourceSupport();
//			resourceSupport.add(links);
//			return resourceSupport;
		}

		@Override
		public JavaType getContentType() {
			return this.contentType;
		}

		@Override
		public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {

			if (property != null) {
				JavaType vc = property.getType().getContentType();
				return new UberResourceSupportDeserializer(vc);
			} else {
				return new UberResourceSupportDeserializer(ctxt.getContextualType());
			}
		}

		/**
		 * Accesor for deserializer use for deserializing content values.
		 */
		@Override
		public JsonDeserializer<Object> getContentDeserializer() {
			return null;
		}
	}

	static class UberResourceDeserializer extends ContainerDeserializerBase<Resource<?>> implements ContextualDeserializer {

		private JavaType contentType;

		UberResourceDeserializer(JavaType contentType) {

			super(contentType);
			this.contentType = contentType;
		}

		UberResourceDeserializer() {
			this(TypeFactory.defaultInstance().constructSimpleType(UberDocument.class, new JavaType[0]));
		}

		@Override
		public Resource<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

			NewUberDocument doc = p.getCodec().readValue(p, NewUberDocument.class);

			ObjectMapper mapper = (ObjectMapper) p.getCodec();

			return doc.getUber().getData().stream()
				.filter(uberData -> !StringUtils.isEmpty(uberData.getLabel()))
				.findFirst()
				.map(newUberData -> {
					try {
						Object value = mapper.readValue(newUberData.getValue().toString(), Class.forName(newUberData.getLabel()));
						return new Resource<>(value, doc.getUber().getLinks());
					} catch (IOException | ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.orElseThrow(() -> new IllegalStateException("No data entry containing a 'value' was found in this document!"));

//			for (UberData uberData : uber.getUber().getData()) {
//				if (!StringUtils.isEmpty(uberData.getLabel())) {
//					Object value = uberData.getValue();
//
//					try {
//						value = mapper.readValue(value.toString(), Class.forName(uberData.getLabel()));
//					} catch (ClassNotFoundException e) {
//						throw new RuntimeException(e);
//					}
//
//					return new Resource<Object>(value, uber.getUber().getLinks());
//				}
//			}
//			throw new IllegalStateException("No data entry containing a 'value' was found in this document!");
		}

		@Override
		public JavaType getContentType() {
			return this.contentType;
		}

		@Override
		public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {

			if (property != null) {
				JavaType vc = property.getType().getContentType();
				return new UberResourceDeserializer(vc);
			} else {
				return new UberResourceDeserializer(ctxt.getContextualType());
			}
		}

		/**
		 * Accesor for deserializer use for deserializing content values.
		 */
		@Override
		public JsonDeserializer<Object> getContentDeserializer() {
			return null;
		}
	}

	static class UberResourcesDeserializer extends ContainerDeserializerBase<Resources<?>> implements ContextualDeserializer {

		private JavaType contentType;

		UberResourcesDeserializer(JavaType contentType) {

			super(contentType);
			this.contentType = contentType;
		}

		UberResourcesDeserializer() {
			this(TypeFactory.defaultInstance().constructSimpleType(UberDocument.class, new JavaType[0]));
		}
		
		@Override
		public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {

			if (property != null) {
				JavaType vc = property.getType().getContentType();
				return new UberResourcesDeserializer(vc);
			} else {
				return new UberResourcesDeserializer(ctxt.getContextualType());
			}
		}

		/**
		 * Accessor for declared type of contained value elements; either exact
		 * type, or one of its supertypes.
		 */
		@Override
		public JavaType getContentType() {
			return this.contentType;
		}

		/**
		 * Accesor for deserializer use for deserializing content values.
		 */
		@Override
		public JsonDeserializer<Object> getContentDeserializer() {
			return null;
		}

		@Override
		public Resources<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

			NewUberDocument doc = p.getCodec().readValue(p, NewUberDocument.class);

			ObjectMapper mapper = (ObjectMapper) p.getCodec();

			List<Object> content = new ArrayList<>();

			for (NewUberData uberData : doc.getUber().getData()) {

				if (uberData.getLinks().isEmpty()) {

					List<Link> resourceLinks = new ArrayList<>();
					Resource<?> resource = null;

					for (NewUberData item : uberData.getData()) {
						if (item.getRel() != null) {
							item.getRel().forEach(rel -> resourceLinks.add(new Link(item.getUrl(), rel)));
						} else {
							try {
								Object value = mapper.readValue(item.getValue().toString(), Class.forName(item.getLabel()));
								resource = new Resource<>(value);
							} catch (ClassNotFoundException e) {
								throw new RuntimeException(e);
							}
						}
					}

					if (resource != null) {
						resource.add(resourceLinks);
						content.add(resource);
					} else {
						throw new RuntimeException("No content!");
					}
				}
			}

//			for (UberData uberData : uber.getUber().getData()) {
//
//				if (!uberData.hasLinks()) {
//
//					List<Link> resourceLinks = new ArrayList<Link>();
//					Resource<?> resource = null;
//
//					for (UberData item : uberData.getData()) {
//						if (item.hasLinks()) {
//							for (String rel : item.getRels()) {
//								resourceLinks.add(new Link(item.getUrl()).withRel(rel));
//							}
//						} else {
//							try {
//								Object value = mapper.readValue(item.getValue().toString(), Class.forName(item.getLabel()));
//								resource = new Resource<Object>(value);
//							} catch (ClassNotFoundException e) {
//								throw new RuntimeException(e);
//							}
//						}
//					}
//
//					if (resource != null) {
//						resource.add(resourceLinks);
//						content.add(resource);
//					} else {
//						throw new RuntimeException("No content!");
//					}
//				}
//			}

			if (isResourcesOfResource(this.getContentType())) {
				/*
				 * Either return a Resources<Resource<T>>...
				 */
				return new Resources<>(content, doc.getUber().getLinks());

//				return new Resources<Object>(content, uber.getUber().getLinks());
			} else {
				/*
				 * ...or return a Resources<T>
				 */

				List<Object> resourceLessContent = new ArrayList<>();

				content.forEach(item -> {
					Resource<?> resource = (Resource<?>) item;
					resourceLessContent.add(resource.getContent());
				});

//				for (Object item : content) {
//					Resource<?> resource = (Resource<?>) item;
//					resourceLessContent.add(resource.getContent());
//				}

				return new Resources<>(resourceLessContent, doc.getUber().getLinks());
				
//				return new Resources<Object>(resourceLessContent, uber.getUber().getLinks());
			}
		}
	}

	static class UberActionDeserializer extends StdDeserializer<UberAction> {

		UberActionDeserializer() {
			super(UberAction.class);
		}

		@Override
		public UberAction deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

			return UberAction.valueOf(p.getText().toUpperCase());
		}
	}


	static class UberHandlerInstantiator extends HandlerInstantiator {

		private final Map<Class<?>, Object> serializers = new HashMap<Class<?>, Object>();

		UberHandlerInstantiator() {

			this.serializers.put(UberResourceSerializer.class, new UberResourceSerializer());
		}

		@Override
		public JsonDeserializer<?> deserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> deserClass) {
			return (JsonDeserializer<?>) findInstance(deserClass);
		}

		@Override
		public KeyDeserializer keyDeserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> keyDeserClass) {
			return (KeyDeserializer) findInstance(keyDeserClass);
		}

		@Override
		public JsonSerializer<?> serializerInstance(SerializationConfig config, Annotated annotated, Class<?> serClass) {
			return (JsonSerializer<?>) findInstance(serClass);
		}

		@Override
		public TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> config, Annotated annotated, Class<?> builderClass) {
			return (TypeResolverBuilder<?>) findInstance(builderClass);
		}

		@Override
		public TypeIdResolver typeIdResolverInstance(MapperConfig<?> config, Annotated annotated, Class<?> resolverClass) {
			return (TypeIdResolver) findInstance(resolverClass);
		}

		private Object findInstance(Class<?> type) {

			Object result = this.serializers.get(type);
			return result != null ? result : BeanUtils.instantiateClass(type);
		}
	}

}
