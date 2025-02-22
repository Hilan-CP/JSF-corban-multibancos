package dto;

public class EmployeeDTO {

	private String cpf;
	private String name;
	private String teamName;
	
	public EmployeeDTO() {
	}

	public EmployeeDTO(String cpf, String name, String teamName) {
		this.cpf = cpf;
		this.name = name;
		this.teamName = teamName;
	}

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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
