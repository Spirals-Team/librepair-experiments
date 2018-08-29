/*
 * Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal Genome Nexus.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.cbioportal.genome_nexus.service;

import org.cbioportal.genome_nexus.model.GenomicLocation;
import org.cbioportal.genome_nexus.model.VariantAnnotation;
import org.cbioportal.genome_nexus.service.exception.VariantAnnotationNotFoundException;
import org.cbioportal.genome_nexus.service.exception.VariantAnnotationWebServiceException;

import java.util.List;

/**
 * @author Benjamin Gross
 */
public interface VariantAnnotationService
{
    VariantAnnotation getAnnotation(String variant)
        throws VariantAnnotationNotFoundException, VariantAnnotationWebServiceException;
    List<VariantAnnotation> getAnnotations(List<String> variants);
    VariantAnnotation getAnnotation(String variant, String isoformOverrideSource, List<String> fields)
        throws VariantAnnotationWebServiceException, VariantAnnotationNotFoundException;
    List<VariantAnnotation> getAnnotations(List<String> variants, String isoformOverrideSource, List<String> fields);

    VariantAnnotation getAnnotation(GenomicLocation genomicLocation)
        throws VariantAnnotationNotFoundException, VariantAnnotationWebServiceException;
    VariantAnnotation getAnnotationByGenomicLocation(String genomicLocation)
        throws VariantAnnotationNotFoundException, VariantAnnotationWebServiceException;
    List<VariantAnnotation> getAnnotationsByGenomicLocations(List<GenomicLocation> genomicLocations);
    VariantAnnotation getAnnotationByGenomicLocation(String genomicLocation, String isoformOverrideSource, List<String> fields)
        throws VariantAnnotationWebServiceException, VariantAnnotationNotFoundException;
    List<VariantAnnotation> getAnnotationsByGenomicLocations(List<GenomicLocation> genomicLocations, String isoformOverrideSource, List<String> fields);

    VariantAnnotation getAnnotationById(String variantId)
        throws VariantAnnotationNotFoundException, VariantAnnotationWebServiceException;
    List<VariantAnnotation> getAnnotationsByIds(List<String> variantIds);
    VariantAnnotation getAnnotationById(String variantId, String isoformOverrideSource, List<String> fields)
        throws VariantAnnotationWebServiceException, VariantAnnotationNotFoundException;
    List<VariantAnnotation> getAnnotationsByIds(List<String> variantIds, String isoformOverrideSource, List<String> fields);
}
