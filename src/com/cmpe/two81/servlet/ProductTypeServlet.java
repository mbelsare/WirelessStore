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
import com.cmpe.two81.viewobject.Store;

public class ProductTypeServlet extends HttpServlet {

	private static final long serialVersionUID = 7638112084851719894L;

	/**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
     *      response)
     */
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
                //Something went wrong
                //Go back to store selection page.
                response.sendRedirect(request.getContextPath() + "/store");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            return;
        }


        // Navigate to Specific store main page
        if (request.getParameterMap().size() == 1 && request.getParameter("productTypeId") != null) {

            Store store = (Store) request.getSession().getAttribute("store");
            @SuppressWarnings("unchecked")
			List<ProductType> productTypes = (List<ProductType>) request.getSession().getAttribute("productTypes");

            Integer productTypeId = Integer.parseInt(request.getParameter("productTypeId"));

            if (store == null || productTypes == null || productTypes.size() == 0) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                if (productTypeId == -1) {
                    request.getSession().removeAttribute("productType");
                } else {
                    for (ProductType p : productTypes) {
                        if (p.getID__c() == productTypeId) {
                            List<Product> frontProducts = commonLookupService.getStoreFrontProducts(oAuthInfo, store.getID__c(), p.getID__c());
                            if (frontProducts != null && frontProducts.size() > 0) {
                                request.getSession().setAttribute("frontProducts", frontProducts);
                                request.getSession().setAttribute("productType", p);
                            }
                            break;
                        }
                    }
                }
                response.sendRedirect(request.getContextPath() + "/main.jsp");
            }
        }
    }
}
