package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

import java.util.Objects;

/**
 * @author Victor CHEN (Kantoki), Samuel COHEN Contract 2 - Version 2.0.2 Last
 *         Update : 22/03/2018 This class stores a teacher's preferences for a
 *         course: it allows him to indicate a preference for CM, TD, CMTD, TP
 *         (A, B, C or Absent, for each).
 */

public class CoursePref {

	/**
	 * Course is never <code> null </code>
	 */
	private Course course;

	private String teacher;
	private Choice cmChoice;
	private Choice tdChoice;
	private Choice cmtdChoice;
	private Choice tpChoice;

	/**
	 * @param course
	 *            : the Course to insert the teacher's preferences. course not
	 *            <code> null </code>
	 * @param teacher
	 *            : the teacher of the Course. teacher not <code> null </code>
	 */
	public CoursePref(Course course, String teacher) {
		this.course = Objects.requireNonNull(course);
		this.teacher = Objects.requireNonNull(teacher);
		this.cmChoice = Choice.ABSENT;
		this.tdChoice = Choice.ABSENT;
		this.cmtdChoice = Choice.ABSENT;
		this.tpChoice = Choice.ABSENT;
	}

	@Override
	public String toString() {
		return "Preferences for the course " + course.getName() + ", whose teacher is " + teacher + ", for CM : "
				+ cmChoice.toString() + " ; TD : " + tdChoice.toString() + " ; CMTD : " + cmtdChoice.toString()
				+ " ; TP : " + tpChoice.toString() + ".";
	}

	public Course getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            not <code> null </code>
	 */
	public void setCourse(Course course) {
		this.course = Objects.requireNonNull(course);
	}

	public String getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher
	 *            not <code> null </code>
	 */
	public void setTeacher(String teacher) {
		this.teacher = Objects.requireNonNull(teacher);
	}

	public Choice getCmChoice() {
		return cmChoice;
	}

	/**
	 * @param cmChoice
	 *            not <code> null </code>
	 */
	public void setCmChoice(Choice cmChoice) {
		this.cmChoice = Objects.requireNonNull(cmChoice);
	}

	public Choice getTdChoice() {
		return tdChoice;
	}

	/**
	 * @param tdChoice
	 *            not <code> null </code>
	 */
	public void setTdChoice(Choice tdChoice) {
		this.tdChoice = Objects.requireNonNull(tdChoice);
	}

	public Choice getCmtdChoice() {
		return cmtdChoice;
	}

	/**
	 * @param cmtdChoice
	 *            not <code> null </code>
	 */
	public void setCmtdChoice(Choice cmtdChoice) {
		this.cmtdChoice = Objects.requireNonNull(cmtdChoice);
	}

	public Choice getTpChoice() {
		return tpChoice;
	}

	/**
	 * @param tpChoice
	 *            not <code> null </code>
	 */
	public void setTpChoice(Choice tpChoice) {
		this.tpChoice = Objects.requireNonNull(tpChoice);
	}

}