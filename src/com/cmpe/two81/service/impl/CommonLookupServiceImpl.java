package com.cmpe.two81.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.cmpe.two81.service.CommonLookupService;
import com.cmpe.two81.service.ProductService;
import com.cmpe.two81.viewobject.Cart;
import com.cmpe.two81.viewobject.OAuthInfo;
import com.cmpe.two81.viewobject.Order;
import com.cmpe.two81.viewobject.Product;
import com.cmpe.two81.viewobject.ProductType;
import com.cmpe.two81.viewobject.Promotion;
import com.cmpe.two81.viewobject.Store;
import com.cmpe.two81.viewobject.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonLookupServiceImpl implements CommonLookupService {

    private List<ProductType> productTypeList = new ArrayList<ProductType>();
    private List<User> userList = new ArrayList<User>();
    private List<Promotion> promotionList = new ArrayList<Promotion>();
    private Map<Integer, List<Product>> productMap = new HashMap<Integer, List<Product>>();

    private ProductServiceImpl productService;

    private void init(OAuthInfo oAuthInfo, Integer storeId) {
        try {
            //Populate all lookup maps
            populateUserList(oAuthInfo);
            populateProductTypeList(oAuthInfo, storeId);
            populatePromotionList(oAuthInfo, storeId);
            populateProductMap(oAuthInfo, storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Store> getStores(OAuthInfo oAuthInfo) throws IOException, HTTPException {
        List<Store> stores = new ArrayList<Store>();
        HttpClient httpclient = new HttpClient();

        GetMethod get = new GetMethod(oAuthInfo.getInstanceUrl() + "/services/apexrest/Store/all");
        get.setRequestHeader("Authorization", "OAuth " + oAuthInfo.getAccessToken());

        try {
            httpclient.executeMethod(get);
            if (get.getStatusCode() == HttpStatus.SC_OK) {
                // Now lets use the standard java json classes to work with the results
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.core.type.TypeReference<List<Store>> typeReference
                        = new com.fasterxml.jackson.core.type.TypeReference<List<Store>>() {
                };
                stores = mapper.readValue(get.getResponseBodyAsString(), typeReference);
            }
        } finally {
            get.releaseConnection();
        }
//        System.out.println("stores: " + stores);
        return stores;
    }

    private void populateUserList(OAuthInfo oAuthInfo) {
        HttpClient httpclient = new HttpClient();

        GetMethod get = new GetMethod(oAuthInfo.getInstanceUrl() + "/services/apexrest/User");
        get.setRequestHeader("Authorization", "OAuth " + oAuthInfo.getAccessToken());

        try {
            httpclient.executeMethod(get);
            if (get.getStatusCode() == HttpStatus.SC_OK) {
                // Now lets use the standard java json classes to work with the results
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.core.type.TypeReference<List<User>> typeReference
                        = new com.fasterxml.jackson.core.type.TypeReference<List<User>>() {
                };
                userList = mapper.readValue(get.getResponseBodyAsString(), typeReference);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
//        System.out.println("userList: " + userList);
    }

    public Boolean storeOrder(OAuthInfo oAuthInfo, Cart cart) {
        Boolean bStatus = Boolean.FALSE;
        HttpClient httpclient = new HttpClient();

        PostMethod post = new PostMethod(oAuthInfo.getInstanceUrl() + "/services/apexrest/Order/");
        post.setRequestHeader("Authorization", "OAuth " + oAuthInfo.getAccessToken());
		Order order = new Order(cart);

        try {
			ObjectMapper mapper = new ObjectMapper();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			mapper.setDateFormat(df);

			String orderJson = mapper.writeValueAsString(order);
			System.out.println(orderJson);
			
//			Audit.audit_order(orderJson);
			
			post.setRequestBody(orderJson);
			post.setRequestHeader("Content-Type","application/json; charset=UTF-8");
			post.setRequestHeader("Accept","application/json");
            httpclient.executeMethod(post);
            if (post.getStatusCode() == HttpStatus.SC_OK) {
                bStatus = Boolean.TRUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return bStatus;
    }

    private void populateProductTypeList(OAuthInfo oAuthInfo, Integer storeId) {
        try {
            productTypeList = getProductService().getProductTypes(oAuthInfo, storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populatePromotionList(OAuthInfo oAuthInfo, Integer storeId) {
        try {
            promotionList = getProductService().getPromotions(oAuthInfo, storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateProductMap(OAuthInfo oAuthInfo, Integer storeId) {
        try {
            List<Product> products = getProductService().getProducts(oAuthInfo, storeId);
            // Fill the productMap - ProductType - Products
            for (Product p : products) {
                List<Product> productListByType = productMap.get(p.getProductTypeID__c());
                if (productListByType == null) {
                    productListByType = new ArrayList<Product>();
                    productMap.put(p.getProductTypeID__c(), productListByType);
                }
                productListByType.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProductService getProductService() {
        if (this.productService == null) {
            this.productService = new ProductServiceImpl();
        }
        return this.productService;
    }

    public List<User> getUsers(OAuthInfo oAuthInfo, Integer storeId) {
        if (userList.size() == 0) {
            System.out.println("Empty ProductTypes");
            init(oAuthInfo, storeId);    // This should move to Spring framework
        }
        return this.userList;
    }

    public List<ProductType> getProductTypes(OAuthInfo oAuthInfo, Integer storeId) {
        if (productTypeList.size() == 0) {
            System.out.println("Empty ProductTypes");
            init(oAuthInfo, storeId);    // This should move to Spring framework
        }
        return this.productTypeList;
    }

    public List<Promotion> getPromotions(OAuthInfo oAuthInfo, Integer storeId) {
        if (promotionList.size() == 0) {
            System.out.println("Empty Promotions");
            init(oAuthInfo, storeId);
        }
        return this.promotionList;
    }

    public Map<Integer, List<Product>> getProducts(OAuthInfo oAuthInfo, Integer storeId) {
        if (productMap.size() == 0) {
            System.out.println("Empty products");
            init(oAuthInfo, storeId);
        }
        return productMap;
    }

    public List<Product> getStoreFrontProducts(OAuthInfo oAuthInfo, Integer storeId, Integer productType) {
        if (productMap.size() == 0) {
            System.out.println("Empty StoreFrontProducts");
            init(oAuthInfo, storeId);
        }

        List<Product> frontProducts = productMap.get(productType);
        return frontProducts;
    }
}
