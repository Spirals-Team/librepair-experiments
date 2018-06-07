// generated at 2018-06-04 06:56.56 from  main/resources/templates/FluentPojoClass.vm

package net.atf4j.generated;

import java.util.Date;

public class FluentPojo {

	// $field.toCode()
	private String string;

	// $field.toCode()
	private Date date;

	// $field.toCode()
	private Boolean bool;


	public FluentPojo() {
		super();
	}

	// ClassField [accessModifier=DEFAULT, visibility=, fieldType=STRING, type=String, name=string, initialValue=null]
	public FluentPojo setstring(final String string) {
		this.string = string;
		return this;
	}

	public String getstring() {	
		return this.string;
 	}
 	
	// ClassField [accessModifier=DEFAULT, visibility=, fieldType=DATE, type=Date, name=date, initialValue=null]
	public FluentPojo setdate(final Date date) {
		this.date = date;
		return this;
	}

	public Date getdate() {	
		return this.date;
 	}
 	
	// ClassField [accessModifier=DEFAULT, visibility=, fieldType=BOOLEAN, type=Boolean, name=bool, initialValue=null]
	public FluentPojo setbool(final Boolean bool) {
		this.bool = bool;
		return this;
	}

	public Boolean getbool() {	
		return this.bool;
 	}
 	
}
