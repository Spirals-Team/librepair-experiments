package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

import java.util.Objects;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Samuel COHEN (samuelcohen75)
 * @contributors Louis Fontaine, Victor CHEN
 * 
 *               <p>
 *               <b> Contract 1 This class makes it possible to store a course :
 *               Name, Peak Code, CM hours, TD hours, CMTD hours, TP hours, CM
 *               groups / TD hours / etc., and the year of studies in which the
 *               course is inserted package
 *               io.github.oliviercailloux.y2018.teach_spreadsheets.java; (L3
 *               Computing, M1 Mathematics, L3 learning,...) </b>
 *               </p>
 */
public class Course {
	private String name = "", apogeeCode = "", yearOfStud = "";
	private double cmH = 0.0, tdH = 0.0, cmtdH = 0.0, tpH = 0.0, cmtpH = 0.0;
	private String grpNbr = "";
	private String teachers = "", supervisor = "";

	public Course(String name, String apogeeCode, String yearOfStud) {
		this.name = Objects.requireNonNull(name);
		this.apogeeCode = Objects.requireNonNull(apogeeCode);
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
	}

	public Course() {

	}

	public void set(int index, String data) {
		assert data != null;

		switch (index) {
		case 0:
			setName(data);
			break;
		case 1:
			setapogeeCode(data);
			break;
		case 2:
			setYearOfStud(data);
			break;
		case 3:
			setCM_Hour(Double.parseDouble(data));
			break;
		case 4:
			setTD_Hour(Double.parseDouble(data));
			break;
		case 5:
			setCMTD_Hour(Double.parseDouble(data));
			break;
		case 6:
			setTP_Hour(Double.parseDouble(data));
			break;
		case 7:
			setGrpNbr(data);
			break;

		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {
		return "Course [name=" + name + ", apogeeCode=" + apogeeCode + ", yearOfStud=" + yearOfStud + ", cmH=" + cmH
				+ ", tdH=" + tdH + ", cmtdH=" + cmtdH + ", tpH=" + tpH + ", cmtpH=" + cmtpH + ", grpNbr=" + grpNbr
				+ ", teachers=" + teachers + ", supervisor=" + supervisor + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public String getapogeeCode() {
		return apogeeCode;
	}

	public void setapogeeCode(String apogeeCode) {
		this.apogeeCode = Objects.requireNonNull(apogeeCode);
	}

	public String getYearOfStud() {
		return yearOfStud;
	}

	public void setYearOfStud(String yearOfStud) {
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
	}

	public double getCM_Hour() {
		return cmH;
	}

	public void setCM_Hour(double cmH) {
		if (cmH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmH = cmH;
	}

	public double getTD_Hour() {
		return tdH;
	}

	public void setTD_Hour(double tdH) {
		if (tdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.tdH = tdH;
	}

	public double getCMTD_Hour() {
		return cmtdH;
	}

	public void setCMTD_Hour(double cmtdH) {
		if (cmtdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmtdH = cmtdH;
	}

	public double getTP_Hour() {
		return tpH;
	}

	public void setTP_Hour(double tpH) {
		if (tpH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.tpH = tpH;
	}

	public double getCMTP_Hour() {
		return cmtpH;
	}

	public void setCMTP_Hour(double cmtpH) {
		if (cmtpH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmtpH = cmtpH;
	}

	public String getGrpNbr() {
		return grpNbr;
	}

	public void setGrpNbr(String grpNbr) {
		this.grpNbr = Objects.requireNonNull(grpNbr);
	}

	public String getTeachers() {
		return teachers;
	}

	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

}
