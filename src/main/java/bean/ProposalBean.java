package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Bank;
import model.entity.Customer;
import model.entity.Employee;
import model.entity.Proposal;
import model.enumeration.ProposalStatus;
import service.BankService;
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
	
	private Proposal proposal;
	private List<Proposal> proposalList;
	private List<Employee> employeeList;
	private List<Bank> bankList;
	private String searchTerm;
	private String searchOption;
	private String dateOption;
	private Date beginDate;
	private Date endDate;
	
	public void findProposals() {
		proposalList = findByOption();
	}
	
	private List<Proposal> findByOption() {
		if(searchTerm.equals("") && dateOption.equals("generation")) {
			return proposalService.findByGenerationDate(beginDate, endDate);
		}
		if(searchTerm.equals("") && dateOption.equals("payment")) {
			return proposalService.findByPaymentDate(beginDate, endDate);
		}
		if(searchOption.equals("Proposal")) {
			return findById();
		}
		if(searchOption.equals("Employee") && dateOption.equals("generation")) {
			return proposalService.findByEmployeeNameAndGenerationDate(searchTerm, beginDate, endDate);
		}
		if(searchOption.equals("Employee") && dateOption.equals("payment")) {
			return proposalService.findByEmployeeNameAndPaymentDate(searchTerm, beginDate, endDate);
		}
		if(searchOption.equals("Bank") && dateOption.equals("generation")) {
			return proposalService.findByBankCodeAndGenerationDate(Long.parseLong(searchTerm), beginDate, endDate);
		}
		if(searchOption.equals("Bank") && dateOption.equals("payment")) {
			return proposalService.findByBankCodeAndPaymentDate(Long.parseLong(searchTerm), beginDate, endDate);
		}
		return null;
	}
	
	private List<Proposal> findById(){
		List<Proposal> result = List.of();
		Proposal p = proposalService.findById(Long.parseLong(searchTerm));
		if(p != null) {
			result = List.of(p);
		}
		return result;
	}
	
	public void save() {
		proposalService.save(proposal);
	}
	
	public void initializeForm() {
		initializeProposal();
		findEmployees();
		findBanks();
	}
	
	private void initializeProposal() {
		if (proposal == null) {
			proposal = new Proposal();
			proposal.setStatus(ProposalStatus.SOLICITADA);
			proposal.setCustomer(new Customer());
		}
	}
	
	private void findEmployees() {
		if(employeeList == null) {
			employeeList = employeeService.findAll();
		}
	}
	
	private void findBanks() {
		if(bankList == null) {
			bankList = bankService.findAll();
		}
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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
