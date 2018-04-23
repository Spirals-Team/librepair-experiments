package com.epam.brest.course.utility.dozer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * this class contains generic mapping definitions for dozer.
 */
@Service
public class MappingService {
    /**
     * the dozer mapper.
     */
    @Autowired
    private Mapper mapper;

    /**
     * @param source object to convert.
     * @param target copy to target.
     * @param <T> type parameter.
     * @return mapped obj.
     */
    public final <T> T map(final Object source, final Class<T> target) {
        return mapper.map(source, target);
    }

    /**
     *for void.
     * @param source obj.
     * @param target to be mapped.
     * @param <T> type param.
     */
    public final <T> void map(final Object source, final T target) {
        mapper.map(source, target);
    }


    /**
     * @param objects obj.
     * @param target to be mapped.
     * @param destination list to return.
     * @param <T> type paramm.
     * @param <O> type param.
     * @return list.
     */
    public final  <T, O> List<O> map(final List<T> objects,
                                         final Class<O> target,
                                                    final List<O> destination) {

        return (List<O>) internalMap(objects, target, destination);
    }

    /**
     *
     * @param objects obj.
     * @param target to be mapped.
     * @param <T> type param.
     * @param <O> type and return type for collection.
     * @return collection.
     */
    //for collection
    public final  <T, O> Collection<O> map(final Collection<T> objects,
                                                    final Class<O> target) {

        return (List<O>) internalMap(objects, target, new ArrayList<>());
    }

    /**
     *
     * @param objects obj.
     * @param target to be mapped
     * @param destination to be coped to .
     * @param <T> type param.
     * @param <O> type param and return type.
     * @return list of type parameter o.
     */
    private  <T, O> Collection<O> internalMap(final Collection<T> objects,
                                             final Class<O> target,
                                          final Collection<O> destination) {
        for (T t : objects) {
            destination.add(mapper.map(t, target));
        }
        return destination;
    }

}
