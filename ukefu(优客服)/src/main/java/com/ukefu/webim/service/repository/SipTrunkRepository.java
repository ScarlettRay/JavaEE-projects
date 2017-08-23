package com.ukefu.webim.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.SipTrunk;

public interface SipTrunkRepository extends JpaRepository<SipTrunk, String> {
	
	public SipTrunk findByIdAndOrgi(String id , String orgi);
	public List<SipTrunk> findByHostidAndOrgi(String hostid , String orgi);
	public int countByNameAndOrgi(String name, String orgi);
}
