package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.AmbiguousResolutionException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.hql.internal.ast.SqlASTFactory;

import sepim.server.clients.World;

import com.a.push.Push;
import com.mysql.jdbc.BufferRow;
import com.platform.model.Relation;
import com.platform.model.Shouhuan;

@WebServlet("/addrelation")
// 添加手环,添加手环要先判断是否注册前有管理员，若有，则先经过同意，若无则为管理员
/** * @author  作者 E-mail: * @date 创建时间：2015年7月26日 下午6:34:21 * @version 1.0 * @parameter  * @since  * @return  */
public class AddRelationServlet extends HttpServlet {
	@Override
	//得到手环所有关注者信息
	//addrelation get  
	//shouhuan_id,user_id
	//返回所有关注者信息
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String shouhuanid = request.getParameter("shouhuan_id");

		response.setContentType("text/x-json");

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try 
		{
			SQLQuery sqlQuery = session
					.createSQLQuery(
							"select * from dbo.[relation] where shouhuan_id=:shouhuan_id")
					.addEntity(Relation.class);
			// sqlQuery.setString("pshouhuan_id", "shouhuan_id");
			sqlQuery.setString("shouhuan_id", shouhuanid);
			List<Relation> info = sqlQuery.list();

			data.put("code", "100");
			data.put("msg", "获取数据成功");
			data.put("data", JSONArray.fromObject(info).toString());

			out.println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	//addrelation post
	//shouhuan_id,set_id,relation,power,user_id
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 发送user_id和想要添加relation的信息，判断是否可以加，否则推送给管理员
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		// 数据
		String shouhuan_id = request.getParameter("shouhuan_id");
		String set_userid = request.getParameter("set_id");
		String relation = request.getParameter("relation");
//		String powerString = request.getParameter("power");
//		int power = Integer.parseInt(powerString);
		// int power=8;
		String user_id = request.getParameter("user_id");
		
		int maxPower=9;
		int midPower=4;
		int minPower=0;

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String admin_id = null;
		Relation relation2 = null;
		Map<String, String> data = new HashMap<String, String>();
		// 查询手环关联的是否有管理员，如果有，比较user_id和admin_id
		try 
		{
			SQLQuery sqlQuery = session.createSQLQuery("select user_id from dbo.[relation] where shouhuan_id=:shouhuan_id and administor=1");
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			admin_id = (String) sqlQuery.uniqueResult();//找到管理员ID
			
			System.out.println("admin_id:" + admin_id);
			
		} 
		catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("sql error");
			data.put("code", "500");
			data.put("msg", "系统错误");
		}
		// List<String> admin_id=sqlQuery.list();
		// 如果没有管理员，直接插入，并且成为管理员
		if (admin_id == null)//第一个绑定的人为管理员
		{
			try 
			{
				Relation insertRelation = new Relation();
				insertRelation.setShouhuan_id(shouhuan_id);
				insertRelation.setUser_id(set_userid);
				insertRelation.setPower(maxPower);
				insertRelation.setRelation(relation);
				insertRelation.setAdministor(1);

				session.save(insertRelation);
				transaction.commit();
				System.out.println(admin_id);
				data.put("code", "100");
				data.put("msg", "添加成功！");
				data.put("data", "");
				
				/*
				 * 2015/8/17
				 * 胡启罡添加，增减UserId和手环Id的关系
				 * */
				ArrayList<String> UserIdList = World.getWorld().getRingPhoneListMap().get(shouhuan_id);//得到已绑该手环的UserID列表
				if(!UserIdList.contains(set_userid))
				{
					UserIdList.add(set_userid);
					World.getWorld().getRingPhoneListMap().put(shouhuan_id,UserIdList);
				}
				
			} 
			catch (Exception e)
			{
				// TODO: handle exception
			}
			
		} 
		else
		{
			if (admin_id.equals(user_id))
			{
				// 如果user_id就是管理员，那么设置他推送来的set_userid为关联者
				//如果不是管理员，先加入到relation，并置为power=0
				try 
				{
					Relation insertRelation = new Relation();
					insertRelation.setShouhuan_id(shouhuan_id);
					insertRelation.setUser_id(set_userid);
					insertRelation.setPower(midPower);
					insertRelation.setRelation(relation);
					insertRelation.setAdministor(0);

					session.save(insertRelation);
					transaction.commit();
					System.out.println("1");

					data.put("code", "100");
					data.put("msg", "添加成功");
					data.put("data", "administer");
					
					/*
					 * 2015/8/17
					 * 胡启罡添加，增减UserId和手环Id的关系
					 * */
					ArrayList<String> UserIdList = World.getWorld().getRingPhoneListMap().get(shouhuan_id);//得到已绑该手环的UserID列表
					if(!UserIdList.contains(set_userid))
					{
						UserIdList.add(set_userid);
						World.getWorld().getRingPhoneListMap().put(shouhuan_id,UserIdList);
					}
					
					
					String channelID = null;

					//查找set_userid对应的channelID

					SQLQuery sqlQuery = session
							.createSQLQuery("select channel_id from dbo.[user_info] where user_id=:user_id ");
					sqlQuery.setString("user_id", set_userid);
					channelID = (String) sqlQuery.uniqueResult();
					
					
					//查找name
					String name = null;	

										
					SQLQuery sqlQuery1 =session.createSQLQuery("select * from dbo.[shouhuan] where shouhuan_id : shouhuan_id ").addEntity(Shouhuan.class);
					sqlQuery1.setString("shouhuan_id", shouhuan_id);
					@SuppressWarnings("unchecked")
					List< Shouhuan> shouhuans =sqlQuery1.list();
					for (Shouhuan s : shouhuans) 
					{
						
						name = s.getName();
					}
							
										
					// 将推送信息 加到jsonObject中
					JSONObject jsonObject = new JSONObject();

					jsonObject.put("type", "add_ring");
					jsonObject.put("shouhuan_id", shouhuan_id);
					jsonObject.put("name",name);
					
					new Push().pushToApp(channelID, jsonObject.toString());
					
				} 
				catch (Exception e) 
				{
					// TODO: handle exception
				}
				
			} 
			else
			{
				// 推送给admin，让他发过来
				// 应该 从admin_id查询channelID
				try {
										
					Relation insertRelation = new Relation();
					insertRelation.setShouhuan_id(shouhuan_id);
					insertRelation.setUser_id(set_userid);
					insertRelation.setPower(minPower);
					insertRelation.setRelation(relation);
					insertRelation.setAdministor(0);

					session.save(insertRelation);
					transaction.commit();
					
					
					String channelID = null;

					//查找admin_ID对应的channelID

					SQLQuery sqlQuery = session
							.createSQLQuery("select channel_id from dbo.[user_info] where user_id=:user_id ");
					sqlQuery.setString("user_id", admin_id);
					channelID = (String) sqlQuery.uniqueResult();
					
					
					// 将推送信息 加到jsonObject中
					JSONObject jsonObject = new JSONObject();

					jsonObject.put("sign", "addRelation");
					jsonObject.put("shouhuan_id", shouhuan_id);
					jsonObject.put("set_userid", set_userid);
					jsonObject.put("power", 0);
					jsonObject.put("relation", relation);
					JSONObject jsonObject2 = new JSONObject();

					jsonObject2.put("title", "addRelation");
					jsonObject2.put("description", "addRelation");
					jsonObject2.put("custom_content", jsonObject.toString());
					new Push().pushToApp(channelID, jsonObject2.toString());
					
					
					System.out.println("推送给管理员！");
					data.put("code", "100");
					data.put("msg", "推送给管理员");
					data.put("data", "");
	
				} catch (Exception e) {
					// TODO: handle exception
				}				
			}
		}

