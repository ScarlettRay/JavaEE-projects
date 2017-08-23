package com.ukefu.webim.service.repository.es;

import java.util.Date;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ukefu.webim.web.model.Contacts;

public interface ContactsEsCommonRepository {
	
	public Page<Contacts> findByCreaterAndShares(String creater , String shares , boolean includeDeleteData , String q , Pageable page) ;

	public Page<Contacts> findByOrgi(String orgi, boolean includeDeleteData , String q , Pageable page) ;
	
	public Page<Contacts> findByDataAndOrgi(String orgi, String q , Pageable page) ;
	
	public Page<Contacts> findByCreaterAndShares(String creater , String shares  , Date begin , Date end, boolean includeDeleteData , String q , Pageable page) ;

	public Page<Contacts> findByCreaterAndShares(String creater, String shares,Date begin, Date end, boolean includeDeleteData,BoolQueryBuilder boolQueryBuilder, String q, Pageable page);
}
