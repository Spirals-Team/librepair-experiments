package nc.noumea.mairie.radi.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
public class User extends LightUser {

	private Date badPasswordTime;
	private String badPwdCount;
	private String businessCategory;
	private String c;
	private String cn;
	private String company;
	private String countryCode;
	private String department;
	private String departmentNumber;
	private String description;
	private String directReports;
	private String displayName;
	private String division;
	private String employeeID;
	private String employeeType;
	private Date accountExpires;
	private String extensionAttribute1;
	private String extensionAttribute10;
	private String extensionAttribute11;
	private String extensionAttribute12;
	private String extensionAttribute2;
	private String extensionAttribute3;
	private String extensionAttribute4;
	private String extensionAttribute5;
	private String facsimileTelephoneNumber;
	private String givenName;
	private String info;
	private String initials;
	private String internationalISDNNumber;
	private String ipPhone;
	private String l;
	private Date lastLogon;
	private Date lastLogonTimestamp;
	private String lockoutTime;
	private String logonCount;
	private String mailNickname;
	private String manager;
	private String memberOf;
	private String mobile;
	private String name;
	private String objectCategory;
	private List<String> objectClass;
	private String objectGUID;
	private String objectSid;
	private String otherTelephone;
	private String personalTitle;
	private String postalAddress;
	private String postalCode;
	private Date pwdLastSet;
	private String sAMAccountType;
	private String secretary;
	private String showInAddressBook;
	private String sn;
	private String st;
	private String street;
	private String streetAddress;
	private String telephoneAssistant;
	private String telephoneNumber;
	private String title;
	private String url;
	private String userAccountControl;
	private String userPrincipalName;
	private String uSNChanged;
	private String uSNCreated;
	private Date whenChanged;
	private Date whenCreated;
	private String wWWHomePage;

	@JsonIgnore
	private List<String> groupNames;

	public User() {
		groupNames = new ArrayList<String>();
		objectClass = new ArrayList<String>();
	}

	@XmlTransient
	public List<String> getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(List<String> groups) {
		this.groupNames = groups;
	}

	public Date getBadPasswordTime() {
		return badPasswordTime;
	}

	public void setBadPasswordTime(Date badPasswordTime) {
		this.badPasswordTime = badPasswordTime;
	}

	public String getBadPwdCount() {
		return badPwdCount;
	}

