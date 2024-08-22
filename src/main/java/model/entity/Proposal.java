package model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import model.enumeration.ProposalStatus;

@Entity
public class Proposal implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private Double value;
	private Date generation;
	private Date payment;
	
	@Enumerated(EnumType.STRING)
	private ProposalStatus status;
	
	@ManyToOne
	private Employee employee;
	
	@ManyToOne
	private Bank bank;
	
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

	public Date getGeneration() {
		return generation;
	}

	public void setGeneration(Date generation) {
		this.generation = generation;
	}

	public Date getPayment() {
		return payment;
	}

	public void setPayment(Date payment) {
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
