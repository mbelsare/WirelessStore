package com.cmpe.two81.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import com.cmpe.two81.service.ProductService;
import com.cmpe.two81.viewobject.OAuthInfo;
import com.cmpe.two81.viewobject.Product;
import com.cmpe.two81.viewobject.ProductType;
import com.cmpe.two81.viewobject.Promotion;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductServiceImpl implements ProductService {

    public List<ProductType> getProductTypes(OAuthInfo oAuthInfo, Integer storeId) throws IOException, HTTPException {
        List<ProductType> productTypes = new ArrayList<ProductType>();
        if (oAuthInfo != null && storeId != null) {
            HttpClient httpclient = new HttpClient();

            NameValuePair[] params = new NameValuePair[1];
            params[0] = new NameValuePair();
            params[0].setName("storeId");
            params[0].setValue(String.valueOf(storeId));

            GetMethod get = new GetMethod(oAuthInfo.getInstanceUrl() + "/services/apexrest/ProductType");
            get.setQueryString(params);
            get.setRequestHeader("Authorization", "OAuth " + oAuthInfo.getAccessToken());

            try {
                httpclient.executeMethod(get);
                if (get.getStatusCode() == HttpStatus.SC_OK) {
                    // Now lets use the standard java json classes to work with the results
                    ObjectMapper mapper = new ObjectMapper();
                    com.fasterxml.jackson.core.type.TypeReference<List<ProductType>> typeReference
                            = new com.fasterxml.jackson.core.type.TypeReference<List<ProductType>>() {
                    };
                    productTypes = mapper.readValue(get.getResponseBodyAsString(), typeReference);
                }
            } finally {
                get.releaseConnection();
            }
        }
        else {
            System.out.println("storeId: " + storeId);
        }
        System.out.println("productTypes: " + productTypes);
        return productTypes;
    }

    public List<Promotion> getPromotions(OAuthInfo oAuthInfo, Integer storeId) throws IOException, HTTPException {
        List<Promotion> promotions = new ArrayList<Promotion>();
        if (oAuthInfo != null && storeId != null) {
            HttpClient httpclient = new HttpClient();

            NameValuePair[] params = new NameValuePair[1];
            params[0] = new NameValuePair();
            params[0].setName("storeId");
            params[0].setValue(String.valueOf(storeId));

            GetMethod get = new GetMethod(oAuthInfo.getInstanceUrl() + "/services/apexrest/Promotion");
            get.setQueryString(params);
            get.setRequestHeader("Authorization", "OAuth " + oAuthInfo.getAccessToken());

            try {
                httpclient.executeMethod(get);
                if (get.getStatusCode() == HttpStatus.SC_OK) {
                    // Now lets use the standard java json classes to work with the results
                    ObjectMapper mapper = new ObjectMapper();
                    com.fasterxml.jackson.core.type.TypeReference<List<Promotion>> typeReference
                            = new com.fasterxml.jackson.core.type.TypeReference<List<Promotion>>() {
                    };
                    promotions = mapper.readValue(get.getResponseBodyAsString(), typeReference);
                }
            } finally {
                get.releaseConnection();
            }
        }
        else {
            System.out.println("storeId: " + storeId);
        }
        System.out.println("promotions: " + promotions);
        return promotions;
    }

    public List<Product> getProducts(OAuthInfo oAuthInfo, Integer storeId) throws IOException, HTTPException {
        List<Product> products = new ArrayList<Product>();
        if (oAuthInfo != null && storeId != null) {
            HttpClient httpclient = new HttpClient();

            NameValuePair[] params = new NameValuePair[1];
            params[0] = new NameValuePair();
            params[0].setName("storeId");
            params[0].setValue(String.valueOf(storeId));

            GetMethod get = new GetMethod(oAuthInfo.getInstanceUrl() + "/services/apexrest/Product");
            get.setQueryString(params);
            get.setRequestHeader("Authorization", "OAuth " + oAuthInfo.getAccessToken());

            try {
                httpclient.executeMethod(get);
                if (get.getStatusCode() == HttpStatus.SC_OK) {
                    // Now lets use the standard java json classes to work with the results
                    ObjectMapper mapper = new ObjectMapper();
                    com.fasterxml.jackson.core.type.TypeReference<List<Product>> typeReference
                            = new com.fasterxml.jackson.core.type.TypeReference<List<Product>>() {
                    };
                    products = mapper.readValue(get.getResponseBodyAsString(), typeReference);
                }
            } finally {
                get.releaseConnection();
            }
        }
        else {
            System.out.println("storeId: " + storeId);
        }
        System.out.println("products: " + products);
        return products;
    }
}