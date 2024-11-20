package converter;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import model.entity.Bank;
import service.BankService;

@FacesConverter(value = "bankConverter", managed = true)
public class BankConverter implements Converter<Bank>{
	
	private BankService service;

	@Override
	public Bank getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			service = CDI.current().select(BankService.class).get();
			return service.findById(Long.parseLong(value));
		}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Bank value) {
		try {
			return value.getId().toString();
		}
		catch(Exception e) {
			return null;
		}
	}

}
