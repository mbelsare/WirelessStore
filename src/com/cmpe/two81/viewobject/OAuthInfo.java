package com.cmpe.two81.viewobject;

import java.io.Serializable;

public class OAuthInfo implements Serializable {

	private static final long serialVersionUID = 1996281684189095283L;

	private String instanceUrl;
    private String accessToken;

    public OAuthInfo(String instanceUrl, String accessToken) {
        this.accessToken = accessToken;
        this.instanceUrl = instanceUrl;
    }

    public OAuthInfo() {
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
