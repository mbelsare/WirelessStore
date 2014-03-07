package com.cmpe.two81.viewobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// SELECT Name, Description__c, Extention__c, ID__c, ImageLink__c FROM Promotion__c WHERE Store__c = :storeId
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attributes",
        "Name",
        "ImageLink__c",
        "ID__c",
        "Description__c",
        "Extention__c",
        "Id"
})
public class Promotion implements Serializable {

	private static final long serialVersionUID = 821685059363082353L;	
	
	@JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("ImageLink__c")
    private String ImageLink__c;
    @JsonProperty("ID__c")
    private String ID__c;
    @JsonProperty("Id")
    private String Id;
    @JsonProperty("Description__c")
    private String Description__c;
    @JsonProperty("Extention__c")
    private String Extention__c;
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

    @JsonProperty("ImageLink__c")
    public String getImageLink__c() {
        return ImageLink__c;
    }

    @JsonProperty("ImageLink__c")
    public void setImageLink__c(String imageLink__c) {
        this.ImageLink__c = imageLink__c;
    }

    @JsonProperty("ID__c")
    public String getID__c() {
        return ID__c;
    }

    @JsonProperty("ID__c")
    public void setID__c(String id__c) {
        this.ID__c = id__c;
    }

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(String Id) {
        this.Id = Id;
    }

    @JsonProperty("Description__c")
    public String getDescription__c() {
        return Description__c;
    }

    @JsonProperty("Description__c")
    public void setDescription__c(String Description__c) {
        this.Description__c = Description__c;
    }

    @JsonProperty("Extention__c")
    public String getExtention__c() {
        return Extention__c;
    }

    @JsonProperty("Extention__c")
    public void setExtention__c(String extention__c) {
        this.Extention__c = extention__c;
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
