package dto;

import java.time.LocalDate;

import model.enumeration.ProposalStatus;

public class ProposalReportDTO {

	private Double value;
	private LocalDate generation;
	private LocalDate payment;
	private ProposalStatus status;
	private String employeeCpf;
	private String employeeName;
	private String teamName;
	
	public ProposalReportDTO() {
	}
	
	public ProposalReportDTO(Double value, LocalDate generation, LocalDate payment, ProposalStatus status,
			String employeeCpf, String employeeName, String teamName) {
		this.value = value;
		this.generation = generation;
		this.payment = payment;
		this.status = status;
		this.employeeCpf = employeeCpf;
		this.employeeName = employeeName;
		this.teamName = teamName;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public LocalDate getGeneration() {
		return generation;
	}

	public void setGeneration(LocalDate generation) {
		this.generation = generation;
	}

	public LocalDate getPayment() {
		return payment;
	}

	public void setPayment(LocalDate payment) {
		this.payment = payment;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}

	public String getEmployeeCpf() {
		return employeeCpf;
	}

	public void setEmployeeCpf(String employeeCpf) {
		this.employeeCpf = employeeCpf;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		return "ProposalReportProjection [value=" + value + ", generation=" + generation + ", payment=" + payment
				+ ", status=" + status + ", employeeCpf=" + employeeCpf + ", employeeName=" + employeeName
				+ ", teamName=" + teamName + "]";
	}
}
