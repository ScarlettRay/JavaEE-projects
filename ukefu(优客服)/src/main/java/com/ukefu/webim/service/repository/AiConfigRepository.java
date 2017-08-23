package com.ukefu.webim.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.AiConfig;

public abstract interface AiConfigRepository  extends JpaRepository<AiConfig, String>{
	public abstract AiConfig findByOrgi(String orgi);
}

