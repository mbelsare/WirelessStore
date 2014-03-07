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
import com.cmpe.two81.viewobject.Store;

public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 3613120767141331756L;

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
        if (request.getParameterMap().size() == 1 && request.getParameter("productId") != null) {

            Store store = (Store) request.getSession().getAttribute("store");
            @SuppressWarnings("unchecked")
			List<Product> frontProducts = (List<Product>) request.getSession().getAttribute("frontProducts");

            String productId = request.getParameter("productId");

            if (store == null || frontProducts == null || frontProducts.size() == 0 || productId == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                for (Product p : frontProducts) {
                    if (p.getId().equalsIgnoreCase(productId)) {
                        request.getSession().setAttribute("product", p);
                        break;
                    }
                }
                response.sendRedirect(request.getContextPath() + "/product.jsp");
            }
        }
    }
}
