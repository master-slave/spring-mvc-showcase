package org.springframework.samples.mvc.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.beans.Transient;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class JavaBean {

	private String foo = "bar";

	private String fruit = "apple";

    private Account account;


    private Account accountId;


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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
	public String toString() {
		return "JavaBean {foo=[" + foo + "], fruit=[" + fruit + "]}";
	}


    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.account = account;
    }
}
