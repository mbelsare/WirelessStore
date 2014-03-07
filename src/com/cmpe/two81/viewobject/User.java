package com.cmpe.two81.viewobject;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//SELECT Name, LoginName__c, Password__c FROM User__c
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attributes",
        "Name",
        "LoginName__c",
        "Password__c",
        "Id"
})
public class User implements Serializable {
	
	private static final long serialVersionUID = -1799085830118282048L;

	@JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("LoginName__c")
    private String LoginName__c;
    @JsonProperty("Password__c")
    private String Password__c;
    @JsonProperty("Id")
    private String Id;

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public User withAttributes(Attributes attributes) {
        this.attributes = attributes;
        return this;
    }

    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    @JsonProperty("Name")
    public void setName(String Name) {
        this.Name = Name;
    }

    public User withName(String Name) {
        this.Name = Name;
        return this;
    }

    @JsonProperty("LoginName__c")
    public String getLoginName__c() {
        return LoginName__c;
    }

    @JsonProperty("LoginName__c")
    public void setLoginName__c(String password__c) {
        this.LoginName__c = password__c;
    }

    public User withPassword__c(String password__c) {
        this.Password__c = password__c;
        return this;
    }

    @JsonProperty("Password__c")
    public String getPassword__c() {
        return Password__c;
    }

    @JsonProperty("Password__c")
    public void setPassword__c(String password__c) {
        this.Password__c = password__c;
    }

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(String Id) {
        this.Id = Id;
    }

    public User withId(String Id) {
        this.Id = Id;
        return this;
    }
}
