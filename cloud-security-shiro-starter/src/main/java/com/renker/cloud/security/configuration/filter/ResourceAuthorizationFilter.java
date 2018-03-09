package com.renker.cloud.security.configuration.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;


public class ResourceAuthorizationFilter extends AuthorizationFilter{
	
//	private IResourceService resourceService;
//	
//	private static Map<String, Resource> resourceMap = null;
	
	private CacheManager cacheManager;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		String requestUri =  WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
		
//		if(resourceMap == null){
//			resourceMap = resourceService.findAll();
//		}
//		Resource resource = resourceMap.get(requestUri);
//		if(resource == null ){
//			return true;
//		}
//		
//		Set<Role> roles = resource.getRoles();
//		Set<Permissions> permissions = resource.getPermissions();
		
//		if(hasRole(roles, subject) && hasPermissions(permissions,subject)){
//			return true;
//		}
		return false;
	}
	
//	private boolean hasRole(Set<Role> roles,Subject subject){
//		for (Role role : roles) {
//			if(subject.hasRole(role.getCode())){
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	private boolean  hasPermissions(Set<Permissions> permissions,Subject subject){
//		for (Permissions perm : permissions) {
//			if(subject.isPermitted(perm.getCode())){
//				return true;
//			}
//		}
//		return false;
//	}

	

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
        	// Convenience method for subclasses to use when a login redirect is required
        	saveRequestAndRedirectToLogin(request, response);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (StringUtils.hasText(unauthorizedUrl)) {
				try {
					HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
	            	httpServletRequest.getSession().setAttribute("SHIRO_UNAUTHORIZED_EXCEPTION",new UnauthorizedException("没有相关角色或权限，禁止访问"));
	                //WebUtils.issueRedirect(request, response, unauthorizedUrl);
	            	if(isAjax(httpServletRequest)){
	            		unauthorizedUrl+="Ajax";
	            	}
					request.getServletContext().getRequestDispatcher(unauthorizedUrl).forward(request, response);
				} catch (ServletException e) {
					e.printStackTrace();
				}
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
	}
	
	
	private boolean isAjax(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");  
	    boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;  
	    return isAjax;
	}
	
//	public void setResourceService(IResourceService resourceService) {
//		this.resourceService = resourceService;
//	}
	
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
