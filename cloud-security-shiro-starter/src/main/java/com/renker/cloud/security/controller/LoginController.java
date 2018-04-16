package com.renker.cloud.security.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.renker.cloud.common.mvc.AjaxResult;
import com.renker.cloud.common.mvc.BaseController;
import com.renker.cloud.common.util.VerifyCodeUtil;
import com.renker.cloud.security.ShiroConstant;


@Controller
public class LoginController extends BaseController{
	@Value("${fairyland.shiro.successUrl}")
	private String successUrl;
	
	@Value("${fairyland.shiro.successView}")
	private String successView;
	
	@Value("${fairyland.shiro.loginUrl}")
	private String loginUrl;
	
	@Value("${fairyland.shiro.unauthorizedUrl}")
	private String unauthorizedUrl;
	
	@Value("${fairyland.shiro.unauthorizedView}")
	private String unauthorizedView;
	
	@Value("${fairyland.shiro.verificationCodeHeight}")
	private Integer verificationCodeHeight;
	
	@Value("${fairyland.shiro.verificationCodeWidth}")
	private Integer verificationCodeWidth;
	
	@RequestMapping("${fairyland.shiro.loginUrl}")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="${fairyland.shiro.doLoginUrl}",method=RequestMethod.POST)
	public String doLogin(@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam(value="rememberMe",defaultValue="false")Boolean rememberMe,Model model,HttpServletRequest request){
		String errorMsg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(rememberMe);
			subject.login(token);
			
			String redirectUrl = successUrl;
			
			// 重定向到之前跳至登陆的页面
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(RequestMethod.GET.toString())){
				if(savedRequest.getQueryString()==null){
					redirectUrl = savedRequest.getRequestURI();
				}else{
					redirectUrl = savedRequest.getRequestURI()+"?"+savedRequest.getQueryString();
				}
				String contextPath = request.getContextPath();
				redirectUrl = redirectUrl.replaceFirst(contextPath, "");
			}
			
			return "redirect:"+redirectUrl;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			request.getSession().setAttribute(ShiroConstant.SHIRO_LOGIN__EXCEPTION, e);
			return "redirect:"+loginUrl;
		}
	}
	
	@RequestMapping("${fairyland.shiro.successUrl}")
	public String index(){
		return successView;
	}
	
	@RequestMapping("${fairyland.shiro.logoutUrl}")
	public String logoutUrl(){
		SecurityUtils.getSubject().logout();
		return "redirect:"+loginUrl;
	}
	
	@RequestMapping("${fairyland.shiro.verificationCodeUrl}")
	public void verificationCode(HttpServletResponse response,HttpServletRequest request) throws IOException{
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase()); 
        //生成图片 
        VerifyCodeUtil.outputImage(verificationCodeWidth, verificationCodeHeight, response.getOutputStream(), verifyCode);
	}
	
	@RequestMapping("${fairyland.shiro.unauthorizedUrl}")
	public ModelAndView unauthorized(HttpServletRequest request){
		UnauthorizedException exception = (UnauthorizedException) request.getSession().getAttribute(ShiroConstant.SHIRO_UNAUTHORIZED_EXCEPTION);
		request.getSession().removeAttribute(ShiroConstant.SHIRO_UNAUTHORIZED_EXCEPTION);
		ModelAndView mv = new ModelAndView(unauthorizedView);
		mv.addObject("UNAUTHORIZED_EXCEPTION_MSG", exception.getMessage());
		return mv;
	}
	
	@RequestMapping("${fairyland.shiro.unauthorizedUrl}Ajax")
	@ResponseBody
	public AjaxResult unauthorizedAjax(HttpServletRequest request){
		UnauthorizedException exception = (UnauthorizedException) request.getSession().getAttribute(ShiroConstant.SHIRO_UNAUTHORIZED_EXCEPTION);
		request.getSession().removeAttribute(ShiroConstant.SHIRO_UNAUTHORIZED_EXCEPTION);
		return ajaxResult(Status.UNAUTHORIZED, exception.getMessage());
	}
}
