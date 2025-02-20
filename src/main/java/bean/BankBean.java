package bean;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Bank;
import service.BankService;
import util.Message;

@Named
@ViewScoped
public class BankBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private BankService service;
	
	private Bank bank;
	private List<Bank> bankList;
	
	public void findAll() {
		bankList = service.findAll();
	}
	
	public void save() {
		service.save(bank);
		Message.info("Banco salvo com sucesso");
	}
	
	public void initializeCreate() {
		bank = new Bank();
	}
	
	public Bank getBank() {
		return bank;
	}
	
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	public List<Bank> getBankList() {
		return bankList;
	}
}
