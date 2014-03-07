package com.cmpe.two81.servlet; /**
 * Created by IntelliJ IDEA.
 * User: jue
 * Date: 10/1/13
 * Time: 12:17 AM
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.cmpe.two81.common.CommonConstants;
import com.cmpe.two81.util.FileUtil;
import com.cmpe.two81.viewobject.OAuthInfo;

/**
 * Servlet parameters
 */
public class OAuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String clientId = null;
    private String clientSecret = null;
    private String redirectUri = null;
    private String environment = null;
    private String authUrl = null;
    private String tokenUrl = null;

    public void init() throws ServletException {
        clientId = this.getInitParameter("clientId");
        clientSecret = this.getInitParameter("clientSecret");
        redirectUri = this.getInitParameter("redirectUri");
        environment = this.getInitParameter("environment");

        try {
            authUrl = environment
                    + "/services/oauth2/authorize?response_type=code&client_id="
                    + clientId + "&redirect_uri="
                    + URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ServletException(e);
        }

        tokenUrl = environment + "/services/oauth2/token";
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        OAuthInfo oAuthInfo = (OAuthInfo) request.getSession().getAttribute(CommonConstants.OAUTH_INFO);
        request.getSession().removeAttribute("errMsg");

        if (oAuthInfo == null) {
            String instanceUrl = null;
            String accessToken = null;

            if (request.getRequestURI().endsWith("/oauth/")) {
                System.out.println(request.getRequestURI() + " authUrl " + authUrl);

                // we need to send the user to authorize
                response.sendRedirect(authUrl);
                return;
            } else {
                System.out.println(request.getRequestURI() + " Auth successful - got callback");

                String code = request.getParameter("code");

                HttpClient httpclient = new HttpClient();

                PostMethod post = new PostMethod(tokenUrl);
                post.addParameter("code", code);
                post.addParameter("grant_type", "authorization_code");
                post.addParameter("client_id", clientId);
                post.addParameter("client_secret", clientSecret);
                post.addParameter("redirect_uri", redirectUri);

                try {
                    httpclient.executeMethod(post);

                    try {
                        JSONObject authResponse = new JSONObject(
                                new JSONTokener(new InputStreamReader(
                                        post.getResponseBodyAsStream())));
                        System.out.println("Auth response: "
                                + authResponse.toString(2));

                        accessToken = authResponse.getString("access_token");
                        instanceUrl = authResponse.getString("instance_url");

                        System.out.println("Got access token: " + accessToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        throw new ServletException(e);
                    }
                } finally {
                    post.releaseConnection();
                }
            }

            // Set a session attribute so that other servlets can get the access token
            oAuthInfo = new OAuthInfo(instanceUrl, accessToken);
            System.out.println("OAuthServlet: New Token from force.com" + oAuthInfo.getInstanceUrl() + " : " + oAuthInfo.getAccessToken());
            request.getSession().setAttribute(CommonConstants.OAUTH_INFO, oAuthInfo);
            //Storing OAuthInfo in file for other sessions.
            FileUtil.storeObject(oAuthInfo, CommonConstants.OAUTH_FILE_LOCATION, CommonConstants.OAUTH_FILENAME);

        } else {
            System.out.println("OAuthServlet: Existing Token from session" + oAuthInfo.getInstanceUrl() + " : " + oAuthInfo.getAccessToken());
        }

        response.sendRedirect(request.getContextPath() + "/store");
    }


}
