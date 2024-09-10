package util;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("hello")
@RequestScoped
public class HelloWorldBean {
	
	private String hello = "hello world";

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}
}
