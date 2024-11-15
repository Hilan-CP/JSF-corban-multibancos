package model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import validator.CPF;

@Entity
public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;

	@CPF
	@NotBlank(message = "CPF deve ser informado")
	@Id
	@Column(length = 11)
	private String cpf;
	
	@NotBlank(message = "nome deve ser informado")
	private String name;
	
	@Size(min = 11, max = 11, message = "telefone deve ser informado com DDD+número")
	@Column(length = 11)
	private String phone;
	
	@NotNull(message = "data de nascimento deve ser informada")
	private LocalDate birthDate;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
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
		Customer other = (Customer) obj;
		return Objects.equals(cpf, other.cpf);
	}
}
