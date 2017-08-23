package com.ukefu.webim.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.AgentUser;

public abstract interface AgentUserRepository  extends JpaRepository<AgentUser, String>
{
	public abstract AgentUser findByIdAndOrgi(String paramString, String orgi);

	public abstract List<AgentUser> findByUseridAndOrgi(String userid, String orgi);

	public abstract List<AgentUser> findByAgentnoAndOrgi(String agentno , String orgi , Sort sort);

	public abstract Page<AgentUser> findByOrgiAndStatus(String orgi ,String status , Pageable page);
	
	public abstract List<AgentUser> findByAgentnoAndStatusAndOrgi(String agentno ,String status , String orgi);
	
	public abstract int countByAgentnoAndStatusAndOrgi(String agentno ,String status , String orgi);

}
