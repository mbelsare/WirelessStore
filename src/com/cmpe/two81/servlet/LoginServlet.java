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
import com.cmpe.two81.viewobject.Store;
import com.cmpe.two81.viewobject.User;

public class LoginServlet extends HttpServlet {

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

        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        Store store = (Store) request.getSession().getAttribute("store");
        List<User> userList = commonLookupService.getUsers(oAuthInfo, store.getID__c());
        boolean bFailed = true;
        for(User user: userList) {
            if(user.getLoginName__c().equalsIgnoreCase(userName) && user.getPassword__c().equals(password)) {
                bFailed = false;
                request.getSession().setAttribute("loggedUser", user);
                break;
            }
        }

        if(bFailed) {
            request.getSession().setAttribute("errMsg", "Invalid Username or Password...");
        }
        
        // Uncomment locally to show audit.
//        Audit.audit_login(userName, store);
        response.sendRedirect(request.getContextPath() + "/main.jsp");
    }

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

        request.getSession().removeAttribute("loggedUser");
        response.sendRedirect(request.getContextPath() + "/main.jsp");
    }
}
