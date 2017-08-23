package com.ukefu.webim.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ukefu.webim.service.repository.es.ContactsEsCommonRepository;
import com.ukefu.webim.web.model.Contacts;

public interface ContactsRepository extends  ElasticsearchRepository<Contacts, String> , ContactsEsCommonRepository {
}
