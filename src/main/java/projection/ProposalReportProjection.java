package projection;

import java.time.LocalDate;

import model.entity.Employee;
import model.enumeration.ProposalStatus;

public class ProposalReportProjection {

	private Double value;
	private LocalDate generation;
	private LocalDate payment;
	private ProposalStatus status;
	private Employee employee;
	
	public ProposalReportProjection() {
	}
	
	public ProposalReportProjection(Double value, LocalDate generation, LocalDate payment, ProposalStatus status, Employee employee) {
		this.value = value;
		this.generation = generation;
		this.payment = payment;
		this.status = status;
		this.employee = employee;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "ProposalReport [value=" + value + ", generation=" + generation + ", payment=" + payment + ", status="
				+ status + ", employee=" + employee + "]\n";
	}
	
	
}
