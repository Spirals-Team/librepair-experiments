package org.simpleflatmapper.test.beans;

import org.simpleflatmapper.test.beans.DbObject.Type;

import java.util.Date;

public class DbPartialFinalObject {
	private long id;
	private String name;
	private final String email;
	private Date creationTime;
	
	private final Type typeOrdinal;
	private Type typeName;
	
	public DbPartialFinalObject(String email,  Type typeOrdinal) {
		this.email = email;
		this.typeOrdinal = typeOrdinal;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public void setTypeName(Type typeName) {
		this.typeName = typeName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public Type getTypeOrdinal() {
		return typeOrdinal;
	}
	public Type getTypeName() {
		return typeName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DbPartialFinalObject that = (DbPartialFinalObject) o;

		if (id != that.id) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		if (creationTime != null ? !creationTime.equals(that.creationTime) : that.creationTime != null) return false;
		if (typeOrdinal != that.typeOrdinal) return false;
		return typeName == that.typeName;

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
		result = 31 * result + (typeOrdinal != null ? typeOrdinal.hashCode() : 0);
		result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
		return result;
	}

	public static DbPartialFinalObject newInstance() {
		DbObject dbObject = DbObject.newInstance();
		DbPartialFinalObject partialFinalObject = new DbPartialFinalObject(dbObject.getEmail(), dbObject.getTypeOrdinal());

		partialFinalObject.setId(dbObject.getId());
		partialFinalObject.setName(dbObject.getName());
		partialFinalObject.setCreationTime(dbObject.getCreationTime());
		partialFinalObject.setTypeName(dbObject.getTypeName());

		return partialFinalObject;
	}
}
