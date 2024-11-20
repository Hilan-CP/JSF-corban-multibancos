package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Bank;
import model.entity.Customer;
import model.entity.Employee;
import model.entity.Proposal;
import model.enumeration.ProposalStatus;
import service.BankService;
import service.CustomerService;
import service.EmployeeService;
import service.ProposalService;

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
	}
	
	public void findProposals() {
		proposalList = findByOption();
	}
	
	private List<Proposal> findByOption() {
		if(searchTerm.isBlank()) {
			return generalSearchByDate();
		}
		else if(searchOption.equals("proposal")) {
			return findById();
		}
		else if(searchOption.equals("employee")) {
			return employeeSearchByDate();
		}
		else if(searchOption.equals("bank")) {
			return bankSearchByDate();
		}
		else {
			return List.of();
		}
	}
	
	private List<Proposal> findById(){
		List<Proposal> list = List.of();
		Proposal result = proposalService.findById(Long.parseLong(searchTerm));
		if(result != null) {
			list = List.of(result);
		}
		return list;
	}
	
	private List<Proposal> generalSearchByDate(){
		return proposalService.findByDate(dateOption, beginDate, endDate);
	}
	
	private List<Proposal> employeeSearchByDate(){
		return proposalService.findByEmployeeAndDate(searchTerm, dateOption, beginDate, endDate);
	}
	
	private List<Proposal> bankSearchByDate(){
		return proposalService.findByBankAndDate(Long.parseLong(searchTerm), dateOption, beginDate, endDate);
	}
	
	public void findCustomer(){
		String cpf = proposal.getCustomer().getCpf();
		Customer result = customerService.findByCpf(cpf);
		if(result == null) {
			result = new Customer();
		}
		proposal.setCustomer(result);
	}
	
	public void save() {
		changeStatusIfPaid();
		proposalService.save(proposal);
	}
	
	public void cancelProposal() {
		proposal.setStatus(ProposalStatus.CANCELADA);
		proposal.setPayment(null);
		proposalService.save(proposal);
	}
	
	private void changeStatusIfPaid() {
		if(isPaid()) {
			proposal.setStatus(ProposalStatus.CONTRATADA);
		}
	}
	
	private boolean isPaid() {
		return proposal.getPayment() != null;
	}
	
	public void initializeCreate() {
		proposal = new Proposal();
		proposal.setStatus(ProposalStatus.SOLICITADA);
		proposal.setCustomer(new Customer());
		proposal.setGeneration(LocalDate.now());
		proposal.setEmployee(currentUser());
	}
	
	private Employee currentUser() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String username = context.getUserPrincipal().getName();
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
