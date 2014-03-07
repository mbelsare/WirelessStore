package com.cmpe.two81.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.cmpe.two81.viewobject.Cart;
import com.cmpe.two81.viewobject.OAuthInfo;
import com.cmpe.two81.viewobject.Product;
import com.cmpe.two81.viewobject.ProductType;
import com.cmpe.two81.viewobject.Promotion;
import com.cmpe.two81.viewobject.Store;
import com.cmpe.two81.viewobject.User;

public interface CommonLookupService {

    List<Store> getStores(OAuthInfo oAuthInfo) throws IOException, HttpException;

    List<User> getUsers(OAuthInfo oAuthInfo, Integer storeId);

    List<ProductType> getProductTypes(OAuthInfo oAuthInfo, Integer storeId);

    List<Promotion> getPromotions(OAuthInfo oAuthInfo, Integer storeId);

    Map<Integer, List<Product>> getProducts(OAuthInfo oAuthInfo, Integer storeId);

    List<Product> getStoreFrontProducts(OAuthInfo oAuthInfo, Integer storeId, Integer productType);
    
    Boolean storeOrder(OAuthInfo oAuthInfo, Cart cart);
}
