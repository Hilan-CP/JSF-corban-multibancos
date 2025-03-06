package bean.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Bank;
import service.BankService;

@Dependent
public class BankLazyModel extends LazyDataModel<Bank>{
	
	@Inject
	private BankService service;
	
	private List<Bank> banks;
	private String searchTerm;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		Long count = service.countByOption(searchTerm);
		return count.intValue();
	}

	@Override
	public List<Bank> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		banks = service.findByOption(first, pageSize, searchTerm);
		return banks;
	}

	@Override
	public String getRowKey(Bank bank) {
		if(bank.getId() != null) {
			return bank.getId().toString();
		}
		return null;
	}
	
	@Override
	public Bank getRowData(String rowKey) {
		for(Bank bank : banks) {
			if(bank.getId().toString().equals(rowKey)) {
				return bank;
			}
		}
		return null;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
}
