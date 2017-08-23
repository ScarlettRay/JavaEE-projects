package com.ukefu.webim.service.quene;

import org.apache.commons.lang.StringUtils;

import com.hazelcast.mapreduce.KeyPredicate;
import com.ukefu.webim.service.cache.CacheHelper;
import com.ukefu.webim.web.model.AgentStatus;

@SuppressWarnings("deprecation")
public class AgentStatusOrgiFilter implements KeyPredicate<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1236581634096258855L;
	private String orgi ;
	/**
	 * 
	 */
	public AgentStatusOrgiFilter(String orgi){
		this.orgi = orgi ;
	}
	public boolean evaluate(String key) {
		AgentStatus agent = (AgentStatus) CacheHelper.getAgentStatusCacheBean().getCacheObject(key, orgi);
		return !StringUtils.isBlank(orgi) && orgi.equals(agent.getOrgi());
	}
}