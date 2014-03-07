package com.cmpe.two81.viewobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// SELECT Name, ID__c, Rank__c, TabName__c FROM ProductType__c WHERE Store__c = :storeId
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attributes",
        "Name",
        "Rank__c",
        "TabName__c",
        "ID__c",
        "Id"
})
public class ProductType implements Serializable {

    private static final long serialVersionUID = 3786643375171593352L;
	
    @JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Rank__c")
    private Integer Rank__c;
    @JsonProperty("TabName__c")
    private String TabName__c;
    @JsonProperty("ID__c")
    private Integer ID__c;
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

    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    @JsonProperty("Name")
    public void setName(String Name) {
        this.Name = Name;
    }

    @JsonProperty("rank__c")
    public Integer getRank__c() {
        return Rank__c;
    }

    @JsonProperty("Rank__c")
    public void setRank__c(Integer rank__c) {
        this.Rank__c = rank__c;
    }

    @JsonProperty("TabName__c")
    public String getTabName__c() {
        return TabName__c;
    }

    @JsonProperty("TabName__c")
    public void setTabName__c(String tabName__c) {
        this.TabName__c = tabName__c;
    }

    @JsonProperty("ID__c")
    public Integer getID__c() {
        return ID__c;
    }

    @JsonProperty("ID__c")
    public void setID__c(Integer Id__c) {
        this.ID__c = Id__c;
    }

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(String Id) {
        this.Id = Id;
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