package com.platform.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.hibernate.cfg.Configuration;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

import sepim.server.clients.World;
import sipim.server.function.SocketConnectNetty;

import com.platform.model.Fence;
import com.platform.model.Historylocation;
import com.platform.model.Shouhuan;
import com.platform.model.StepNumHistory;

/**
 * * @author ���� E-mail: * @date ����ʱ�䣺2015��7��23�� ����6:32:52 * @version 1.0 * @parameter
 * * @since * @return
 */
// post �����λ����Ϣ

@WebServlet("/getshouhuanlatestinfo")
public class GetShouhuanLatestInfoServlet extends HttpServlet {

	//�õ��ֻ�������Ϣ,�л��ֻ�
	//getshouhuanlatestinfo
	//����:post
	//shouhuan_id
	//user_id
	//���أ�location(XX,XX),bat(XX),online(0/1),gprs(XX)
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String shouhuan_id = request.getParameter("shouhuan_id");
		String user_id=request.getParameter("user_id");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Map<String, String> data = new HashMap<String, String>();
		try {
			SQLQuery sqlQuery = session
					.createSQLQuery(
							"select * from dbo.[historylocation] h where shouhuan_id =:shouhuan_id and time =( select max(time)as t from dbo.[historylocation] h where shouhuan_id =:shouhuan_id )")
					.addEntity(Historylocation.class);
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			Historylocation historylocation = (Historylocation) sqlQuery
					.uniqueResult();
			//�õ�fence
			SQLQuery sqlQuery2 = session.createSQLQuery(
					"select * from dbo.[fence] where shouhuan_id=:shouhuan_id")
					.addEntity(Fence.class);
			sqlQuery2.setString("shouhuan_id", shouhuan_id);
			List<Fence> fences = sqlQuery2.list();
			// List to JSONArray
			JSONArray jsonArray = new JSONArray().fromObject(fences);

			data.put("code", "100");
			data.put("msg", "λ����Ϣ");
			if(historylocation!=null)
			{
				data.put("location", historylocation.getLng() + ","
						+ historylocation.getLat() );
			}
			else {
				data.put("location", "" );
			}
			data.put("bat", "75");
			
			/*������޸ģ��ж��ֻ��Ƿ�����*/
			int flag=0;
			if(World.getWorld().getRingLkTimeMap().get(shouhuan_id) != null)
			{
				flag=1;
			}
			data.put("online", flag+"");
			data.put("gprs", "89");
			data.put("fence", jsonArray.toString());
			//���Ҽǲ���
			String step_num = null;	
			Date nowTime = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat(
					"yyyy-MM-dd");
			String Time = dateFormat1.format(nowTime);					
			
			SQLQuery sqlQuery1 =session.createSQLQuery("select * from dbo.[StepNumHistory] where shouhuan_id=:shouhuan_id and convert(varchar(10),time,23) =:time").addEntity(StepNumHistory.class);
			sqlQuery1.setString("shouhuan_id", shouhuan_id);
			sqlQuery1.setString("time", Time);
			
			StepNumHistory stepNumHistory= (StepNumHistory) sqlQuery1.uniqueResult();
			if(stepNumHistory!=null)
			{
				step_num =stepNumHistory.getStepNum()+"";
			}
			else {
				step_num =0+"";
			}
			data.put("step_num", step_num);
			//��CR
			String companyString="LJ";
			String cmdString = "CR";
			// nettyParameter=parameter;
			// len��һ��00XX���͵�ʮ��������
			int l = cmdString.length() + user_id.length();
			String len = String.format("%04x", l);

			String nettyString = "[" + companyString + "*" + shouhuan_id + "*" + len
					+ "*" + cmdString + "]" + user_id;

			// ����netty
			new SocketConnectNetty().connect(user_id, nettyString);

			

		} catch (Exception e) {
			// TODO: handle exception

			data.put("code", "500");
			data.put("msg", "system error!");
			data.put("data", "");
		}
		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(JSONObject.fromObject(data).toString());
	}

}
 