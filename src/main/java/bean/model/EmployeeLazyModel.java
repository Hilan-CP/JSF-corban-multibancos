package bean.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Employee;
import service.EmployeeService;

@Dependent
public class EmployeeLazyModel extends LazyDataModel<Employee>{
	
	@Inject
	private EmployeeService service;
	
	private List<Employee> employees;
	private String searchTerm;
	private String searchOption;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		Long count = service.countByOption(searchTerm, searchOption);
		return count.intValue();
	}

	@Override
	public List<Employee> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		employees = service.findByOption(first, pageSize, searchTerm, searchOption);
		return employees;
	}

	@Override
	public String getRowKey(Employee employee) {
		if(employee.getCpf() != null) {
			return employee.getCpf();
		}
		return null;
	}
	
	@Override
	public Employee getRowData(String rowKey) {
		for(Employee employee : employees) {
			if(employee.getCpf().equals(rowKey)) {
				return employee;
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
