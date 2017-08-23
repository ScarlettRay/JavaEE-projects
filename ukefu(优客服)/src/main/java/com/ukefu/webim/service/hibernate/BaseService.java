package com.ukefu.webim.service.hibernate;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService<T> {

	private SessionFactory hibernateFactory;

	@Autowired
	public BaseService(EntityManagerFactory factory) {
		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		this.hibernateFactory = factory.unwrap(SessionFactory.class);
	}
	
	
	public void save(T t){
		Session session = hibernateFactory.openSession() ; 
		try{
			session.save(t) ;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			session.flush();
			session.close();	
		}
	}
	
	@SuppressWarnings("unchecked")
	public void delete(T t){
		Session session = hibernateFactory.openSession() ; 
		try{
			t = (T) session.get(t.getClass(), BeanUtils.getProperty(t, "id")) ;
			if(t!=null){
				session.delete(t);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			session.flush();
			session.close();	
		}
	}
}