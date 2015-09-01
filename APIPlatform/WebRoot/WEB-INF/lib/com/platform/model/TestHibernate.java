package com.platform.model;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import java.util.*;

public class TestHibernate {

	public static void main(String[] args)
	{
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		
		SQLQuery query = s.createSQLQuery("select * from cross_fence").addEntity(Cross_fence.class);
		List list = query.list();
		for(int i=0; i<list.size(); i++)
		{
			Cross_fence user = (Cross_fence) list.get(i);
			System.out.println(user.getTime());
		}
	}
}
