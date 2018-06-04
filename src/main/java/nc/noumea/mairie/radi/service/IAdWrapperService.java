package nc.noumea.mairie.radi.service;

import java.util.List;

import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.LightUser;

public interface IAdWrapperService {
	
	public <T extends LightUser> List<T> getUsersFromProperty(Class<? extends LightUser> T, String propertyName, String propertySearchString) throws AdWrapperServiceException;
	public <T extends LightGroup> List<T> getGroupsFromProperty(Class<? extends LightGroup> T, String propertyName, String propertySearchString) throws AdWrapperServiceException;
	public LightGroup getGroupByDn(String dn) throws AdWrapperServiceException;
	<T extends LightUser> List<T> getListUsersFromProperty(Class<? extends LightUser> T,
			String propertyName, List<Integer> listPropertySearchString)
			throws AdWrapperServiceException;
}
