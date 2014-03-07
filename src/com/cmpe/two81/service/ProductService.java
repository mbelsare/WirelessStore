package com.cmpe.two81.service;

import com.cmpe.two81.viewobject.*;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductType> getProductTypes(OAuthInfo oAuthInfo, Integer storeId) throws IOException, HTTPException;

    List<Promotion> getPromotions(OAuthInfo oAuthInfo, Integer storeId) throws IOException, HTTPException;

    List<Product> getProducts(OAuthInfo oAuthInfo, Integer storeId) throws IOException, HTTPException;
}
