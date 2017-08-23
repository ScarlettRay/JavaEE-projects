package com.ukefu.webim.service.cache.hazelcast;

import com.ukefu.core.UKDataContext;
import com.ukefu.webim.service.cache.CacheBean;
import com.ukefu.webim.service.cache.CacheInstance;
import com.ukefu.webim.service.cache.hazelcast.impl.AgentStatusCache;
import com.ukefu.webim.service.cache.hazelcast.impl.AgentUserCache;
import com.ukefu.webim.service.cache.hazelcast.impl.ApiUserCache;
import com.ukefu.webim.service.cache.hazelcast.impl.CallCenterCache;
import com.ukefu.webim.service.cache.hazelcast.impl.MultiCache;
import com.ukefu.webim.service.cache.hazelcast.impl.OnlineCache;
import com.ukefu.webim.service.cache.hazelcast.impl.SystemCache;
/**
 * Hazlcast缓存处理实例类
 * @author admin
 *
 */
public class HazlcastCacheHelper implements CacheInstance{
	/**
	 * 服务类型枚举
	 * @author admin
	 *
	 */
	public enum CacheServiceEnum{
		HAZLCAST_CLUSTER_AGENT_USER_CACHE, HAZLCAST_CLUSTER_AGENT_STATUS_CACHE, HAZLCAST_CLUSTER_QUENE_USER_CACHE,HAZLCAST_ONLINE_CACHE , HAZLCAST_CULUSTER_SYSTEM , HAZLCAST_IMR_CACHE , API_USER_CACHE , CALLCENTER_CURRENT_CALL;
		public String toString(){
			return super.toString().toLowerCase();
		}
	}
	
	@Override
	public CacheBean getAgentStatusCacheBean() {
		// TODO Auto-generated method stub
		return UKDataContext.getContext().getBean(AgentStatusCache.class).getCacheInstance(CacheServiceEnum.HAZLCAST_CLUSTER_AGENT_STATUS_CACHE.toString()) ;
	}
	@Override
	public CacheBean getAgentUserCacheBean() {
		// TODO Auto-generated method stub
		return UKDataContext.getContext().getBean(AgentUserCache.class).getCacheInstance(CacheServiceEnum.HAZLCAST_CLUSTER_QUENE_USER_CACHE.toString()) ;
	}
	@Override
	public CacheBean getOnlineCacheBean() {
		return UKDataContext.getContext().getBean(OnlineCache.class).getCacheInstance(CacheServiceEnum.HAZLCAST_ONLINE_CACHE.toString()) ;
	}
	@Override
	public CacheBean getSystemCacheBean() {
		return UKDataContext.getContext().getBean(SystemCache.class).getCacheInstance(CacheServiceEnum.HAZLCAST_CULUSTER_SYSTEM.toString()) ;
	}
	@Override
	public CacheBean getIMRCacheBean() {
		return UKDataContext.getContext().getBean(MultiCache.class).getCacheInstance(CacheServiceEnum.HAZLCAST_IMR_CACHE.toString()) ;
	}
	@Override
	public CacheBean getCallCenterCacheBean() {
		return UKDataContext.getContext().getBean(CallCenterCache.class).getCacheInstance(CacheServiceEnum.CALLCENTER_CURRENT_CALL.toString()) ;
	}
	@Override
	public CacheBean getApiUserCacheBean() {
		return UKDataContext.getContext().getBean(ApiUserCache.class).getCacheInstance(CacheServiceEnum.API_USER_CACHE.toString()) ;
	}
}
