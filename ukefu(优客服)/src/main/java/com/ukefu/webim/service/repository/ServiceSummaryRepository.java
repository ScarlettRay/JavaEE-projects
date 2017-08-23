package com.ukefu.webim.service.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.AgentServiceSummary;

public interface ServiceSummaryRepository extends JpaRepository<AgentServiceSummary, String>{
	
	public abstract AgentServiceSummary findByAgentserviceidAndOrgi(String agentserviceid , String orgi);
	
	public abstract AgentServiceSummary findByIdAndOrgi(String id , String orgi) ;
	
	public abstract AgentServiceSummary findByStatuseventidAndOrgi(String statuseventid , String orgi);
	
	public abstract Page<AgentServiceSummary> findAll(Specification<AgentServiceSummary> spec, Pageable pageable);  //分页按条件查询 

	public abstract Page<AgentServiceSummary> findByChannelAndOrgi(String string, String orgi, Pageable pageable);
}
