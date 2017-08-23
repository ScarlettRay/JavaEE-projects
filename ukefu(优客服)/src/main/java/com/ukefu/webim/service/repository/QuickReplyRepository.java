package com.ukefu.webim.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ukefu.webim.service.repository.es.QuickReplyEsCommonRepository;
import com.ukefu.webim.web.model.QuickReply;

public interface QuickReplyRepository extends  ElasticsearchRepository<QuickReply, String> , QuickReplyEsCommonRepository {
	
}
