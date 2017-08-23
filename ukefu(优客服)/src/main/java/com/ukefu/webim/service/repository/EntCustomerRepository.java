package com.ukefu.webim.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ukefu.webim.service.repository.es.EntCustomerEsCommonRepository;
import com.ukefu.webim.web.model.EntCustomer;

public interface EntCustomerRepository extends  ElasticsearchRepository<EntCustomer, String> , EntCustomerEsCommonRepository {
}
