package com.ukefu.util.task.process;

import com.ukefu.webim.service.repository.EntCustomerRepository;
import com.ukefu.webim.web.model.EntCustomer;

public class EntCustomerProcess implements JPAProcess{
	
	private EntCustomerRepository entCustomerRes ;
	
	public EntCustomerProcess(EntCustomerRepository entCustomerRes){
		this.entCustomerRes = entCustomerRes ;
	}

	@Override
	public void process(Object data) {
		entCustomerRes.save((EntCustomer)data) ;
	}

}
