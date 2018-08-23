package java_programs;


public class Node {
	private java.lang.String value;

	private java.util.ArrayList<java_programs.Node> successors;

	private java.util.ArrayList<java_programs.Node> predecessors;

	private java_programs.Node successor;

	public Node() {
		this.successor = null;
		this.successors = new java.util.ArrayList<java_programs.Node>();
		this.predecessors = new java.util.ArrayList<java_programs.Node>();
		this.value = null;
	}

	public Node(java.lang.String value) {
		this.value = value;
		this.successor = null;
		this.successors = new java.util.ArrayList<>();
		this.predecessors = new java.util.ArrayList<>();
	}

	public Node(java.lang.String value, java_programs.Node successor) {
		this.value = value;
		this.successor = successor;
	}

	public Node(java.lang.String value, java.util.ArrayList<java_programs.Node> successors) {
		this.value = value;
		this.successors = successors;
	}

	public Node(java.lang.String value, java.util.ArrayList<java_programs.Node> predecessors, java.util.ArrayList<java_programs.Node> successors) {
		this.value = value;
		this.predecessors = predecessors;
		this.successors = successors;
	}

	public java.lang.String getValue() {
		return value;
	}

	public void setSuccessor(java_programs.Node successor) {
		this.successor = successor;
	}

	public void setSuccessors(java.util.ArrayList<java_programs.Node> successors) {
		this.successors = successors;
	}

	public void setPredecessors(java.util.ArrayList<java_programs.Node> predecessors) {
		this.predecessors = predecessors;
	}

	public java_programs.Node getSuccessor() {
		return successor;
	}

	public java.util.ArrayList<java_programs.Node> getSuccessors() {
		return successors;
	}

	public java.util.ArrayList<java_programs.Node> getPredecessors() {
		return predecessors;
	}
}

