package nc.noumea.mairie.radi.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import nc.noumea.mairie.radi.dto.Group;
import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.LightUser;
import nc.noumea.mairie.radi.dto.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdWrapperService implements IAdWrapperService {

	private Logger logger = LoggerFactory.getLogger(AdWrapperService.class);

	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Autowired
	@Qualifier("userOu")
	private String _userOu;

	@Autowired
	@Qualifier("groupOu")
	private String _groupOu;
	
	@Autowired
	@Qualifier("baseDn")
	private String _baseDn;

	@SuppressWarnings("unchecked")
	public <T extends LightUser> List<T> getUsersFromProperty(Class<? extends LightUser> T, String propertyName, String propertySearchString) throws AdWrapperServiceException {

		String ouSearchString = String.format("OU=%s", _userOu);
		String searchString = String.format("(&(objectClass=user)(%s=%s))",
				propertyName, propertySearchString);

		logger.info(
				"Looking for users in Ou [{}] with search string [{}]...",
				ouSearchString, searchString);
		
		List<T> users = new ArrayList<T>();
		
		AttributesMapper mapper = null;
		
		if (T.equals(LightUser.class))
			mapper = new LightUserLdapAttributesMapper();
		else
			mapper = new UserLdapAttributesMapper();
		
		try {
			users = ldapTemplate.search(ouSearchString, searchString, mapper);
		} catch (Exception ex) {
			throw new AdWrapperServiceException(String.format("An error occured while looking for users with property [%s] with value [%s] in AD...", propertyName, propertySearchString), ex);
		}

		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends LightUser> List<T> getListUsersFromProperty(Class<? extends LightUser> T, String propertyName, List<Integer> listPropertySearchString) throws AdWrapperServiceException {

		String ouSearchString = String.format("OU=%s", _userOu);
		
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("(&(objectClass=user)(|");
		for(Integer employeeNumber : listPropertySearchString) {
			strBuffer.append(String.format("(%s=%s)",
				propertyName, employeeNumber));
		}
		strBuffer.append("))");

		logger.info(
				"Looking for users in Ou [{}] with search string [{}]...",
				ouSearchString, strBuffer.toString());
		
		List<T> users = new ArrayList<T>();
		
		AttributesMapper mapper = null;
		
		if (T.equals(LightUser.class))
			mapper = new LightUserLdapAttributesMapper();
		else
			mapper = new UserLdapAttributesMapper();
		
		try {
			users = ldapTemplate.search(ouSearchString, strBuffer.toString(), mapper);
		} catch (Exception ex) {
			throw new AdWrapperServiceException(String.format("An error occured while looking for users with property [%s] with value [%s] in AD...", propertyName, strBuffer.toString()), ex);
		}

		return users;
	}
	
	private static class UserLdapAttributesMapper implements AttributesMapper {

		@SuppressWarnings("unchecked")
		@Override
		public User mapFromAttributes(Attributes attrs) throws NamingException {
			User u = new User();
			u.setsAMAccountName(attrs.get("sAMAccountName").get().toString());
			if (attrs.get("distinguishedName") !=null) u.setDistinguishedName(attrs.get("distinguishedName").get().toString());
			if (attrs.get("employeeNumber") !=null) u.setEmployeeNumber(Integer.parseInt(attrs.get("employeeNumber").get().toString()));
			if (attrs.get("mail") !=null) u.setMail(attrs.get("mail").get().toString());
			
			if (attrs.get("badPasswordTime") != null) u.setBadPasswordTime(Helper.fromActiveDirectoryLongDateTime(attrs.get("badPasswordTime").get().toString()));
			if (attrs.get("badPwdCount") != null) u.setBadPwdCount(attrs.get("badPwdCount").get().toString());
			if (attrs.get("businessCategory") != null) u.setBusinessCategory(attrs.get("businessCategory").get().toString());
			if (attrs.get("c") != null) u.setC(attrs.get("c").get().toString());
			if (attrs.get("cn") != null) u.setCn(attrs.get("cn").get().toString());
			if (attrs.get("company") != null) u.setCompany(attrs.get("company").get().toString());
			if (attrs.get("countryCode") != null) u.setCountryCode(attrs.get("countryCode").get().toString());
			if (attrs.get("department") != null) u.setDepartment(attrs.get("department").get().toString());
			if (attrs.get("departmentNumber") != null) u.setDepartmentNumber(attrs.get("departmentNumber").get().toString());
			if (attrs.get("description") != null) u.setDescription(attrs.get("description").get().toString());
			if (attrs.get("directReports") != null) u.setDirectReports(attrs.get("directReports").get().toString());
			if (attrs.get("displayName") != null) u.setDisplayName(attrs.get("displayName").get().toString());
			if (attrs.get("division") != null) u.setDivision(attrs.get("division").get().toString());
			if (attrs.get("employeeID") != null) u.setEmployeeID(attrs.get("employeeID").get().toString());
			if (attrs.get("employeeType") != null) u.setEmployeeType(attrs.get("employeeType").get().toString());
			if (attrs.get("accountExpires") != null) u.setAccountExpires(Helper.fromActiveDirectoryLongDateTime(attrs.get("accountExpires").get().toString()));
			if (attrs.get("extensionAttribute1") != null) u.setExtensionAttribute1(attrs.get("extensionAttribute1").get().toString());
			if (attrs.get("extensionAttribute10") != null) u.setExtensionAttribute10(attrs.get("extensionAttribute10").get().toString());
			if (attrs.get("extensionAttribute11") != null) u.setExtensionAttribute11(attrs.get("extensionAttribute11").get().toString());
			if (attrs.get("extensionAttribute12") != null) u.setExtensionAttribute12(attrs.get("extensionAttribute12").get().toString());
			if (attrs.get("extensionAttribute2") != null) u.setExtensionAttribute2(attrs.get("extensionAttribute2").get().toString());
			if (attrs.get("extensionAttribute3") != null) u.setExtensionAttribute3(attrs.get("extensionAttribute3").get().toString());
			if (attrs.get("extensionAttribute4") != null) u.setExtensionAttribute4(attrs.get("extensionAttribute4").get().toString());
			if (attrs.get("extensionAttribute5") != null) u.setExtensionAttribute5(attrs.get("extensionAttribute5").get().toString());
			if (attrs.get("facsimileTelephoneNumber") != null) u.setFacsimileTelephoneNumber(attrs.get("facsimileTelephoneNumber").get().toString());
			if (attrs.get("givenName") != null) u.setGivenName(attrs.get("givenName").get().toString());
			if (attrs.get("info") != null) u.setInfo(attrs.get("info").get().toString());
			if (attrs.get("initials") != null) u.setInitials(attrs.get("initials").get().toString());
			if (attrs.get("internationalISDNNumber") != null) u.setInternationalISDNNumber(attrs.get("internationalISDNNumber").get().toString());
			if (attrs.get("ipPhone") != null) u.setIpPhone(attrs.get("ipPhone").get().toString());
			if (attrs.get("l") != null) u.setL(attrs.get("l").get().toString());
			if (attrs.get("lastLogon") != null) u.setLastLogon(Helper.fromActiveDirectoryLongDateTime(attrs.get("lastLogon").get().toString()));
			if (attrs.get("lastLogonTimestamp") != null) u.setLastLogonTimestamp(Helper.fromActiveDirectoryLongDateTime(attrs.get("lastLogonTimestamp").get().toString()));
			if (attrs.get("lockoutTime") != null) u.setLockoutTime(attrs.get("lockoutTime").get().toString());
			if (attrs.get("logonCount") != null) u.setLogonCount(attrs.get("logonCount").get().toString());
			if (attrs.get("mailNickname") != null) u.setMailNickname(attrs.get("mailNickname").get().toString());
			if (attrs.get("manager") != null) u.setManager(attrs.get("manager").get().toString());
			if (attrs.get("mobile") != null) u.setMobile(attrs.get("mobile").get().toString());
			if (attrs.get("name") != null) u.setName(attrs.get("name").get().toString());
			if (attrs.get("objectCategory") != null) u.setObjectCategory(attrs.get("objectCategory").get().toString());
			if (attrs.get("otherTelephone") != null) u.setOtherTelephone(attrs.get("otherTelephone").get().toString());
			if (attrs.get("personalTitle") != null) u.setPersonalTitle(attrs.get("personalTitle").get().toString());
			if (attrs.get("postalAddress") != null) u.setPostalAddress(attrs.get("postalAddress").get().toString());
			if (attrs.get("postalCode") != null) u.setPostalCode(attrs.get("postalCode").get().toString());
			if (attrs.get("pwdLastSet") != null) u.setPwdLastSet(Helper.fromActiveDirectoryLongDateTime(attrs.get("pwdLastSet").get().toString()));
			if (attrs.get("sAMAccountType") != null) u.setsAMAccountType(attrs.get("sAMAccountType").get().toString());
			if (attrs.get("secretary") != null) u.setSecretary(attrs.get("secretary").get().toString());
			if (attrs.get("showInAddressBook") != null) u.setShowInAddressBook(attrs.get("showInAddressBook").get().toString());
			if (attrs.get("sn") != null) u.setSn(attrs.get("sn").get().toString());
			if (attrs.get("st") != null) u.setSt(attrs.get("st").get().toString());
			if (attrs.get("street") != null) u.setStreet(attrs.get("street").get().toString());
			if (attrs.get("streetAddress") != null) u.setStreetAddress(attrs.get("streetAddress").get().toString());
			if (attrs.get("telephoneAssistant") != null) u.setTelephoneAssistant(attrs.get("telephoneAssistant").get().toString());
			if (attrs.get("telephoneNumber") != null) u.setTelephoneNumber(attrs.get("telephoneNumber").get().toString());
			if (attrs.get("title") != null) u.setTitle(attrs.get("title").get().toString());
			if (attrs.get("url") != null) u.setUrl(attrs.get("url").get().toString());
			if (attrs.get("userAccountControl") != null) u.setUserAccountControl(attrs.get("userAccountControl").get().toString());
			if (attrs.get("userPrincipalName") != null) u.setUserPrincipalName(attrs.get("userPrincipalName").get().toString());
			if (attrs.get("uSNChanged") != null) u.setuSNChanged(attrs.get("uSNChanged").get().toString());
			if (attrs.get("uSNCreated") != null) u.setuSNCreated(attrs.get("uSNCreated").get().toString());
			if (attrs.get("whenChanged") != null) u.setWhenChanged(Helper.fromActiveDirectoryStringDateTime(attrs.get("whenChanged").get().toString()));
			if (attrs.get("whenCreated") != null) u.setWhenCreated(Helper.fromActiveDirectoryStringDateTime(attrs.get("whenCreated").get().toString()));
			if (attrs.get("wWWHomePage") != null) u.setwWWHomePage(attrs.get("wWWHomePage").get().toString());

			if (attrs.get("objectGUID") != null) u.setObjectGUID(Helper.fromBinaryArrayToString(attrs.get("objectGUID").get()));
			if (attrs.get("objectSid") != null) u.setObjectSid(Helper.fromBinaryArrayToString(attrs.get("objectSid").get()));
			
			if (attrs.get("memberof") != null) {
				NamingEnumeration<String> groups = (NamingEnumeration<String>) attrs.get("memberof").getAll();
				while(groups.hasMoreElements()) {
					u.getGroupNames().add(groups.nextElement());
				}
			}

			if (attrs.get("objectClass") != null) {
				NamingEnumeration<String> groups = (NamingEnumeration<String>) attrs.get("objectClass").getAll();
				while(groups.hasMoreElements()) {
					u.getObjectClass().add(groups.nextElement());
				}
			}
			
			return u;
		}
	}
	
	private static class LightUserLdapAttributesMapper implements AttributesMapper {

		@Override
		public LightUser mapFromAttributes(Attributes attrs) throws NamingException {
			LightUser u = new LightUser();
			u.setsAMAccountName((String) attrs.get("sAMAccountName").get());
			if (attrs.get("distinguishedName") !=null) u.setDistinguishedName((String) attrs.get("distinguishedName").get());
			if (attrs.get("employeeNumber") !=null) u.setEmployeeNumber(Integer.parseInt((String) attrs.get("employeeNumber").get()));
			if (attrs.get("mail") !=null) u.setMail((String) attrs.get("mail").get());
			if (attrs.get("department") !=null) u.setDepartment((String) attrs.get("department").get());
			
			return u;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends LightGroup> List<T> getGroupsFromProperty(Class<? extends LightGroup> T, String propertyName, String propertySearchString) throws AdWrapperServiceException {
		
		String ouSearchString = String.format("OU=%s", _groupOu);
		String searchString = String.format("(&(objectClass=group)(%s=%s))",
				propertyName, propertySearchString);

		logger.info(
				"Looking for groups in Ou [{}] with search string [{}]...",
				ouSearchString, searchString);
				
		AttributesMapper mapper = null;
		
		if (T.equals(LightGroup.class))
			mapper = new LightGroupLdapAttributesMapper();
		else
			mapper = new GroupLdapAttributesMapper();
		
		List<T> groups = new ArrayList<T>();
		
		try {
			groups = ldapTemplate.search(ouSearchString, searchString, mapper);
		} catch (Exception ex) {
			throw new AdWrapperServiceException(String.format("An error occured while looking for Groups with property [%s] with value [%s] in AD...", propertyName, propertySearchString), ex);
		}

		return groups;
	}

	private static class LightGroupLdapAttributesMapper implements AttributesMapper {

		@Override
		public LightGroup mapFromAttributes(Attributes attrs) throws NamingException {
			LightGroup g = new LightGroup();
			g.setsAMAccountName((String) attrs.get("sAMAccountName").get());
			g.setDistinguishedName((String) attrs.get("distinguishedName").get());
			if (attrs.get("name") != null) g.setName((String) attrs.get("name").get());
			if (attrs.get("description") != null) g.setDescription((String) attrs.get("description").get());
			
			return g;
		}
	}
	
	private static class GroupLdapAttributesMapper implements AttributesMapper {

		@SuppressWarnings("unchecked")
		@Override
		public Group mapFromAttributes(Attributes attrs) throws NamingException {
			Group g = new Group();
			g.setsAMAccountName((String) attrs.get("sAMAccountName").get());
			g.setDistinguishedName((String) attrs.get("distinguishedName").get());
			if (attrs.get("name") != null) g.setName((String) attrs.get("name").get());
			if (attrs.get("description") != null) g.setDescription((String) attrs.get("description").get());
			if (attrs.get("cn") != null) g.setCn(attrs.get("cn").get().toString());
			if (attrs.get("sAMAccountType") != null) g.setsAMAccountType(attrs.get("sAMAccountType").get().toString());
			if (attrs.get("whenChanged") != null) g.setWhenChanged(Helper.fromActiveDirectoryStringDateTime(attrs.get("whenChanged").get().toString()));
			if (attrs.get("whenCreated") != null) g.setWhenCreated(Helper.fromActiveDirectoryStringDateTime(attrs.get("whenCreated").get().toString()));
			if (attrs.get("objectGUID") != null) g.setObjectGUID(attrs.get("objectGUID").get().toString());
			if (attrs.get("objectSid") != null) g.setObjectSid(attrs.get("objectSid").get().toString());
			if (attrs.get("objectCategory") != null) g.setObjectCategory(attrs.get("objectCategory").get().toString());
			
			if (attrs.get("member") != null) {
				NamingEnumeration<String> groups = (NamingEnumeration<String>) attrs.get("member").getAll();
				while(groups.hasMoreElements()) {
					g.getMembers().add(groups.nextElement());
				}
			}
			
			if (attrs.get("objectClass") != null) {
				NamingEnumeration<String> groups = (NamingEnumeration<String>) attrs.get("objectClass").getAll();
				while(groups.hasMoreElements()) {
					g.getObjectClass().add(groups.nextElement());
				}
			}
			return g;
		}
	}

	@Override
	public LightGroup getGroupByDn(String dn) throws AdWrapperServiceException {

		// We can only lookup using the sub Dn (without the base dn we already have)
		String effectiveDn = dn.substring(0, dn.toLowerCase().lastIndexOf("," + _baseDn.toLowerCase()));
		
		logger.info("Looking for Group with Dn [{}] => [{}] in AD...", dn, effectiveDn);
		
		LightGroup g = null;
		
		try {
			g = (LightGroup) ldapTemplate.lookup(effectiveDn, new LightGroupLdapAttributesMapper());
		} catch (NameNotFoundException ex) {
			logger.error("Could not find Group with Dn [{}] => [{}] in AD", dn, effectiveDn);
		}
		
		return g;
	}

}
