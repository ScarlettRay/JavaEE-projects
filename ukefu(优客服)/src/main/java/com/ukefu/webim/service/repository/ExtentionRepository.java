package com.ukefu.webim.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ukefu.webim.web.model.Extention;

public interface ExtentionRepository extends JpaRepository<Extention, String> {
	
	public Extention findByIdAndOrgi(String id , String orgi);
	public List<Extention> findByHostidAndOrgi(String hostid , String orgi);
	
	public List<Extention> findByExtentionAndOrgi(String extention, String orgi);
	
	public List<Extention> findByExtypeAndOrgi(String type, String orgi);
	
	public int countByExtentionAndHostidAndOrgi(String extention ,String hosti, String orgi) ;
	
}
