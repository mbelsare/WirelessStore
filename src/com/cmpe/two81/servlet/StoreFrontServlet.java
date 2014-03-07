package com.cmpe.two81.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmpe.two81.common.CommonConstants;
import com.cmpe.two81.service.CommonLookupService;
import com.cmpe.two81.service.impl.CommonLookupServiceImpl;
import com.cmpe.two81.util.FileUtil;
import com.cmpe.two81.viewobject.OAuthInfo;
import com.cmpe.two81.viewobject.Product;
import com.cmpe.two81.viewobject.ProductType;
import com.cmpe.two81.viewobject.Promotion;
import com.cmpe.two81.viewobject.Store;

public class StoreFrontServlet extends HttpServlet {

	private static final long serialVersionUID = 4725951240460070804L;

	protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        OAuthInfo oAuthInfo = (OAuthInfo) request.getSession().getAttribute(CommonConstants.OAUTH_INFO);
        request.getSession().removeAttribute("errMsg");

        // If not in session, try from fileStore.
        if (oAuthInfo == null) {
            oAuthInfo = (OAuthInfo) FileUtil.loadObject(CommonConstants.OAUTH_FILE_LOCATION, CommonConstants.OAUTH_FILENAME);
        }

        //If OAuthInfo doesn't exist in session and file store - go to '/oauth'
        if (oAuthInfo == null) {
            response.sendRedirect(request.getContextPath() + "/oauth/");
            return;
        }

        System.out.println("We have an access token: " + oAuthInfo.getAccessToken() + "\n"
                + "Using instance " + oAuthInfo.getInstanceUrl());

        CommonLookupService commonLookupService = (CommonLookupService) request.getSession().getAttribute("serviceImpl");
        if (commonLookupService == null) {
            commonLookupService = new CommonLookupServiceImpl();
            request.getSession().setAttribute("serviceImpl", commonLookupService);
        }

        // Navigate to store selection page.
        if (request.getQueryString() == null || request.getQueryString().length() == 0) {
            @SuppressWarnings("unchecked")
			List<Store> stores = (List<Store>) request.getSession().getAttribute("stores");
            if (stores == null || stores.size() == 0) {
                stores = commonLookupService.getStores(oAuthInfo);
                request.getSession().setAttribute("stores", stores);
            }
            if (stores == null || stores.size() == 0) {
                //Invalid token - go to '/oauth' - Assumption: There wont be a state with 0 stores.
                request.getSession().removeAttribute(CommonConstants.OAUTH_INFO);
                FileUtil.removeObject(CommonConstants.OAUTH_FILE_LOCATION, CommonConstants.OAUTH_FILENAME);
                response.sendRedirect(request.getContextPath() + "/oauth/");
                return;
            } else {
                request.getSession().setAttribute("stores", stores);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
        }

        // Navigate to Specific store main page
        if (request.getParameterMap().size() == 1 && request.getParameter("storeId") != null) {

            @SuppressWarnings("unchecked")
			List<Store> stores = (List<Store>) request.getSession().getAttribute("stores");
            if (stores == null || stores.size() == 0) {
                stores = commonLookupService.getStores(oAuthInfo);
                request.getSession().setAttribute("stores", stores);
            }
            if (stores == null || stores.size() == 0) {
                //Something went wrong
                //Go back to store selection page.
                response.sendRedirect(request.getContextPath() + "/store");
                return;
            }

            Integer storeId = Integer.parseInt(request.getParameter("storeId"));
            for (Store store : stores) {
                if (store.getID__c() == storeId) {
                    request.getSession().setAttribute("store", store);
                    break;
                }
            }

            List<ProductType> productTypes = commonLookupService.getProductTypes(oAuthInfo, storeId);
            if (productTypes != null && productTypes.size() > 0) {
                request.getSession().setAttribute("productTypes", productTypes);
            }

            List<Promotion> promotions = commonLookupService.getPromotions(oAuthInfo, storeId);
            if (promotions != null && promotions.size() > 0) {
                request.getSession().setAttribute("promotions", promotions);
            }

            for (ProductType p : productTypes) {
                List<Product> frontProducts = commonLookupService.getStoreFrontProducts(oAuthInfo, storeId, p.getID__c());
                if (frontProducts != null && frontProducts.size() > 0) {
                    request.getSession().setAttribute("frontProducts", frontProducts);
                    request.getSession().setAttribute("productType", p);
                    break;
                }
            }
            response.sendRedirect(request.getContextPath() + "/main.jsp");
        }
    }
}
