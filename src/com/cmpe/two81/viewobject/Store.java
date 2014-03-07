package com.cmpe.two81.viewobject;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//SELECT ID__c, Name, Style__c, Facebook__c, Twitter__c FROM Store__c
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attributes",
        "Name",
        "ID__c",
        "Style__c",
        "Facebook__c",
        "Twitter__c",
        "Id"
})
public class Store implements Serializable {


	private static final long serialVersionUID = -4490891943275596812L;

	@JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("ID__c")
    private Integer ID__c;
    @JsonProperty("Style__c")
    private String Style__c;
    @JsonProperty("Facebook__c")
    private String Facebook__c;
    @JsonProperty("Twitter__c")
    private String Twitter__c;
    @JsonProperty("Id")
    private String Id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Store withAttributes(Attributes attributes) {
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

    public Store withName(String Name) {
        this.Name = Name;
        return this;
    }

    @JsonProperty("ID__c")
    public Integer getID__c() {
        return ID__c;
    }

    @JsonProperty("ID__c")
    public void setID__c(Integer id__c) {
        this.ID__c = id__c;
    }

    public Store withID__c(Integer id__c) {
        this.ID__c = id__c;
        return this;
    }

    @JsonProperty("Style__c")
    public String getStyle__c() {
        return Style__c;
    }

    @JsonProperty("Style__c")
    public void setStyle__c(String id__c) {
        this.Style__c = id__c;
    }

    public Store withStyle__c(String id__c) {
        this.Style__c = id__c;
        return this;
    }

    @JsonProperty("Facebook__c")
    public String getFacebook__c() {
        return Facebook__c;
    }

    @JsonProperty("Facebook__c")
    public void setFacebook__c(String id__c) {
        this.Facebook__c = id__c;
    }

    public Store withFacebook__c(String id__c) {
        this.Facebook__c = id__c;
        return this;
    }

    @JsonProperty("Twitter__c")
    public String getTwitter__c() {
        return Twitter__c;
    }

    @JsonProperty("Twitter__c")
    public void setTwitter__c(String id__c) {
        this.Twitter__c = id__c;
    }

    public Store withTwitter__c(String id__c) {
        this.Twitter__c = id__c;
        return this;
    }

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(String Id) {
        this.Id = Id;
    }

    public Store withId(String Id) {
        this.Id = Id;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}