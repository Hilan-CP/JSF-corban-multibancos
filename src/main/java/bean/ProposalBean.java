package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Bank;
import model.entity.Customer;
import model.entity.Employee;
import model.entity.Proposal;
import model.enumeration.ProposalStatus;
import security.LoggedUserBean;
import service.BankService;
import service.CustomerService;
import service.EmployeeService;
import service.ProposalService;
import util.Message;

@Named
@ViewScoped
public class ProposalBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ProposalService proposalService;
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private BankService bankService;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private LoggedUserBean loggedUser;
	
	private Proposal proposal;
	private List<Proposal> proposalList;
	private List<Employee> employeeList;
	private List<Bank> bankList;
	private String searchTerm;
	private String searchOption;
	private String dateOption;
	private LocalDate beginDate;
	private LocalDate endDate;
	
	@PostConstruct
	public void init() {
		employeeList = employeeService.findAll();
		bankList = bankService.findAll();
		beginDate = LocalDate.now();
		endDate = LocalDate.now();
	}
	
	public void findProposals() {
		try {
			proposalList = proposalService.findByOptionAndRole(searchTerm, searchOption, dateOption, beginDate, endDate);
		}
		catch(NumberFormatException e) {
			Message.error("Código de busca inválido");
		}
	}
	
	public void findCustomer(){
		String cpf = proposal.getCustomer().getCpf();
		Customer result = customerService.findByCpfOrDefault(cpf);
		proposal.setCustomer(result);
	}
	
	public void save() {
		proposalService.save(proposal);
		Message.info("Proposta cadastrada com sucesso");
	}
	
	public void cancelProposal() {
		proposalService.cancelProposal(proposal);
		Message.info("Proposta cancelada");
	}
	
	public void initializeCreate() {
		proposal = new Proposal();
		proposal.setStatus(ProposalStatus.GERADA);
		proposal.setCustomer(new Customer());
		proposal.setGeneration(LocalDate.now());
		proposal.setEmployee(getLoggedUser());
	}
	
	private Employee getLoggedUser() {
		String username = loggedUser.getUsername();
		for(Employee employee : employeeList) {
			if(employee.getCpf().equals(username)) {
				return employee;
			}
		}
		return new Employee();
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public List<Proposal> getProposalList() {
		return proposalList;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public List<Bank> getBankList() {
		return bankList;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getDateOption() {
		return dateOption;
	}

	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
