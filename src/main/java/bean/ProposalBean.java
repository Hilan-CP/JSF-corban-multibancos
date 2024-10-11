package bean;

import java.io.Serializable;
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
	
	public void findProposals() {
		proposalList = proposalService.findAll();
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
}
