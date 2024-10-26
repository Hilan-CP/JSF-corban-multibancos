package model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import model.enumeration.ProposalStatus;

@Entity
public class Proposal implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull(message = "código da proposta deve ser informado")
	@Id
	private Long id;
	
	@NotNull(message = "valor deve ser informado")
	private Double value;
	
	@NotNull(message = "data de geração deve ser informada")
	private LocalDate generation;
	
	private LocalDate payment;
	
	@NotNull(message = "proposta deve ter um status")
	@Enumerated(EnumType.STRING)
	private ProposalStatus status;
	
	@NotNull(message = "proposta deve ter um funcionário responsável")
	@ManyToOne
	private Employee employee;
	
	@NotNull(message = "proposta deve pertencer a um banco")
	@ManyToOne
	private Bank bank;
	
	@NotNull(message = "proposta deve ser feita para algum cliente")
	@ManyToOne
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposal other = (Proposal) obj;
		return Objects.equals(id, other.id);
	}
}
