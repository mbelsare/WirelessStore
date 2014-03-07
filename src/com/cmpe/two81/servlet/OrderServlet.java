package com.cmpe.two81.servlet;

import com.cmpe.two81.common.CommonConstants;
import com.cmpe.two81.service.CommonLookupService;
import com.cmpe.two81.service.impl.CommonLookupServiceImpl;
import com.cmpe.two81.util.FileUtil;
import com.cmpe.two81.viewobject.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OrderServlet extends HttpServlet {

	private static final long serialVersionUID = -2899298947990754344L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        
        User user = (User) request.getSession().getAttribute("loggedUser");
        if(user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Cart cart = (Cart) request.getSession().getAttribute("savedCart");
        if(cart == null) {
            request.getSession().setAttribute("errMsg", "Cart is empty, please first add items to cart.");
            response.sendRedirect(request.getContextPath() + "/main.jsp");
            return;
        }
        Store store = (Store) request.getSession().getAttribute("store");

        // populate other details of order in cart object
        cart.setStore(store);
        cart.setUser(user);
        cart.setOrderDate(new Date());

        double totalCost = 0.0;
        List<ProductInfo> listProducts = cart.getProductInfos();
        for (ProductInfo product : listProducts) {
            totalCost += (product.getPrice() * product.getQuantity());
        }
        cart.setTotalCost(totalCost);

        // process order here
        if(! commonLookupService.storeOrder(oAuthInfo, cart)) {
            request.getSession().setAttribute("errMsg", "Failed to store order in Force.com database.");
        }
        else {
            request.getSession().removeAttribute("savedCart");
            request.getSession().setAttribute("errMsg", "Successfully processed order!!");

            // Update Inventory of Local objects
            @SuppressWarnings("unchecked")
			List<ProductType> productTypes = (List<ProductType>) request.getSession().getAttribute("productTypes");
            for (ProductInfo product : listProducts) {
                for (ProductType p : productTypes) {
                    boolean bBreak = false;
                    List<Product> frontProducts = commonLookupService.getStoreFrontProducts(oAuthInfo, store.getID__c(), p.getID__c());
                    if (frontProducts != null && frontProducts.size() > 0) {
                        for (Product prod : frontProducts) {
                            if(product.getProductId().equalsIgnoreCase(prod.getId())){
                                prod.setTotalInventory__c(prod.getTotalInventory__c() - product.getQuantity());
                                bBreak = true;
								request.getSession().setAttribute("frontProducts",frontProducts);
								break;
                            }
                        }
                    }
                    if(bBreak) break;
                }
            }
		}
        response.sendRedirect(request.getContextPath() + "/main.jsp");
    }
}