		response.getWriter().println(JSONObject.fromObject(data).toString());

		// if ()

	}

	// 修改relation
	// user_id 发送方
	// shouhuan_id 手环_id
	// which,修改什么,修改relation,power,administor
	// value，修改的值
	// set_id，修改的id
	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "utf-8"));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		
		JSONObject jsonObject = JSONObject.fromObject(sb.toString());
		System.out.println(jsonObject);

		String user_id = null;
		String shouhuan_id = null;
		String set_id = null;
		String which = null;
		String value = null;

		user_id = jsonObject.getString("user_id");
		shouhuan_id = jsonObject.getString("shouhuan_id");
		set_id = jsonObject.getString("set_id");
		which = jsonObject.getString("which");
		value = jsonObject.getString("value");
		

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String admin_id = null;
		Map<String, String> data = new HashMap<String, String>();
		// 如果修改relation
		if (which.equals("relation")) {
			System.out.println(which);
			try {
				SQLQuery sqlQuery2 = session
						.createSQLQuery("update dbo.[relation] set relation =:value where shouhuan_id=:shouhuan_id and user_id =:set_id");
				sqlQuery2.setString("value", value);
				sqlQuery2.setString("shouhuan_id", shouhuan_id);
				sqlQuery2.setString("set_id", set_id);
				sqlQuery2.executeUpdate();
				transaction.commit();

				data.put("code", "100");
				data.put("msg", "更改成功！");
				data.put("data", "");
				System.out.println("update+++++++++++++++++++++++++++++++");

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("---");
			}

		} else {
			System.out.println(which);
			// 查询手环关联的是否有管理员，如果有，比较user_id和admin_id
			try {
				SQLQuery sqlQuery = session
						.createSQLQuery("select user_id from dbo.[relation] where shouhuan_id=:shouhuan_id and administor=1");
				sqlQuery.setString("shouhuan_id", shouhuan_id);
				admin_id = (String) sqlQuery.uniqueResult();
				System.out.println(admin_id + "admin----------------");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("sql error");
			}
			// 修改权限
			if (which.equals("power")) {
				if (admin_id.equals(user_id)) 
				{

					SQLQuery sqlQuery2 = session
							.createSQLQuery("update dbo.[relation] set "
									+ which
									+ "= '"
									+ value
									+ "' where shouhuan_id=:shouhuan_id and user_id =:set_id");
					sqlQuery2.setString("shouhuan_id", shouhuan_id);
					sqlQuery2.setString("set_id", set_id);
					sqlQuery2.executeUpdate();

					transaction.commit();

					data.put("code", "100");
					data.put("msg", "更改成功！");
					data.put("data", "");
					System.out.println("update+++++++++++++++++++++++++++++++");
					
					/*
					 * 2015/8/17
					 * 胡启罡添加，增减UserId和手环Id的关系
					 * */
					ArrayList<String> UserIdList = World.getWorld().getRingPhoneListMap().get(shouhuan_id);//得到已绑该手环的UserID列表
					if(!UserIdList.contains(set_id))
					{
						UserIdList.add(set_id);
						World.getWorld().getRingPhoneListMap().put(shouhuan_id,UserIdList);
					}
					
									
					String channelID = null;

					//查找set_userid对应的channelID

					SQLQuery sqlQuery = session
							.createSQLQuery("select channel_id from dbo.[user_info] where user_id=:user_id ");
					sqlQuery.setString("user_id", set_id);
					channelID = (String) sqlQuery.uniqueResult();
					
					//查找name
					String name = null;	

										
					SQLQuery sqlQuery1 =session.createSQLQuery("select * from dbo.[shouhuan] where shouhuan_id=:shouhuan_id ").addEntity(Shouhuan.class);
					sqlQuery1.setString("shouhuan_id", shouhuan_id);
					@SuppressWarnings("unchecked")
					List< Shouhuan> shouhuans =sqlQuery1.list();
					for (Shouhuan s : shouhuans) 
					{
						
						name = s.getName();
					}
						
					// 将推送信息 加到jsonObject中
					jsonObject = new JSONObject();

					jsonObject.put("type", "add_ring");
					jsonObject.put("shouhuan_id", shouhuan_id);
					jsonObject.put("name",name);
					
					new Push().pushToApp(channelID, jsonObject.toString());
								
				} 
				else {
					// 如果不是管理员，推送给管理员，让管理员来修改权限
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("sign", "putrelation");

					jsonObject2.put("set_id", set_id);
					jsonObject2.put("shouhuan_id", shouhuan_id);
					jsonObject2.put("which", which);
					jsonObject2.put("value", value);

					JSONObject jsonObject_Push = new JSONObject();
					jsonObject_Push.put("title", "putRelation");
					jsonObject_Push.put("description", "putRelation");
					jsonObject_Push.put("custom_content",
							jsonObject2.toString());

					data.put("code", "200");
					data.put("msg", "推送到管理员");
					data.put("data", "");
				}

			}
			// 如果转让管理员,先修改set_id为administer置1，然后将原管理员administer置为0，为一整个事务，如果执行错误，回滚
			if (which.equals("administer")) {
				System.out.println(user_id+"--"+admin_id);
				if (admin_id.equals(user_id)) {
					try {

						SQLQuery adminSqlQuery = session
								.createSQLQuery("update dbo.[relation] set administor=1 where user_id=:set_id and shouhuan_id=:shouhuan_id");
						adminSqlQuery.setString("set_id", set_id);
						adminSqlQuery.setString("shouhuan_id", shouhuan_id);
						adminSqlQuery.executeUpdate();
//						transaction.commit();
						 SQLQuery
						 sqlQuery2=session.createSQLQuery("update dbo.[relation] set administor=0 where user_id=:user_id and shouhuan_id=:shouhuan_id");
						 sqlQuery2.setString("user_id", user_id);
						 sqlQuery2.setString("shouhuan_id", shouhuan_id);
						 sqlQuery2.executeUpdate();
						
						 transaction.commit();
						System.out.println("commit????????");
						data.put("code", "100");
						data.put("msg", "更改成功！");
						data.put("data", "");

					} catch (Exception e) {
						// TODO: handle exception
						if (transaction != null) {
							transaction.rollback();
							System.out.println("-----------------------");
						}
						data.put("code", "500");
						data.put("msg", "系统错误");
						data.put("data", "");
					}
				} else {
					data.put("code", "500");
					data.put("msg", "没有权限");
					data.put("data", "");
					System.out.println("没有权限");
				}

			}

		}

		session.close();
		sessionFactory.close();
		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(JSONObject.fromObject(data));

	}

	@Override
	// 删除手环关系
	// shouhuan_id,user_id，delete_id
	// 返回code
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String shouhuan_id = request.getParameter("shouhuan_id");
		String delete_id = request.getParameter("delete_id");
		String user_id =request.getParameter("user_id");
		System.out.println("---------"+shouhuan_id+delete_id+user_id);
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Map<String, String> data = new HashMap<String, String>();
		try {
			SQLQuery sqlQuery1=session.createSQLQuery("select user_id from dbo.[relation] where shouhuan_id=:shouhuan_id and administor=1 ");
			sqlQuery1.setString("shouhuan_id", shouhuan_id);
			String admin=(String) sqlQuery1.uniqueResult();
			System.out.println(admin);
			//如果user_id=delete_Id，并且不是管理员
			if (user_id.equals(delete_id)) {
				
				if (!admin.equals(user_id)){
					SQLQuery sqlQuery = session
							.createSQLQuery("delete  dbo.[relation] where shouhuan_id=:shouhuan_id and user_id=:user_id");
					sqlQuery.setString("shouhuan_id", shouhuan_id);
					sqlQuery.setString("user_id", delete_id);
					sqlQuery.executeUpdate();
					transaction.commit();
					data.put("code", "100");
					data.put("msg", "删除成功");
					data.put("data", "");
					
					/*
					 * 2015/8/17
					 * 胡启罡添加，增减UserId和手环Id的关系
					 * */
					ArrayList<String> UserIdList = World.getWorld().getRingPhoneListMap().get(shouhuan_id);//得到已绑该手环的UserID列表
					if(!UserIdList.contains(delete_id))
					{
						UserIdList.remove(delete_id);
						World.getWorld().getRingPhoneListMap().put(shouhuan_id,UserIdList);
					}
				}			
			}else {
				//如果是管理员，并且两个不相等
				if (admin.equals(user_id)){
					SQLQuery sqlQuery = session
							.createSQLQuery("delete  dbo.[relation] where shouhuan_id=:shouhuan_id and user_id=:user_id");
					sqlQuery.setString("shouhuan_id", shouhuan_id);
					sqlQuery.setString("user_id", delete_id);
					sqlQuery.executeUpdate();
					transaction.commit();
					data.put("code", "100");
					data.put("msg", "删除成功");
					data.put("data", "");
					
					/*
					 * 2015/8/17
					 * 胡启罡添加，增减UserId和手环Id的关系
					 * */
					ArrayList<String> UserIdList = World.getWorld().getRingPhoneListMap().get(shouhuan_id);//得到已绑该手环的UserID列表
					if(!UserIdList.contains(delete_id))
					{
						UserIdList.remove(delete_id);
						World.getWorld().getRingPhoneListMap().put(shouhuan_id,UserIdList);
					}
				
				}				
			}
			//
		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");
		}

		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(JSONObject.fromObject(data));

	}

}
