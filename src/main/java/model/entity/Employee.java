package model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import model.enumeration.EmployeeType;
import validator.CPF;

@Entity
public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;

	@CPF
	@NotBlank(message = "CPF deve ser informado")
	@Id
	@Column(length = 11)
	private String cpf;
	
	@NotBlank(message = "nome deve ser informado")
	private String name;
	
	private String password;
	
	@NotNull(message = "tipo de funcionário de ser informado")
	@Enumerated(EnumType.STRING)
	private EmployeeType type;
	
	@ManyToOne
	private Team team;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(cpf, other.cpf);
	}
}
