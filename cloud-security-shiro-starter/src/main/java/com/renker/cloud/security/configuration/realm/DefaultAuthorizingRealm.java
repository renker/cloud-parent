package com.renker.cloud.security.configuration.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class DefaultAuthorizingRealm extends AuthorizingRealm{

//	@Resource
//	private IUserService userService;
	
//	@Resource
//	private IPermissionsService permissionsService;
//	
//	@Resource
//	private IRoleService roleService;
	
	/**
	 * 鉴权时调用
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		
//		// 获取用户信息
//		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("SHIRO_CURRENT_USER");
//		
//		// 获取用户权限
//		List<Permissions> permissions = permissionsService.findPermissions(user.getId());
//		
//		// 添加权限资源
//		for (int i = 0; permissions != null && i < permissions.size(); i++) {
//			simpleAuthorizationInfo.addStringPermission(permissions.get(i).getCode());
//		}
//		
//		// 获取用户角色
//		List<Role> roles = roleService.findRole(user.getId());
//		
//		// 添加角色资源
//		for (int i = 0; roles!=null && i < roles.size(); i++) {
//			simpleAuthorizationInfo.addRole(roles.get(i).getCode());
//		}
		
		return simpleAuthorizationInfo;
	}

	/**
	 * 登陆验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		
		String account = token.getUsername();
		String password = new String(token.getPassword());
		return null;
//		User user = userService.findByAccount(account);
//		if(user != null){
//			
//			if(user.getStatus() == -1 | user.getStatus() ==3  ){
//				throw new DisabledAccountException("账号已删除或禁用");
//			}else if(user.getStatus() ==2 ){
//				throw new LockedAccountException("账号已锁定");
//			}
//			
//			User userWithPassword = userService.findByAccountAndPassword(account, MD5Util.MD5(password, user.getSalt()));
//			if(userWithPassword != null){
//				
//				// 初始登陆错误次数为0
//				userWithPassword.setLoginErrorNum(0);
//				userService.updateByPrimaryKeySelective(userWithPassword);
//				
//				SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(account,password,  token.getUsername());
//				
//				Subject subject = SecurityUtils.getSubject();
//				subject.getSession().setAttribute("SHIRO_CURRENT_USER", userWithPassword);
//				return simpleAuthenticationInfo;
//				
//			}else{
//				// 更新用户登陆错误次数与状态
//				int errorNum = (user.getLoginErrorNum() == null?0:user.getLoginErrorNum())+1;
//				if(user.getLoginErrorAllowNum() < errorNum){
//					user.setStatus(2);
//				}else if(errorNum <= user.getLoginErrorAllowNum()){
//					user.setLoginErrorNum(errorNum);
//				}
//				userService.updateByPrimaryKeySelective(user);
//				throw new IncorrectCredentialsException("密码验证错误");
//			}
//			
//		}else{
//			throw new UnknownAccountException("账号验证错误");
//		}
	}
	
	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
	
	
}
