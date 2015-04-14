package org.springframework.samples.mvc.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlRootElement;
import java.beans.Transient;
import java.util.Date;

//@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class JavaBean {

	private String foo = "bar";

	private String fruit = "apple";


    @JsonDeserialize(using = YourDateDeserializer.class)
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
	public String toString() {
		return "JavaBean {foo=[" + foo + "], fruit=[" + fruit + "], date=[" + date +"]}";
	}


}
