package nc.noumea.mairie.radi.service;

import java.util.List;

import nc.noumea.mairie.radi.dto.Group;
import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.LightUser;
import nc.noumea.mairie.radi.dto.User;

public interface IAdService {

	public User getSingleUserBySAMAccountname(String sAMAccountname) throws AdWrapperServiceException;
	public List<LightGroup> getSingleUserGroupsBySAMAccountname(String sAMAccountname) throws AdWrapperServiceException;
	
	public Group getSingleGroupBySAMAccountname(String sAMAccountName) throws AdWrapperServiceException;
	public List<LightUser> getSingleGroupUsersBySAMAccountname(String sAMAccountName) throws AdWrapperServiceException;

	public List<LightUser> searchUsers(String propertyName, String searchString) throws AdWrapperServiceException;
	public List<LightGroup> searchGroups(String propertyName, String searchString) throws AdWrapperServiceException;
	List<LightUser> searchListUsers(String propertyName,
			List<Integer> searchString) throws AdWrapperServiceException;
}
