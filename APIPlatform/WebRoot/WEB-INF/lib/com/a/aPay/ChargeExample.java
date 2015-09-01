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
 * Charge �������ʾ��
 * @author sunkai
 * 
 * ��ʵ��������ʾ����δ� ping++ ��������� charge ����ѯ charge��
 * 
 * ��������Ҫ��д apiKey �� appId , apiKey ������ ping++ ����ƽ̨��Ӧ����Ϣ����鿴��
 * 
 * apiKey �� TestKey �� LiveKey ���֡� 
 * 
 * TestKey ģʽ�²��������ʵ�Ľ��ס�
 */
public class ChargeExample {

	/**
	 * pingpp ����ƽ̨��Ӧ�� API key
	 */
	public static String apiKey = "sk_test_qX9u9G4avL88rf9K0G5yDSKG";
	/**
	 * pingpp ����ƽ̨��Ӧ��Ӧ�� ID
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
		//֧����Ϣ
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

		// pay��Ϣ
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
		// user_id+ʱ��

		chargeMap.put("order_no", order_no);
		chargeMap.put("channel", "alipay");
		chargeMap.put("client_ip", "127.0.0.1");
		chargeMap.put("metadata", "");
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", appId);
		chargeMap.put("app", app);
		try {
			// ����������
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
			data.put("msg", "ʧ��");
			data.put("data", "");

		}

		// �ﵽcharge

//        Pingpp.apiKey = apiKey;
//        ChargeExample ce = new ChargeExample();
//        System.out.println("---------���� charge");
//        Charge charge = ce.charge();
//        System.out.println("---------��ѯ charge");
//        ce.retrieve(charge.getId());
//        System.out.println("---------��ѯ charge�б�");
//        ce.all();
    }

    /**
     * ���� Charge
     * 
     * ���� Charge �û���Ҫ��װһ�� map ������Ϊ�������ݸ� Charge.create();
     * map ��������ľ���˵����ο���https://pingxx.com/document/api#api-c-new
     * @return
     */
    public Charge charge() {
    	
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "Your Subject");
        chargeMap.put("body", "Your Body");
        //user_id+ʱ��
        chargeMap.put("order_no", "123456789");
        chargeMap.put("channel", "alipay");
        chargeMap.put("client_ip", "127.0.0.1");
        Map<String, String> app = new HashMap<String, String>();
        app.put("id",appId);
        chargeMap.put("app", app);
        try {
            //����������
            charge = Charge.create(chargeMap);
            System.out.println(charge);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }

    /**
     * ��ѯ Charge
     * 
     * �ýӿڸ��� charge Id ��ѯ��Ӧ�� charge ��
     * �ο��ĵ���https://pingxx.com/document/api#api-c-inquiry
     * 
     * �ýӿڿ��Դ���һ�� expand �� ���ص� charge �е� app ���� app ����
     * �ο��ĵ��� https://pingxx.com/document/api#api-expanding
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
     * ��ҳ��ѯCharge
     * 
     * �ýӿ�Ϊ������ѯ�ӿڣ�Ĭ��һ�β�ѯ10����
     * �û�����ͨ����� limit �����������ò�ѯ��Ŀ�����һ�β��ܳ��� 100 ����
     * 
     * �ýӿ�ͬ������ʹ�� expand ������
     * @return
     */
    public ChargeCollection all() {
        ChargeCollection chargeCollection = null;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("limit", 3);

//���Ӵ˴���ʩ�������ȡapp expande 
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
