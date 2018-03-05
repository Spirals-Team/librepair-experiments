package com.oxygenxml.git.service;

/**
 * Stores information about the branch;
 * 
 * @author Beniamin Savu
 *
 */
public class BranchInfo {

	/**
	 * The branch name
	 */
	private String branchName;

	/**
	 * Used to store the first 6 letters from the commit id. Only used on a
	 * detached HEAD
	 */
	private String shortBranchName;

	/**
	 * <code>true</code> if the branch is detached <code>false</code> otherwise
	 */
	private boolean isDetached;

	public BranchInfo() {

	}

	public BranchInfo(String branchName, boolean isDetached) {
		this.branchName = branchName;
		this.isDetached = isDetached;

	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public boolean isDetached() {
		return isDetached;
	}

	public void setDetached(boolean isDetached) {
		this.isDetached = isDetached;
	}

	public String getShortBranchName() {
		return shortBranchName;
	}

	public void setShortBranchName(String shortBranchName) {
		this.shortBranchName = shortBranchName;
	}

}
