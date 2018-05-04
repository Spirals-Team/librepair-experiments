package org.cbioportal.genome_nexus.service;

import org.cbioportal.genome_nexus.service.exception.ResourceMappingException;

import java.util.List;
import java.util.Optional;

public interface CachedExternalResourceFetcher<T>
{
    Optional<T> fetchAndCache(String id) throws ResourceMappingException;
    List<T> fetchAndCache(List<String> id) throws ResourceMappingException;
}
