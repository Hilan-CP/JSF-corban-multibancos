package bean;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Customer;
import service.CustomerService;

@Named
@ViewScoped
public class CustomerBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private CustomerService service;
	
	private Customer customer;
	private List<Customer> customerList;
	private String searchTerm;
	private String searchOption;
	
	public void find() {
		if(searchTerm.isBlank()) {
			customerList = service.findAll();
		}
		else {
			findByOption();
		}
	}
	
	private void findByOption() {
		switch(searchOption) {
			case "cpf":
				findByCpf();
				break;
			case "name":
				customerList = service.findByName(searchTerm);
				break;
			case "phone":
				customerList = service.findByPhone(searchTerm);
				break;
			default:
				break;
		}
	}
	
	private void findByCpf() {
		customerList = List.of();
		Customer result = service.findByCpf(searchTerm);
		if(result != null) {
			customerList = List.of(result);
		}
	}
	
	public void save() {
		service.save(customer);
	}
	
	public void initializeCreate() {
		customer = new Customer();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Customer> getCustomerList() {
		return customerList;
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
}
