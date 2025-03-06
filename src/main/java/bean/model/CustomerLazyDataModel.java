package bean.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Customer;
import service.CustomerService;

@Dependent
public class CustomerLazyDataModel extends LazyDataModel<Customer>{

	@Inject
	private CustomerService service;
	
	private List<Customer> customers;
	private String searchTerm;
	private String searchOption;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return service.countByOption(searchTerm, searchOption).intValue();
	}

	@Override
	public List<Customer> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		customers = service.findByOption(first, pageSize, searchTerm, searchOption);
		return customers;
	}
	
	@Override
	public String getRowKey(Customer customer) {
		if(customer.getCpf() != null) {
			return customer.getCpf();
		}
		return null;
	}
	
	@Override
	public Customer getRowData(String rowKey) {
		for(Customer customer : customers) {
			if(customer.getCpf().equals(rowKey)) {
				return customer;
			}
		}
		return null;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
}
