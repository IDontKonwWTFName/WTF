package sepim.server.clients;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.Shouhuan_log;
import com.platform.model.User_log;

public class WriteSql 
{
	//外部获取全局资源对象
	private static final WriteSql writeSql = new WriteSql();
	public static WriteSql getWritesql() {
		return writeSql;
	}
	//User_log_id,user_id,time,event,log_type
	public void WriteIntoUserInfo(String user_id,String event)
	{
		//写入数据库
		Date now = new Date();
		
	    SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
        Session session =sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
		
        User_log user_log = new User_log();
        
        user_log.setUser_id(user_id);
        user_log.setTime(now);
        user_log.setEvent(event);
        user_log.setLog_type(1);
		session.save(user_log);
		transaction.commit();
		
		session.close();
		sessionFactory.close();
		
	}
	public void WriteIntoRingInfo(String ringId,String event)
	{
		//写入数据库
		Date now = new Date();
		
	    SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
        Session session =sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
		
        Shouhuan_log shouhuan_log = new Shouhuan_log();
        
        shouhuan_log.setShouhuan_id(ringId);
        shouhuan_log.setTime(now);
        shouhuan_log.setEvent(event);
        shouhuan_log.setLog_type(1);
		session.save(shouhuan_log);
		transaction.commit();
		
		session.close();
		sessionFactory.close();
	}
}
