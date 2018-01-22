package py.org.fundacionparaguaya.pspserver.families.services;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyMapDTO;

public interface FamilySnapshotsManager {

	FamilyMapDTO getFamilyMapById(Long familyId);
	
	void deleteSnapshotByFamily(Long familyId);
	
}
