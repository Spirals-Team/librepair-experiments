package nc.noumea.mairie.radi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.noumea.mairie.radi.dto.Group;
import nc.noumea.mairie.radi.dto.LightGroup;
import nc.noumea.mairie.radi.dto.LightUser;
import nc.noumea.mairie.radi.dto.User;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

public class AdServiceTest {

	@Test
	public void testgetSingleUserBySAMAccountname_noresult_returnNull() throws AdWrapperServiceException, AdServiceException {
		
		// Given
		String propertyValue = "rayni84";
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getUsersFromProperty(User.class, "sAMAccountName", propertyValue))
			.thenAnswer(new Answer<List<User>>() {
				@Override
				public List<User> answer(InvocationOnMock invocation) throws Throwable {
					return new ArrayList<User>();
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		User result = service.getSingleUserBySAMAccountname(propertyValue);
		
		// Then
		assertNull(result);
	}
	
	@Test
	public void testgetSingleUserBySAMAccountname_1result_returnIt() throws AdWrapperServiceException, AdServiceException {
		
		// Given
		String propertyValue = "rayni84";
		final User expected = new User();
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getUsersFromProperty(User.class, "sAMAccountName", propertyValue))
			.thenAnswer(new Answer<List<User>>() {
				@Override
				public List<User> answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList(expected);
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		User result = service.getSingleUserBySAMAccountname(propertyValue);
		
		// Then
		assertEquals(expected, result);
	}
	
	@Test
	public void testgetSingleUserBySAMAccountname_moreThan1result_throwException() throws AdWrapperServiceException, AdServiceException {
		
		// Given
		String propertyValue = "rayni84";
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getUsersFromProperty(Mockito.eq(User.class), Mockito.eq("sAMAccountName"), Mockito.eq(propertyValue)))
			.thenAnswer(new Answer<List<User>>() {
				@Override
				public List<User> answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList( new User(),  new User());
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		User result = service.getSingleUserBySAMAccountname(propertyValue);

		// Then
		assertNull(result);
	}
	
	@Test
	public void testgetSingleGroupBySAMAccountname_noresult_returnNull() throws AdWrapperServiceException, AdServiceException {
		
		// Given
		String propertyValue = "rayni84";
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getGroupsFromProperty(Group.class, "sAMAccountName", propertyValue))
			.thenAnswer(new Answer<List<Group>>() {
				@Override
				public List<Group> answer(InvocationOnMock invocation) throws Throwable {
					return new ArrayList<Group>();
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		Group result = service.getSingleGroupBySAMAccountname(propertyValue);
		
		// Then
		assertNull(result);
	}
	
	@Test
	public void testgetSingleGroupBySAMAccountname_1result_returnIt() throws AdWrapperServiceException, AdServiceException {
		
		// Given
		String propertyValue = "rayni84";
		final Group expected = new Group();
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getGroupsFromProperty(Group.class, "sAMAccountName", propertyValue))
			.thenAnswer(new Answer<List<Group>>() {
				@Override
				public List<Group> answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList(expected);
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		Group result = service.getSingleGroupBySAMAccountname(propertyValue);
		
		// Then
		assertEquals(expected, result);
	}
	
	@Test
	public void testgetSingleGroupBySAMAccountname_moreThan1result_throwException() throws AdWrapperServiceException, AdServiceException {
		
		// Given
		String propertyValue = "rayni84";
		final Group expected1 = new Group();
		final Group expected2 = new Group();
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getGroupsFromProperty(Group.class, "sAMAccountName", propertyValue))
			.thenAnswer(new Answer<List<Group>>() {
				@Override
				public List<Group> answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList(expected1, expected2);
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		Group result = service.getSingleGroupBySAMAccountname(propertyValue);
		
		assertNull(result);
	}
	
	@Test
	public void testgetSingleGroupUsersBySAMAccountname_groupExists_returnListOfUsers() throws AdWrapperServiceException {
		
		// Given
		String groupSAMAccountName = "sama";
		final Group expectedGroup = new Group();
		String groupDistinguishedName = "dddddddd";
		expectedGroup.setDistinguishedName(groupDistinguishedName);
		
		LightUser expectedUser1 = new LightUser();
		LightUser expectedUser2 = new LightUser();
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getGroupsFromProperty(Group.class, "sAMAccountName", groupSAMAccountName))
			.thenAnswer(new Answer<List<Group>>() {
				@Override
				public List<Group> answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList(expectedGroup);
				}
			});

		Mockito.when(ldapMock.getUsersFromProperty(LightUser.class, "memberof:1.2.840.113556.1.4.1941:", groupDistinguishedName)).thenReturn(Arrays.asList(expectedUser1, expectedUser2));
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		List<LightUser> result = service.getSingleGroupUsersBySAMAccountname(groupSAMAccountName);
		
		// Then
		assertEquals(2, result.size());
		assertEquals(expectedUser1, result.get(0));
		assertEquals(expectedUser2, result.get(1));
	}
	
	@Test
	public void testgetSingleGroupUsersBySAMAccountname_groupDoesNotExist_returnNull() throws AdWrapperServiceException {
		
		// Given
		String groupSAMAccountName = "sama";
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getGroupsFromProperty(Group.class, "sAMAccountName", groupSAMAccountName))
			.thenAnswer(new Answer<List<Group>>() {
				@Override
				public List<Group> answer(InvocationOnMock invocation) throws Throwable {
					return new ArrayList<Group>();
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		List<LightUser> result = service.getSingleGroupUsersBySAMAccountname(groupSAMAccountName);
		
		// Then
		assertNull(result);
	}
	
	@Test
	public void testsearchUsers_noResult_returnEmptyList() throws AdWrapperServiceException {
		
		// Given
		String searchProperty = "prop";
		String searchFilter = "value";
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getUsersFromProperty(LightUser.class, searchProperty, searchFilter)).thenReturn(new ArrayList<LightUser>());
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		List<LightUser> result = service.searchUsers(searchProperty, searchFilter);

		// Then
		assertEquals(0, result.size());
	}
	
	@Test
	public void testsearchUsers_3Results_returnListOf3() throws AdWrapperServiceException {
		
		// Given
		String searchProperty = "prop";
		String searchFilter = "value";
		LightUser exp1 = new LightUser();
		LightUser exp2 = new LightUser();
		LightUser exp3 = new LightUser();
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getUsersFromProperty(LightUser.class, searchProperty, searchFilter)).thenReturn(Arrays.asList(exp1, exp2, exp3));
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		List<LightUser> result = service.searchUsers(searchProperty, searchFilter);

		// Then
		assertEquals(3, result.size());
		assertEquals(exp1, result.get(0));
		assertEquals(exp2, result.get(1));
		assertEquals(exp3, result.get(2));
	}
	
	@Test
	public void testsearchGroups_noResult_returnEmptyList() throws AdWrapperServiceException {
		
		// Given
		String searchProperty = "prop";
		String searchFilter = "value";
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getGroupsFromProperty(LightGroup.class, searchProperty, searchFilter))
			.thenAnswer(new Answer<List<LightGroup>>() {
				@Override
				public List<LightGroup> answer(InvocationOnMock invocation) throws Throwable {
					return new ArrayList<LightGroup>();
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		List<LightGroup> result = service.searchGroups(searchProperty, searchFilter);

		// Then
		assertEquals(0, result.size());
	}
	
	@Test
	public void testsearchGroups_3Results_returnListOf3() throws AdWrapperServiceException {
		
		// Given
		String searchProperty = "prop";
		String searchFilter = "value";
		final LightGroup exp1 = new LightGroup();
		final LightGroup exp2 = new LightGroup();
		final LightGroup exp3 = new LightGroup();
		
		IAdWrapperService ldapMock = Mockito.mock(IAdWrapperService.class);
		Mockito.when(ldapMock.getGroupsFromProperty(LightGroup.class, searchProperty, searchFilter))
			.thenAnswer(new Answer<List<LightGroup>>() {
				@Override
				public List<LightGroup> answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList(exp1, exp2, exp3);
				}
			});
		
		AdService service = new AdService();
		ReflectionTestUtils.setField(service, "ldapWrapperService", ldapMock);
		
		// When
		List<LightGroup> result = service.searchGroups(searchProperty, searchFilter);

		// Then
		assertEquals(3, result.size());
		assertEquals(exp1, result.get(0));
		assertEquals(exp2, result.get(1));
		assertEquals(exp3, result.get(2));
	}
}
