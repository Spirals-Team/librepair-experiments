package br.net.itech.dtos;

public class DiagnosisDto {
	private String id;

	private String description;

	private String diagnosticStatus;

	public DiagnosisDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiagnosticStatus() {
		return diagnosticStatus;
	}

	public void setDiagnosticStatus(String diagnosisStatus) {
		this.diagnosticStatus = diagnosisStatus;
	}

	@Override
	public String toString() {
		return "DiagnosisDto [id=" + id + ", description=" + description + ", diagnosisStatus=" + diagnosticStatus
				+ "]";
	}
}