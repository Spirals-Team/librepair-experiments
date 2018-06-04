package nc.noumea.mairie.radi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.noumea.mairie.radi.dto.Group;
import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.User;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class LdapWrapperServiceTest {

	@Test
	public void testGetUsersByProperty_sAMAccountname_noResult_returnEmptyList() throws AdWrapperServiceException {
		
		// Given
		String propertyName = "prop";
		String propertyValue = "value";
		String userOu = "userOu";
		
		LdapTemplate ldapMock = Mockito.mock(LdapTemplate.class);
		Mockito.when(ldapMock.search(Mockito.eq("OU=userOu"), Mockito.eq("(&(objectClass=user)(prop=value))"), Mockito.any(AttributesMapper.class))).thenReturn(new ArrayList<User>());
		
		AdWrapperService service = new AdWrapperService();
		ReflectionTestUtils.setField(service, "ldapTemplate", ldapMock);
		ReflectionTestUtils.setField(service, "_userOu", userOu);
		
		// When
		List<User> result = service.getUsersFromProperty(User.class, propertyName, propertyValue);
		
		// Then
		assertEquals(0, result.size());
	}
	
	@Test
	public void testGetUsersByProperty_sAMAccountname_2Results_returnList() throws AdWrapperServiceException {
		
		// Given
		String propertyName = "prop";
		String propertyValue = "value";
		String userOu = "userOu";

		User expected1 = new User();
		User expected2 = new User();
		
		LdapTemplate ldapMock = Mockito.mock(LdapTemplate.class);
		Mockito.when(ldapMock.search(Mockito.eq("OU=userOu"), Mockito.eq("(&(objectClass=user)(prop=value))"), Mockito.any(AttributesMapper.class))).thenReturn(Arrays.asList(expected1, expected2));
		
		AdWrapperService service = new AdWrapperService();
		ReflectionTestUtils.setField(service, "ldapTemplate", ldapMock);
		ReflectionTestUtils.setField(service, "_userOu", userOu);
		
		// When
		List<User> result = service.getUsersFromProperty(User.class, propertyName, propertyValue);
		
		// Then
		assertEquals(2, result.size());
		assertEquals(expected1, result.get(0));
		assertEquals(expected2, result.get(1));
	}
	
	@Test
	public void testGetGroupsByProperty_sAMAccountname_noResult_returnEmptyList() throws AdWrapperServiceException {
		
		// Given
		String propertyName = "prop";
		String propertyValue = "value";
		String groupOu = "groupOu";
		
		LdapTemplate ldapMock = Mockito.mock(LdapTemplate.class);
		Mockito.when(ldapMock.search(Mockito.eq("OU=groupOu"), Mockito.eq("(&(objectClass=group)(prop=value))"), Mockito.any(AttributesMapper.class))).thenReturn(new ArrayList<Group>());
		
		AdWrapperService service = new AdWrapperService();
		ReflectionTestUtils.setField(service, "ldapTemplate", ldapMock);
		ReflectionTestUtils.setField(service, "_groupOu", groupOu);
		
		// When
		List<Group> result = service.getGroupsFromProperty(Group.class, propertyName, propertyValue);
		
		// Then
		assertEquals(0, result.size());
	}
	
	@Test
	public void testGetGroupsByProperty_sAMAccountname_2Results_returnList() throws AdWrapperServiceException {
		
		// Given
		String propertyName = "prop";
		String propertyValue = "value";
		String groupOu = "groupOu";

		Group expected1 = new Group();
		Group expected2 = new Group();
		
		LdapTemplate ldapMock = Mockito.mock(LdapTemplate.class);
		Mockito.when(ldapMock.search(Mockito.eq("OU=groupOu"), Mockito.eq("(&(objectClass=group)(prop=value))"), Mockito.any(AttributesMapper.class))).thenReturn(Arrays.asList(expected1, expected2));
		
		AdWrapperService service = new AdWrapperService();
		ReflectionTestUtils.setField(service, "ldapTemplate", ldapMock);
		ReflectionTestUtils.setField(service, "_groupOu", groupOu);
		
		// When
		List<Group> result = service.getGroupsFromProperty(Group.class, propertyName, propertyValue);
		
		// Then
		assertEquals(2, result.size());
		assertEquals(expected1, result.get(0));
		assertEquals(expected2, result.get(1));
	}
	
	@Test
	public void testgetGroupByDn_groupExists_returnIt() throws AdWrapperServiceException {
		
		// Given
		String dn = "CN=DSI-SIE-GL-BUDGET-RW,OU=New Datacentre,OU=DSI,OU=Z-Groupe,DC=site-mairie,DC=noumea,DC=nc";
		String effectiveDn = "CN=DSI-SIE-GL-BUDGET-RW,OU=New Datacentre,OU=DSI,OU=Z-Groupe";
		String baseDn = "dc=site-mairie,DC=noumea,DC=nc";
		LightGroup expected1 = new LightGroup();
		
		LdapTemplate ldapMock = Mockito.mock(LdapTemplate.class);
		Mockito.when(ldapMock.lookup(Mockito.eq(effectiveDn), Mockito.any(AttributesMapper.class))).thenReturn(expected1);
		
		AdWrapperService service = new AdWrapperService();
		ReflectionTestUtils.setField(service, "ldapTemplate", ldapMock);
		ReflectionTestUtils.setField(service, "_baseDn", baseDn);
		
		// When
		LightGroup result = service.getGroupByDn(dn);
		
		// Then
		assertEquals(expected1, result);
	}
	
	@Test
	public void testgetGroupByDn_groupDoesNotExist_returnNull() throws AdWrapperServiceException {
		
		// Given
		String dn = "CN=DSI-SIE-GL-BUDGET-RW,OU=New Datacentre,OU=DSI,OU=Z-Groupe,DC=site-mairie,DC=noumea,DC=nc";
		String effectiveDn = "CN=DSI-SIE-GL-BUDGET-RW,OU=New Datacentre,OU=DSI,OU=Z-Groupe";
		String baseDn = "dc=site-mairie,DC=noumea,DC=nc";
		
		LdapTemplate ldapMock = Mockito.mock(LdapTemplate.class);
		Mockito.when(ldapMock.lookup(Mockito.eq(effectiveDn), Mockito.any(AttributesMapper.class))).thenThrow(new NameNotFoundException(""));
		
		AdWrapperService service = new AdWrapperService();
		ReflectionTestUtils.setField(service, "ldapTemplate", ldapMock);
		ReflectionTestUtils.setField(service, "_baseDn", baseDn);
		
		// When
		LightGroup result = service.getGroupByDn(dn);
		
		// Then
		assertNull(result);
	}
	
}
