package com.renker.cloud.security.configuration.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

public class KickoutSessionFilter extends AuthorizationFilter{
	
	private CacheManager cacheManager;
	
	private String kickoutUrl = "/manage/login";
	
	private int maxSession = 1;
	
	private SessionManager sessionManager;
	
	private Cache<String, Deque<Serializable>> cache;
	
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		Session session = subject.getSession();
		String username = (String) subject.getPrincipal();
		Serializable sessionId = session.getId();
		
		// 如果没有登陆 直接返回true
		if(!subject.isAuthenticated() && !subject.isRemembered()){
			return true;
		}
		
		Deque<Serializable> deque = cache.get((String)subject.getPrincipal());
		
		if(deque == null) {
            deque = new ConcurrentLinkedDeque<Serializable>();
		}
		
		if(!deque.contains(sessionId) && session.getAttribute("kickout") == null){
			deque.push(sessionId);
			cache.put(username, deque);
		}
		
		// 如果账号在线数量大于maxSession 添加踢出标识
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = deque.removeLast();
			cache.put(username, deque);
			
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
	            if(kickoutSession != null) {
	                //设置会话的kickout属性表示踢出了
	                kickoutSession.setAttribute("kickout", true);
	            }
			} catch (Exception e) {
				//TODO
				e.printStackTrace();
			}
		}
		
		//如果被踢出了，直接退出，重定向到指定链接
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) {
            	e.printStackTrace();
            }
            saveRequest(request);
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }
		
		
		return true;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        return false;
	}
	
	private boolean isAjax(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");  
	    boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;  
	    return isAjax;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
		this.cache = cacheManager.getCache("shiro-kickout-session");
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
}
