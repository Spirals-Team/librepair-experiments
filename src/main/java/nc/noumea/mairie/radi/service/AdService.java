package nc.noumea.mairie.radi.service;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.radi.dto.Group;
import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.LightUser;
import nc.noumea.mairie.radi.dto.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AdService implements IAdService {

	private Logger logger = LoggerFactory.getLogger(AdService.class);

	@Autowired
	private IAdWrapperService ldapWrapperService;

	@Autowired
	@Qualifier("baseDn")
	private String _baseDn;
	
	@Autowired
	@Qualifier("userOu")
	private String _userOu;

	@Autowired
	@Qualifier("groupOu")
	private String _groupOu;
	
	//-- USERS --//
	
	@Override
	public User getSingleUserBySAMAccountname(String sAMAccountname) throws AdWrapperServiceException {
		return getSingleUserByProperty("sAMAccountName", sAMAccountname);
	}

	private User getSingleUserByProperty(String propertyName, String propertyValue) throws AdWrapperServiceException {
		
		List<User> users = getUsersByProperty(User.class, propertyName, propertyValue);
		
		if (users.size() > 1) {
			logger.info("Expected 1 user corresponding to [{}] with value [{}] but found [{}].",
							propertyName, propertyValue, users.size());
			return null;
		}

		if (users.size() == 0) {
			logger.info("Did not find any user corresponding to search criteria");
			return null;
		}
		
		User singleUser = users.get(0);
		
		// Logging info
		logger.info(
				"User found: displayName [{}], employeeNumber [{}]}, mail [{}]",
				new Object[] { singleUser.getDisplayName(), singleUser.getEmployeeNumber(), singleUser.getMail() });

		return singleUser;
	}
	
	@Override
	public List<LightGroup> getSingleUserGroupsBySAMAccountname(String sAMAccountname) throws AdWrapperServiceException {

		List<LightGroup> result = new ArrayList<LightGroup>();
		
		User user = getSingleUserBySAMAccountname(sAMAccountname);
		
		if (user == null)
			return null;
		
		for(String groupName : user.getGroupNames()) {
			LightGroup g = ldapWrapperService.getGroupByDn(groupName);
			if (g != null)
				result.add(g);
		}
		
		return result;
	}
	
	private <T extends LightUser> List<T> getUsersByProperty(Class<? extends LightUser> T, String propertyName, String propertyValue)  throws AdWrapperServiceException {
		List<T> users = ldapWrapperService.getUsersFromProperty(T, propertyName, propertyValue);
		
		// Logging info
		logger.info("{} Users found.", users.size());
		for (T u : users)
			logger.info("displayName [{}], employeeNumber [{}], mail [{}]",
					new Object[] { u.getDistinguishedName(), u.getEmployeeNumber(), u.getMail() });
		
		return users;
	}

	@Override
	public List<LightUser> searchUsers(String propertyName, String searchString) throws AdWrapperServiceException {
		List<LightUser> searchResult = getUsersByProperty(LightUser.class, propertyName, searchString);
		return searchResult;
	}
	
	private <T extends LightUser> List<T> getListUsersFromProperty(Class<? extends LightUser> T, String propertyName, List<Integer> propertyValue)  throws AdWrapperServiceException {
		List<T> users = ldapWrapperService.getListUsersFromProperty(T, propertyName, propertyValue);
		
		// Logging info
		logger.info("{} Users found.", users.size());
		for (T u : users)
			logger.info("displayName [{}], employeeNumber [{}], mail [{}]",
					new Object[] { u.getDistinguishedName(), u.getEmployeeNumber(), u.getMail() });
		
		return users;
	}

	@Override
	public List<LightUser> searchListUsers(String propertyName, List<Integer> searchString) throws AdWrapperServiceException {
		List<LightUser> searchResult = getListUsersFromProperty(LightUser.class, propertyName, searchString);
		return searchResult;
	}
	
	//-- GROUPS --//
	
	@Override
	public Group getSingleGroupBySAMAccountname(String sAMAccountName) throws AdWrapperServiceException {
		return getSingleGroupByProperty("sAMAccountName", sAMAccountName);
	}

	private Group getSingleGroupByProperty(String propertyName, String propertyValue) throws AdWrapperServiceException {
		
		List<Group> groups = getGroupsByProperty(Group.class, propertyName, propertyValue);
		
		if (groups.size() > 1) {
			logger.info("Expected 1 group corresponding to [{}] with value [{}] but found [{}].",
							propertyName, propertyValue, groups.size());
			return null;
		}
		
		if (groups.size() == 0) {
			logger.info("Did not find any group corresponding to search criteria");
			return null;
		}
		
		Group singleGroup = groups.get(0);
		
		return singleGroup;
	}

	@Override
	public List<LightUser> getSingleGroupUsersBySAMAccountname(String sAMAccountName) throws AdWrapperServiceException {

		// Find the group by its sAMAccountName
		Group g = getSingleGroupBySAMAccountname(sAMAccountName);
		
		if (g == null)
			return null;
		
		// Then fetch its dn
		String groupDn = g.getDistinguishedName();
		// http://msdn.microsoft.com/en-us/library/aa746475%28VS.85%29.aspx
		// Active Directory specific LDAP_MATCHING_RULE_IN_CHAIN key
		String propertyName = "memberof:1.2.840.113556.1.4.1941:";
		String propertyValue = groupDn;
		
		// Get the users matching the search criteria
		List<LightUser> users = ldapWrapperService.getUsersFromProperty(LightUser.class, propertyName, propertyValue);
		
		return users;
	}

	private <T extends LightGroup> List<T> getGroupsByProperty(Class<? extends LightGroup> T, String propertyName, String propertyValue)  throws AdWrapperServiceException {
		
		List<T> groups = ldapWrapperService.getGroupsFromProperty(T, propertyName, propertyValue);
		
		// Logging info
		logger.info("{} Groups found.", groups.size());
		for (T g : groups)
			logger.info("Group found: name [{}]", g.getName());
		
		return groups;
	}
	
	@Override
	public List<LightGroup> searchGroups(String propertyName, String searchString) throws AdWrapperServiceException {
		List<LightGroup> searchResult = getGroupsByProperty(LightGroup.class, propertyName, searchString);
		return searchResult;
	}

}
