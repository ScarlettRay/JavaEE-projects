package com.ukefu.util.extra;

import com.ukefu.webim.web.model.PbxHost;

public interface CallCenterInterface {
	public void init(PbxHost pbxHost) throws Exception ;
	public void remove(String id) ;
	public boolean connected(String id) ;
}
