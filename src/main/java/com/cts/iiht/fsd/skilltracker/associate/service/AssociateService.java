package com.cts.iiht.fsd.skilltracker.associate.service;

import com.cts.iiht.fsd.skilltracker.associate.to.AssociateTO;

public interface AssociateService {

	public AssociateTO getAssociatesList();
	
	public AssociateTO getAssociateById(Long associateId);
	
	public Long saveAssociate(AssociateTO associateTO);
	
	public boolean deleteAssociate(Long associateId);
	
	public AssociateTO getAssociateSkills();
}
