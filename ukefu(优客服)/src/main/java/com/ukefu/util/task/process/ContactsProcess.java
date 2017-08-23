package com.ukefu.util.task.process;

import com.ukefu.webim.service.repository.ContactsRepository;
import com.ukefu.webim.web.model.Contacts;

public class ContactsProcess implements JPAProcess{
	
	private ContactsRepository contactsRes ;
	
	public ContactsProcess(ContactsRepository contactsRes){
		this.contactsRes = contactsRes ;
	}

	@Override
	public void process(Object data) {
		contactsRes.save((Contacts)data) ;
	}

}