	public void setBadPwdCount(String badPwdCount) {
		this.badPwdCount = badPwdCount;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirectReports() {
		return directReports;
	}

	public void setDirectReports(String directReports) {
		this.directReports = directReports;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public Date getAccountExpires() {
		return accountExpires;
	}

	public void setAccountExpires(Date accountExpires) {
		this.accountExpires = accountExpires;
	}

	public String getExtensionAttribute1() {
		return extensionAttribute1;
	}

	public void setExtensionAttribute1(String extensionAttribute1) {
		this.extensionAttribute1 = extensionAttribute1;
	}

	public String getExtensionAttribute10() {
		return extensionAttribute10;
	}

	public void setExtensionAttribute10(String extensionAttribute10) {
		this.extensionAttribute10 = extensionAttribute10;
	}

	public String getExtensionAttribute11() {
		return extensionAttribute11;
	}

	public void setExtensionAttribute11(String extensionAttribute11) {
		this.extensionAttribute11 = extensionAttribute11;
	}

	public String getExtensionAttribute12() {
		return extensionAttribute12;
	}

	public void setExtensionAttribute12(String extensionAttribute12) {
		this.extensionAttribute12 = extensionAttribute12;
	}

	public String getExtensionAttribute2() {
		return extensionAttribute2;
	}

	public void setExtensionAttribute2(String extensionAttribute2) {
		this.extensionAttribute2 = extensionAttribute2;
	}

	public String getExtensionAttribute3() {
		return extensionAttribute3;
	}

	public void setExtensionAttribute3(String extensionAttribute3) {
		this.extensionAttribute3 = extensionAttribute3;
	}

	public String getExtensionAttribute4() {
		return extensionAttribute4;
	}

	public void setExtensionAttribute4(String extensionAttribute4) {
		this.extensionAttribute4 = extensionAttribute4;
	}

	public String getExtensionAttribute5() {
		return extensionAttribute5;
	}

	public void setExtensionAttribute5(String extensionAttribute5) {
		this.extensionAttribute5 = extensionAttribute5;
	}

	public String getFacsimileTelephoneNumber() {
		return facsimileTelephoneNumber;
	}

	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber) {
		this.facsimileTelephoneNumber = facsimileTelephoneNumber;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getInternationalISDNNumber() {
		return internationalISDNNumber;
	}

	public void setInternationalISDNNumber(String internationalISDNNumber) {
		this.internationalISDNNumber = internationalISDNNumber;
	}

	public String getIpPhone() {
		return ipPhone;
	}

	public void setIpPhone(String ipPhone) {
		this.ipPhone = ipPhone;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public Date getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(Date lastLogon) {
		this.lastLogon = lastLogon;
	}

	public Date getLastLogonTimestamp() {
		return lastLogonTimestamp;
	}

	public void setLastLogonTimestamp(Date lastLogonTimestamp) {
		this.lastLogonTimestamp = lastLogonTimestamp;
	}

	public String getLockoutTime() {
		return lockoutTime;
	}

	public void setLockoutTime(String lockoutTime) {
		this.lockoutTime = lockoutTime;
	}

	public String getLogonCount() {
		return logonCount;
	}

	public void setLogonCount(String logonCount) {
		this.logonCount = logonCount;
	}

	public String getMailNickname() {
		return mailNickname;
	}

	public void setMailNickname(String mailNickname) {
		this.mailNickname = mailNickname;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(String memberOf) {
		this.memberOf = memberOf;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public List<String> getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(List<String> objectClass) {
		this.objectClass = objectClass;
	}

	public String getObjectGUID() {
		return objectGUID;
	}

	public void setObjectGUID(String objectGUID) {
		this.objectGUID = objectGUID;
	}

	public String getObjectSid() {
		return objectSid;
	}

	public void setObjectSid(String objectSid) {
		this.objectSid = objectSid;
	}

	public String getOtherTelephone() {
		return otherTelephone;
	}

	public void setOtherTelephone(String otherTelephone) {
		this.otherTelephone = otherTelephone;
	}

	public String getPersonalTitle() {
		return personalTitle;
	}

	public void setPersonalTitle(String personalTitle) {
		this.personalTitle = personalTitle;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Date getPwdLastSet() {
		return pwdLastSet;
	}

	public void setPwdLastSet(Date pwdLastSet) {
		this.pwdLastSet = pwdLastSet;
	}

	public String getsAMAccountType() {
		return sAMAccountType;
	}

	public void setsAMAccountType(String sAMAccountType) {
		this.sAMAccountType = sAMAccountType;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getShowInAddressBook() {
		return showInAddressBook;
	}

	public void setShowInAddressBook(String showInAddressBook) {
		this.showInAddressBook = showInAddressBook;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getTelephoneAssistant() {
		return telephoneAssistant;
	}

	public void setTelephoneAssistant(String telephoneAssistant) {
		this.telephoneAssistant = telephoneAssistant;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserAccountControl() {
		return userAccountControl;
	}

	public void setUserAccountControl(String userAccountControl) {
		this.userAccountControl = userAccountControl;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	public String getuSNChanged() {
		return uSNChanged;
	}

	public void setuSNChanged(String uSNChanged) {
		this.uSNChanged = uSNChanged;
	}

	public String getuSNCreated() {
		return uSNCreated;
	}

	public void setuSNCreated(String uSNCreated) {
		this.uSNCreated = uSNCreated;
	}

	public Date getWhenChanged() {
		return whenChanged;
	}

	public void setWhenChanged(Date whenChanged) {
		this.whenChanged = whenChanged;
	}

	public Date getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(Date whenCreated) {
		this.whenCreated = whenCreated;
	}

	public String getwWWHomePage() {
		return wWWHomePage;
	}

	public void setwWWHomePage(String wWWHomePage) {
		this.wWWHomePage = wWWHomePage;
	}	
}
