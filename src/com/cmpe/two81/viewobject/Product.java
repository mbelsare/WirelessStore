package com.cmpe.two81.viewobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// SELECT Name, Description__c, ImageLink__c, Price__c, ProductType__c, TotalInventory__c, SpecialNote__c FROM Product__c WHERE Store__c = :storeId
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attributes",
        "Name",
        "Description__c",
        "ImageLink__c",
        "ProductTypeID__c",
        "ID__c",
        "Id",
        "Price__c",
        "TotalInventory__c",
        "SpecialNote__c"
})
public class Product implements Serializable {

	
	private static final long serialVersionUID = 3378757748488863126L;

	@JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Description__c")
    private String Description__c;
    @JsonProperty("ImageLink__c")
    private String ImageLink__c;
    @JsonProperty("ProductTypeID__c")
    private Integer ProductTypeID__c;
    @JsonProperty("Id")
    private String Id;
    @JsonProperty("ID__c")
    private String ID__c;
    @JsonProperty("Price__c")
    private Double Price__c;
    @JsonProperty("TotalInventory__c")
    private Integer TotalInventory__c;
    @JsonProperty("SpecialNote__c")
    private String SpecialNote__c;
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

    @JsonProperty("Description__c")
    public String getDescription__c() {
        return Description__c;
    }

    @JsonProperty("Description__c")
    public void setDescription__c(String description__c) {
        this.Description__c = description__c;
    }

    @JsonProperty("ImageLink__c")
    public String getImageLink__c() {
        return ImageLink__c;
    }

    @JsonProperty("ImageLink__c")
    public void setImageLink__c(String imageLink__c) {
        this.ImageLink__c = imageLink__c;
    }

    @JsonProperty("ProductTypeID__c")
    public Integer getProductTypeID__c() {
        return ProductTypeID__c;
    }

    @JsonProperty("ProductTypeID__c")
    public void setProductTypeID__c(Integer productTypeId__c) {
        this.ProductTypeID__c = productTypeId__c;
    }

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(String Id) {
        this.Id = Id;
    }

    @JsonProperty("ID__c")
    public String getID__c() {
        return ID__c;
    }

    @JsonProperty("ID__c")
    public void setID__c(String id__c) {
        this.ID__c = id__c;
    }

    @JsonProperty("Price__c")
    public Double getPrice__c() {
        return Price__c;
    }

    @JsonProperty("Price__c")
    public void setPrice__c(Double price__c) {
        this.Price__c = price__c;
    }

    @JsonProperty("TotalInventory__c")
    public Integer getTotalInventory__c() {
        return TotalInventory__c;
    }

    @JsonProperty("TotalInventory__c")
    public void setTotalInventory__c(Integer imageLink__c) {
        this.TotalInventory__c = imageLink__c;
    }

    @JsonProperty("SpecialNote__c")
    public String getSpecialNote__c() {
        return SpecialNote__c;
    }

    @JsonProperty("SpecialNote__c")
    public void setSpecialNote__c(String Id) {
        this.SpecialNote__c = Id;
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