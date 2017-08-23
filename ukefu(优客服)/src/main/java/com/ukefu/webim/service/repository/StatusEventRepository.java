package com.ukefu.webim.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.StatusEvent;

public interface StatusEventRepository extends JpaRepository<StatusEvent, String> {

	public StatusEvent findById(String id);
	
	public Page<StatusEvent> findByAni(String ani , Pageable page) ;
	
	public Page<StatusEvent> findByOrgi(String orgi , Pageable page) ;
	
	public Page<StatusEvent> findByServicestatusAndOrgi(String servicestatus ,String orgi , Pageable page) ;
	
	public Page<StatusEvent> findByMisscallAndOrgi(boolean misscall ,String orgi , Pageable page) ;
	
	public Page<StatusEvent> findByRecordAndOrgi(boolean record ,String orgi , Pageable page) ;
	
	public Page<StatusEvent> findByCalledAndOrgi(String voicemail ,String orgi , Pageable page) ;
	
	public Page<StatusEvent> findAll(Specification<StatusEvent> spec, Pageable pageable);  //分页按条件查询 

	public int countByAgent(String agent) ;
}
