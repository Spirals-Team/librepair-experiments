package org.openmrs.module.registrationcore.api.mpi.openempi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.module.registrationcore.api.mpi.common.MpiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.LinkedList;
import java.util.List;

public class PatientIdentifierMapper {

    private final Log log = LogFactory.getLog(this.getClass());
    //first element ("key") - local, second element ("value") - mpi.
    private List<IdentifierMapPair> MAPPED_ID;

    @Autowired
    @Qualifier("registrationcore.mpiProperties")
    private MpiProperties mpiProperties;

    public String getMappedLocalIdentifierTypeUuid(String mpiIdentifierTypeId) {
        validateInit();
        for (IdentifierMapPair pair : MAPPED_ID) {
            if (pair.mpiIdentifierId.equals(mpiIdentifierTypeId)) {
                return pair.localIdentifierUuid;
            }
        }
        return null;
    }

    public String getMappedMpiIdentifierTypeId(String localIdentifierTypeUuid) {
        validateInit();
        for (IdentifierMapPair pair : MAPPED_ID) {
            if (pair.localIdentifierUuid.equals(localIdentifierTypeUuid)) {
                return pair.mpiIdentifierId;
            }
        }
        return null;
    }

    public String getMappedMpiUniversalIdType(String localIdentifierTypeUuid) {
        validateInit();
        for (IdentifierMapPair pair : MAPPED_ID) {
            if (pair.localIdentifierUuid.equals(localIdentifierTypeUuid)) {
                return pair.universalIdType;
            }
        }
        return null;
    }

    private void validateInit() {
        if (MAPPED_ID == null) {
            init();
        }
    }

    public void init() {
        MAPPED_ID = new LinkedList<IdentifierMapPair>();
        log.info("start init method of PatientIdentifierMapper");
        List<String> properties = mpiProperties.getLocalMpiIdentifierTypeMap();
        for (String mappedIdentifiers : properties) {
            IdentifierMapPair pair = parseIdentifiers(mappedIdentifiers);
            MAPPED_ID.add(pair);

            log.info("Initialized local:mpi identifier type pair. Local: " + pair.localIdentifierUuid
                    + " , MPI: " + pair.mpiIdentifierId);
        }
    }

    private IdentifierMapPair parseIdentifiers(String mappedIdentifiers) {
        String local = getLocalPart(mappedIdentifiers);
        String mpi = getMpiPart(mappedIdentifiers);
        String type = getIdTypePart(mappedIdentifiers);
        return createPair(local, mpi, type);
    }

    private String getLocalPart(String mappedIdentifiers) {
        String parts[] = mappedIdentifiers.split(":");
        if (parts.length > 0) {
            return parts[0];
        }
        return null;
    }

    private String getMpiPart(String mappedIdentifiers) {
        String parts[] = mappedIdentifiers.split(":");
        if (parts.length > 1) {
            return parts[1];
        }
        return null;
    }

    private String getIdTypePart(String mappedIdentifiers) {
        String parts[] = mappedIdentifiers.split(":");
        if (parts.length > 2) {
            return parts[2];
        }
        return null;
    }

    private IdentifierMapPair createPair(String localString, String mpiString, String universalIdType) {
        try {
            return new IdentifierMapPair(localString, mpiString, universalIdType);
        } catch (NumberFormatException e) {
            throw new APIException("Can't create identifier pair for values: local= " +
                    localString + ", mpi=" + mpiString + ", type=" + universalIdType);
        }
    }

    private class IdentifierMapPair {

        public final String localIdentifierUuid;

        public final String mpiIdentifierId;

        public final String universalIdType;

        public IdentifierMapPair(String localIdentifierUuid, String mpiIdentifierId, String universalIdType) {
            this.localIdentifierUuid = localIdentifierUuid;
            this.mpiIdentifierId = mpiIdentifierId;
            this.universalIdType = universalIdType;
        }
    }
}