package bean;

import java.io.Serializable;

import org.primefaces.model.LazyDataModel;

import bean.model.CustomerLazyDataModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Customer;
import service.CustomerService;
import util.Message;

@Named
@ViewScoped
public class CustomerBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private CustomerService service;
	
	@Inject
	private CustomerLazyDataModel customerLazyModel;
	
	private Customer customer;
	private String searchTerm;
	private String searchOption;
	
	@PostConstruct
	public void init() {
		searchTerm = "";
		customerLazyModel.setSearchTerm(searchTerm);
		customerLazyModel.setSearchOption(searchOption);
	}
	
	public void find() {
		customerLazyModel.setSearchTerm(searchTerm);
		customerLazyModel.setSearchOption(searchOption);
	}
	
	public void save() {
		service.save(customer);
		Message.info("Cliente salvo com sucesso");
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

	public LazyDataModel<Customer> getCustomerLazyModel() {
		return customerLazyModel;
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
