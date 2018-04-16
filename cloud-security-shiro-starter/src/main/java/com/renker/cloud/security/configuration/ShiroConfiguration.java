package com.renker.cloud.security.configuration;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.renker.cloud.security.configuration.filter.KickoutSessionFilter;
import com.renker.cloud.security.configuration.filter.ResourceAuthorizationFilter;
import com.renker.cloud.security.configuration.realm.DefaultAuthorizingRealm;
import com.renker.cloud.security.configuration.session.DefaultCachingSessionDao;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableConfigurationProperties({ShiroProperties.class,RedisProperties.class})
//@EnableFeignClients // 不需要此注解，再引用的地方引入即可
public class ShiroConfiguration {
	private Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(defaultWebSecurityManager);
		return advisor;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean
	public DefaultAuthorizingRealm defaultAuthorizingRealm(){
		return new DefaultAuthorizingRealm();
	}
	
	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager(DefaultAuthorizingRealm defaultAuthorizingRealm,DefaultWebSessionManager defaultWebSessionManager,RedisCacheManager redisCacheManager,CookieRememberMeManager rememberMeManager){
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(defaultAuthorizingRealm);
		manager.setSessionManager(defaultWebSessionManager);
		manager.setCacheManager(redisCacheManager);
		manager.setRememberMeManager(rememberMeManager);
		return manager;
	}
	
	/*@Bean
	public ChainDefinitionSectionMetaSource chainDefinitionSectionMetaSource(){
		ChainDefinitionSectionMetaSource source = new ChainDefinitionSectionMetaSource();
		Map<String, String> map = new HashMap<String, String>();
		source.setFilterChainDefinitions(filterChainDefinitions);
		return source;
	}*/
	
	@Bean(name="shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager,ShiroProperties props,KickoutSessionFilter kickoutSessionFilter) throws Exception{
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(defaultWebSecurityManager);
		factoryBean.setLoginUrl(props.getLoginUrl());
		factoryBean.setSuccessUrl(props.getSuccessUrl());
		factoryBean.setUnauthorizedUrl(props.getUnauthorizedUrl());
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		log.info("--------------- load shiro filterChainDefinition start ---------------");
		if(props.getFilterChainDefinitions() != null){
			map.put(props.getDoLoginUrl(), "anon");
			map.put(props.getVerificationCodeUrl(), "anon");
			log.info(props.getDoLoginUrl()+"="+"anon");
			log.info(props.getVerificationCodeUrl()+"="+"anon");
			appendToMap(map,props.getFilterChainDefinitions());
		}
		log.info("--------------- load shiro filterChainDefinition end ---------------");
		
		factoryBean.setFilterChainDefinitionMap(map);
		
		// 添加Filter
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		ResourceAuthorizationFilter resourceFilter =  new ResourceAuthorizationFilter();
//		resourceFilter.setResourceService(resourceService);
		filters.put("resource", resourceFilter);
		filters.put("kickout", kickoutSessionFilter);
		factoryBean.setFilters(filters);
		
		return factoryBean;
	}
	
	@Bean
	public KickoutSessionFilter KickoutSessionFilter (RedisCacheManager redisCacheManager,DefaultWebSessionManager defaultWebSessionManager){
		KickoutSessionFilter filter = new KickoutSessionFilter();
		filter.setCacheManager(redisCacheManager);
		filter.setSessionManager(defaultWebSessionManager);
		return filter;
	}
	
	/*@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new DelegatingFilterProxy("shiroFilter"));
		bean.addInitParameter("targetFilterLifecycle", "true");
		bean.setEnabled(true);
		bean.addUrlPatterns("/*");
		return bean;
	}*/
	
	@Bean
	public DefaultWebSessionManager defaultWebSessionManager(){
		DefaultWebSessionManager bean = new DefaultWebSessionManager();
		bean.setGlobalSessionTimeout(1800000);
		bean.setDeleteInvalidSessions(true);
		bean.setSessionValidationInterval(30000);
		bean.setSessionValidationSchedulerEnabled(true);
		
		// sessionDao
		JavaUuidSessionIdGenerator javaUuidSessionIdGenerator = new JavaUuidSessionIdGenerator();
		DefaultCachingSessionDao sessionDAO = new DefaultCachingSessionDao();
		sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		sessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator);
		bean.setSessionDAO(sessionDAO);
		
		// cookie
		SimpleCookie sessionIdCookie = new SimpleCookie();
		sessionIdCookie.setName("JESSIONID");
		sessionIdCookie.setPath("/");
		bean.setSessionIdCookie(sessionIdCookie);
		bean.setSessionIdCookieEnabled(true);
		
		return bean;
	}
	
	@Bean
	public RedisManager redisManager(RedisProperties props){
		RedisManager bean = new RedisManager();
		bean.setHost(props.getHost());
		bean.setPort(props.getPort());
		bean.setPassword(props.getPassword());
		bean.setTimeout(props.getTimeout());
		bean.setExpire(props.getExpire());
		return bean;
	}
	
	@Bean
	public RedisCacheManager redisCacheManager(RedisManager redisManager){
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager);
		redisCacheManager.setKeyPrefix("shiro_redis_cache:");
		return redisCacheManager;
	}
	
	@Bean
	public SimpleCookie rememberMeCookie(){
		SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
		rememberMeCookie.setHttpOnly(true);
		rememberMeCookie.setMaxAge(604800);
		return rememberMeCookie;
	}
	
	@Bean
	public CookieRememberMeManager cookieRememberMeManager(SimpleCookie rememberMeCookie){
		CookieRememberMeManager bean = new CookieRememberMeManager();
		bean.setCookie(rememberMeCookie);
		bean.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		return bean;
	}
	
	private void appendToMap(Map<String, String> map,String params){
		String regex = "((/|\\w|\\*)+?)=(\\w+(,\\w+)*)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(params);
		while (m.find()) {
			String url = m.group(1);
			String filters = m.group(3);
			map.put(url, filters);
			log.info(url+"="+filters);
		}
	}
}
