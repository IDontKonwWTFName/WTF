package sepim.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.platform.model.Relation;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.net.ServerPipelineFactory;

public class Server {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Server() {
		NioServerSocketChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
		bootstrap.bind(new InetSocketAddress("182.92.67.109", 8082));
		logger.info("Ready and Listening");
		initDataBase();
	}
	
	public static void main(String args[]) {
		new Server();
	}
	
	public void initDataBase(){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		//�����ݿ��ȡ���ֻ�ID�б�
		ArrayList<String> ringIdList = new ArrayList<String>();
		try {
			SQLQuery sqlQueryRingId = session.createSQLQuery(
					"select distinct shouhuan_id from dbo.[relation]");
			List ringIdListInit = sqlQueryRingId.list();
			//ȥ��
			for(Object initRingId : ringIdListInit){
					ringIdList.add(initRingId.toString());
			}
			//�����ݿ�ͨ���ֻ�ID��ȡ���������ĺ���channelID�����ĺ���channelID�б�
			for(String shouhuan_id:ringIdList){
			SQLQuery sqlQueryChannelId = session.createSQLQuery(
					"select channel_id from dbo.[relation] r join dbo.[user_info] s on r.user_id=s.user_id where r.shouhuan_id=:shouhuan_id");
			sqlQueryChannelId.setString("shouhuan_id", shouhuan_id);
			ArrayList<String> channelIdList = (ArrayList<String>) sqlQueryChannelId.list();
			World.getWorld().getRingPhoneListMap().put(shouhuan_id, channelIdList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		
		
	}
	
}