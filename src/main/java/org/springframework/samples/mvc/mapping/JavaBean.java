package org.springframework.samples.mvc.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.beans.Transient;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class JavaBean {

	private String foo = "bar";

	private String fruit = "apple";

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String getFruit() {
		return fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

    @Override
	public String toString() {
		return "JavaBean {foo=[" + foo + "], fruit=[" + fruit + "]}";
	}
}
