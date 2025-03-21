package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;

import bean.model.ProposalLazyModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
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
	
	@Inject
	private ProposalLazyModel proposalLazyModel;
	
	private Proposal proposal;
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
		searchTerm = "";
		dateOption = "generation";
		proposalLazyModel.setSearchTerm(searchTerm);
		proposalLazyModel.setSearchOption(searchOption);
		proposalLazyModel.setDateOption(dateOption);
		proposalLazyModel.setBeginDate(beginDate);
		proposalLazyModel.setEndDate(endDate);
	}
	
	public void findProposals() {
		try {
			checkSearchOptions();
			resetDataTable();
			proposalLazyModel.setSearchTerm(searchTerm);
			proposalLazyModel.setSearchOption(searchOption);
			proposalLazyModel.setDateOption(dateOption);
			proposalLazyModel.setBeginDate(beginDate);
			proposalLazyModel.setEndDate(endDate);
		}
		catch(NumberFormatException e) {
			Message.error("Código de busca inválido");
		}
	}
	
	private void checkSearchOptions() {
		if(!searchTerm.isBlank()) {
			if(searchOption.equals("proposal") || searchOption.equals("bank")) {
				Long.parseLong(searchTerm);
			}
		}
	}
	
	private void resetDataTable() {
		FacesContext context = FacesContext.getCurrentInstance();
		DataTable table = (DataTable) context.getViewRoot().findComponent("contentForm:proposalTable");
		table.reset();
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
		proposal.setEmployee(loggedUser.getLoggedUser());
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public LazyDataModel<Proposal> getProposalLazyModel() {
		return proposalLazyModel;
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
