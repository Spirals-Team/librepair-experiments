package org.simpleflatmapper.map.asm;

import org.simpleflatmapper.map.FieldKey;
import org.simpleflatmapper.map.FieldMapper;
import org.simpleflatmapper.map.Mapper;
import org.simpleflatmapper.map.MappingContext;
import org.simpleflatmapper.reflect.BiInstantiator;
import org.simpleflatmapper.reflect.Instantiator;
import org.simpleflatmapper.reflect.asm.AsmFactory;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapperAsmFactory {

    private final AsmFactory asmFactory;

	public MapperAsmFactory(AsmFactory asmFactory) {
        this.asmFactory = asmFactory;
	}


    private final ConcurrentMap<MapperKey, Class<? extends Mapper<?, ?>>> fieldMapperCache = new ConcurrentHashMap<MapperKey, Class<? extends Mapper<?, ?>>>();

    private <S, T> String generateClassNameForFieldMapper(final FieldMapper<S, T>[] mappers, final FieldMapper<S, T>[] constructorMappers, final Class<? super S> source, final Class<T> target) {
        StringBuilder sb = new StringBuilder();

        sb.append("org.simpleflatmapper.map.generated.");
        sb.append(asmFactory.getPackageName(target));
        sb.append(".AsmMapperFrom").append(asmFactory.replaceArray(source.getSimpleName()));
        sb.append("To").append(asmFactory.replaceArray(target.getSimpleName()));

        if (constructorMappers.length > 0) {
            sb.append("ConstInj").append(constructorMappers.length);
        }

        if (mappers.length > 0) {
            sb.append("Inj").append(mappers.length);
        }

        sb.append("_I").append(Long.toHexString(asmFactory.getNextClassNumber()));

        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public <S, T> Mapper<S, T> createMapper(final FieldKey<?>[] keys,
                                            final FieldMapper<S, T>[] mappers,
                                            final FieldMapper<S, T>[] constructorMappers,
                                            final BiInstantiator<S, MappingContext<? super S>, T> instantiator,
                                            final Class<? super S> source,
                                            final Class<T> target) throws Exception {

        MapperKey key = new MapperKey(keys, mappers, constructorMappers, instantiator, target, source);
        Class<Mapper<S, T>> type = (Class<Mapper<S, T>>) fieldMapperCache.get(key);
        if (type == null) {

            final String className = generateClassNameForFieldMapper(mappers, constructorMappers, source, target);
            final byte[] bytes = MapperAsmBuilder.dump(className, mappers, constructorMappers, source, target);

            type = (Class<Mapper<S, T>>) asmFactory.createClass(className, bytes, target.getClass().getClassLoader());
            fieldMapperCache.put(key, type);
        }
        final Constructor<?> constructor = type.getDeclaredConstructors()[0];
        return (Mapper<S, T>) constructor.newInstance(mappers, constructorMappers, instantiator);
    }
}
