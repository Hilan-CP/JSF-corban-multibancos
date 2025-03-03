package bean.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Proposal;
import service.ProposalService;

@Dependent
public class ProposalLazyModel extends LazyDataModel<Proposal>{
	
	@Inject
	private ProposalService service;
	
	private List<Proposal> proposals;
	private String searchTerm;
	private String searchOption;
	private String dateOption;
	private LocalDate beginDate;
	private LocalDate endDate;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		Long count = service.countByOption(searchTerm, searchOption, dateOption, beginDate, endDate);
		return count.intValue();
	}

	@Override
	public List<Proposal> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		proposals = service.findByOption(first, pageSize, searchTerm, searchOption, dateOption, beginDate, endDate);
		return proposals;
	}

	@Override
	public String getRowKey(Proposal proposal) {
		return proposal.getId().toString();
	}
	
	@Override
	public Proposal getRowData(String rowKey) {
		for(Proposal proposal : proposals) {
			if(proposal.getId().toString().equals(rowKey)) {
				return proposal;
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

	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
