package com.ukefu.webim.web.handler.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ukefu.util.Menu;
import com.ukefu.webim.service.acd.ServiceQuene;
import com.ukefu.webim.util.RestResult;
import com.ukefu.webim.util.RestResultType;
import com.ukefu.webim.web.handler.Handler;

@RestController
@RequestMapping("/api/servicequene")
@Api(value = "ACD服务", description = "获取队列统计信息")
public class ApiServiceQueneController extends Handler{

	/**
	 * 返回当前队列信息
	 * @param request
	 * @return
	 */
	@RequestMapping( method = RequestMethod.GET)
	@Menu(type = "apps" , subtype = "user" , access = true)
	@ApiOperation("获取队列统计信息，包含当前队列服务中的访客数，排队人数，坐席数")
    public ResponseEntity<RestResult> list(HttpServletRequest request) {
        return new ResponseEntity<>(new RestResult(RestResultType.OK , ServiceQuene.getAgentReport(super.getOrgi(request))), HttpStatus.OK);
    }
}