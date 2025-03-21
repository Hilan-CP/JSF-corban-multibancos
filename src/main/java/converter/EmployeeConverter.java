package converter;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import model.entity.Employee;
import service.EmployeeService;

@FacesConverter(value = "employeeConverter", managed = true)
public class EmployeeConverter implements Converter<Employee>{
	
	private EmployeeService service;

	@Override
	public Employee getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		service = CDI.current().select(EmployeeService.class).get();
		return service.findByCpf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Employee value) {
		if(value == null) {
			return null;
		}
		return value.getCpf();
	}
}
