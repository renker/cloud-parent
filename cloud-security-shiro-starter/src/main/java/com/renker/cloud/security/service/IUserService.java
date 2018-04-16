package com.renker.cloud.security.service;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.renker.cloud.security.rest.service.IUserRestService;

@FeignClient("example-server")
public interface IUserService extends IUserRestService{
	
}
