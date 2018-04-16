package com.renker.cloud.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="shiro")
public class ShiroProperties {
	/** 登陆页url */
	private String loginUrl;
	/** 登陆url */
	private String doLoginUrl;
	/** 登出url */
	private String logoutUrl;
	/** 登陆成功后跳转的url */
	private String successUrl;
	/** 登陆成功后跳转的页面View */
	private String successView;
	/** 鉴权失败后提示页面url */
	private String unauthorizedUrl;
	/** 鉴权失败后提示页面view */
	private String unauthorizedView;
	private String filterChainDefinitions;
	private String [] filters;
	private String verificationCodeUrl;
	private String verificationCodeHeight;
	private String verificationCodeWidth;
	
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getDoLoginUrl() {
		return doLoginUrl;
	}
	public void setDoLoginUrl(String doLoginUrl) {
		this.doLoginUrl = doLoginUrl;
	}
	public String getLogoutUrl() {
		return logoutUrl;
	}
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getSuccessView() {
		return successView;
	}
	public void setSuccessView(String successView) {
		this.successView = successView;
	}
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}
	public String getUnauthorizedView() {
		return unauthorizedView;
	}
	public void setUnauthorizedView(String unauthorizedView) {
		this.unauthorizedView = unauthorizedView;
	}
	public String getFilterChainDefinitions() {
		return filterChainDefinitions;
	}
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}
	public String[] getFilters() {
		return filters;
	}
	public void setFilters(String[] filters) {
		this.filters = filters;
	}
	public String getVerificationCodeUrl() {
		return verificationCodeUrl;
	}
	public void setVerificationCodeUrl(String verificationCodeUrl) {
		this.verificationCodeUrl = verificationCodeUrl;
	}
	public String getVerificationCodeHeight() {
		return verificationCodeHeight;
	}
	public void setVerificationCodeHeight(String verificationCodeHeight) {
		this.verificationCodeHeight = verificationCodeHeight;
	}
	public String getVerificationCodeWidth() {
		return verificationCodeWidth;
	}
	public void setVerificationCodeWidth(String verificationCodeWidth) {
		this.verificationCodeWidth = verificationCodeWidth;
	}
}
