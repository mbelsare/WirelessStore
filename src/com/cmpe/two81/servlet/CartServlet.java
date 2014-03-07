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
import com.cmpe.two81.viewobject.Cart;
import com.cmpe.two81.viewobject.OAuthInfo;
import com.cmpe.two81.viewobject.Product;
import com.cmpe.two81.viewobject.ProductInfo;
import com.cmpe.two81.viewobject.ProductType;
import com.cmpe.two81.viewobject.Store;

public class CartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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

        String productId = request.getParameter("productId");
        String action = request.getParameter("actionId");
        String strQty = request.getParameter("qty");

        if(productId == null || action == null) {
            response.sendRedirect(request.getContextPath() + "/main.jsp");
            return;
        }

        int qty;
        if(action.equalsIgnoreCase("ADD")) {
            try {
                qty = Integer.parseInt(strQty);
            }
            catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("errMsg", "Failed to understand quantity specified.");
                response.sendRedirect(request.getContextPath() + "/main.jsp");
                return;
            }
        }
        else {
            // For removing Qty does not matter as we don't allow users to change Qty while removing from cart
            qty = 1;
        }        
        Cart cart = (Cart) request.getSession().getAttribute("savedCart");
        if(cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("savedCart", cart);
        }
        Product product = null;
        
        Store store = (Store) request.getSession().getAttribute("store");
        @SuppressWarnings("unchecked")
		List<ProductType> productTypes = (List<ProductType>) request.getSession().getAttribute("productTypes");
        for (ProductType p : productTypes) {
            List<Product> frontProducts = commonLookupService.getStoreFrontProducts(oAuthInfo, store.getID__c(), p.getID__c());
            if (frontProducts != null && frontProducts.size() > 0) {
                for (Product prod : frontProducts) {
                    if (prod.getID__c().equalsIgnoreCase(productId)) {
                        product = prod;
                        break;
                    }
                }
            }
            if(product != null) {
                break;
            }
        }

        if(product != null) {
            List<ProductInfo> listProducts = cart.getProductInfos();
            for (ProductInfo prod : listProducts) {
                if(prod.getProductId().equalsIgnoreCase(product.getID__c())){
                    listProducts.remove(prod);
                    break;
                }
            }
            if(action.equalsIgnoreCase("ADD")) {
                if(product.getTotalInventory__c() < qty) {
                    request.getSession().setAttribute("errMsg", "Not enough inventory to fulfill order.");
                    response.sendRedirect(request.getContextPath() + "/main.jsp");
                    return;
                }
                ProductInfo prod = new ProductInfo();
                prod.setProductId(product.getID__c());
                prod.setImageLink(product.getImageLink__c());
                prod.setPrice(product.getPrice__c());
                prod.setProductName(product.getName());
                prod.setQuantity(qty);

                listProducts.add(prod);
            }

            if(listProducts.size() == 0) {
                request.getSession().removeAttribute("savedCart");
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}
