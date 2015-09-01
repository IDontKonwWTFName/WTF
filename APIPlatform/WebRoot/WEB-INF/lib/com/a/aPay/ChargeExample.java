package com.a.aPay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.platform.model.Service;

/**
 * Charge 对象相关示例
 * @author sunkai
 * 
 * 该实例程序演示了如何从 ping++ 服务器获得 charge ，查询 charge。
 * 
 * 开发者需要填写 apiKey 和 appId , apiKey 可以在 ping++ 管理平台【应用信息里面查看】
 * 
 * apiKey 有 TestKey 和 LiveKey 两种。 
 * 
 * TestKey 模式下不会产生真实的交易。
 */
public class ChargeExample {

	/**
	 * pingpp 管理平台对应的 API key
	 */
	public static String apiKey = "sk_test_qX9u9G4avL88rf9K0G5yDSKG";
	/**
	 * pingpp 管理平台对应的应用 ID
	 */
	public static String appId = "app_fHuXvD4arnT0DCqP";
	
    public static void main(String[] args) {
    	Pingpp.apiKey = "sk_test_qX9u9G4avL88rf9K0G5yDSKG";
//    	Pingpp.apiKey = "sk_test_qX9u9G4avL88rf9K0G5yDSKG";
//		String appId = "app_fHuXvD4arnT0DCqP";
		
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/x-json");
//		
		//支付信息
		Map<String, String> data = new HashMap<String, String>();
//		String user_id = request.getParameter("user_id");
//		String shouhuan_id = request.getParameter("shouhuan_id");
//		String service_type = request.getParameter("service_type");
		String user_id="1310085132";
		String shouhuan_id ="1310085134";
		String service_type="1";
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		SQLQuery sqlQuery = session.createSQLQuery(
				"select * from dbo.[service] where service_type=:service_type ")
				.addEntity(Service.class);
		sqlQuery.setString("service_type", service_type);
		Service service = (Service) sqlQuery.uniqueResult();

		// pay信息
		Map<String, String> map = new HashMap<String,String>();
		map.put("user_id", user_id);
//		map.put("shouhuan_id", shouhuan_id);
//		map.put("service_type", service_type);
		
		//order_no
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		String dateString = simpleDateFormat.format(date);
		Random random = new Random();
		int r = random.nextInt() % 100;
		String order_no = user_id + dateString ;

		Charge charge = null;
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put("amount", service.getService_price() * 100);
		chargeMap.put("currency", "cny");
		chargeMap.put("subject", service.getService_type());
		chargeMap.put("body", service.getService_describe());
		// user_id+时间

		chargeMap.put("order_no", order_no);
		chargeMap.put("channel", "alipay");
		chargeMap.put("client_ip", "127.0.0.1");
		chargeMap.put("metadata", "");
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", appId);
		chargeMap.put("app", app);
		try {
			// 发起交易请求
			charge = Charge.create(chargeMap);
			System.out.println(charge);
			data.put("code", "100");
			data.put("msg", "zhifu");
			data.put("data", charge.toString());

			//response.getWriter().print(JSONObject.fromObject(data).toString());
			System.out.println(charge.toString());
		} catch (PingppException e) {
			e.printStackTrace();
			data.put("code", "500");
			data.put("msg", "失败");
			data.put("data", "");

		}

		// 达到charge

//        Pingpp.apiKey = apiKey;
//        ChargeExample ce = new ChargeExample();
//        System.out.println("---------创建 charge");
//        Charge charge = ce.charge();
//        System.out.println("---------查询 charge");
//        ce.retrieve(charge.getId());
//        System.out.println("---------查询 charge列表");
//        ce.all();
    }

    /**
     * 创建 Charge
     * 
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
     * @return
     */
    public Charge charge() {
    	
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "Your Subject");
        chargeMap.put("body", "Your Body");
        //user_id+时间
        chargeMap.put("order_no", "123456789");
        chargeMap.put("channel", "alipay");
        chargeMap.put("client_ip", "127.0.0.1");
        Map<String, String> app = new HashMap<String, String>();
        app.put("id",appId);
        chargeMap.put("app", app);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            System.out.println(charge);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }

    /**
     * 查询 Charge
     * 
     * 该接口根据 charge Id 查询对应的 charge 。
     * 参考文档：https://pingxx.com/document/api#api-c-inquiry
     * 
     * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。
     * 参考文档： https://pingxx.com/document/api#api-expanding
     * @param id
     */
    public void retrieve(String id) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> expande = new ArrayList<String>();
            expande.add("app");
            param.put("expand", expande);
            //Charge charge = Charge.retrieve(id);
            //Expand app
            Charge charge = Charge.retrieve(id, param);
            if (charge.getApp() instanceof App) {
                //App app = (App) charge.getApp();
                // System.out.println("App Object ,appId = " + app.getId());
            } else {
                // System.out.println("String ,appId = " + charge.getApp());
            }

            System.out.println(charge);

        } catch (PingppException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询Charge
     * 
     * 该接口为批量查询接口，默认一次查询10条。
     * 用户可以通过添加 limit 参数自行设置查询数目，最多一次不能超过 100 条。
     * 
     * 该接口同样可以使用 expand 参数。
     * @return
     */
    public ChargeCollection all() {
        ChargeCollection chargeCollection = null;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("limit", 3);

//增加此处设施，刻意获取app expande 
//        List<String> expande = new ArrayList<String>();
//        expande.add("app");
//        chargeParams.put("expand", expande);

        try {
            chargeCollection = Charge.all(chargeParams);
            System.out.println(chargeCollection);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return chargeCollection;
    }
}
