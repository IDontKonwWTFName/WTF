package com.a.push;

import java.util.ArrayList;
import java.util.List;






import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import sepim.server.clients.World;
import net.sf.json.JSONObject;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

/**
 * * @author 作者 E-mail: * @date 创建时间：2015年7月27日 下午1:09:01 * @version 1.0 * @parameter
 * * @since * @return
 */
//带channelID,然后加推送数据（包括sign,加数据）
public class Push {
	public static void main(String arg[]) {
//		Push push=new Push();
//		List<String>ids=new ArrayList<>();
//		ids.add("3473377944743044766");
//		ids.add("4587906591108883625");
//		String data="{\"title\":\"TEST\",\"description\":\"Hello Baidu push! LJ is coming-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------!\"}";
////		push.pushToApp(ids, data);
//		push.pushToApp("3473377944743044766", data);
		// 将推送信息 加到jsonObject中
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("sign", "addRelation");
		jsonObject.put("shouhuan_id", "1506012101");
		jsonObject.put("set_userid","15045693947");
		jsonObject.put("power", 0);
		jsonObject.put("relation", "123");
		JSONObject jsonObject2 = new JSONObject();

		jsonObject2.put("title", "addRelation");
		jsonObject2.put("description", "addRelation");
		jsonObject2.put("custom_content", jsonObject.toString());
		new Push().pushToApp("4112173672680921401", jsonObject2.toString());
		
		
		
	}
	public String toChannelID(String user_id) 
	{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		String channel_id=null;
		try {
			SQLQuery sqlQuery=session.createSQLQuery("select channel_id from dbo.[user_info] where user_id=:user_id");
			sqlQuery.setString("user_id", user_id);
			channel_id=(String) sqlQuery.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return channel_id;
	}
	public void pushToApp (String id,String data) 
	{
		if(id!=null&&!id.equals(null))
		{
			System.out.println("in push"+data);
			String channelID=id;
			//String channelID="3473377944743044766";
			
			JSONObject jsonObject=new JSONObject();
			String message=data;
			System.out.println(message);
			//message="{\"title\":\"TEST\",\"description\":\"Hello Baidu push! LJ is coming!\"}";
	
			String apiKey = "GzddpTYVNK5TyphCL2eAddga";
			String secretKey = "vHoVYaUKmEzLL4F6yivggoDOkS5Dz9k1";
			PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
			try {
				BaiduPushClient pushClient = new BaiduPushClient(pair,
						BaiduPushConstants.CHANNEL_REST_URL);
				pushClient.setChannelLogHandler(new YunLogHandler() {
					@Override
					public void onHandle(YunLogEvent event) {
						System.out.println(event.getMessage());
					}
				});
				PushMsgToSingleDeviceRequest yun_request = new PushMsgToSingleDeviceRequest()
						.addChannelId(channelID)
						.addMsgExpires(new Integer(3600))
						. // 设置消息的有效时间,单位秒,默认3600 x 5.
						addMessageType(0)
						. // 设置消息类型,0表示消息,1表示通知,默认为0.
						addMessage(message)
						.addDeviceType(3); // 设置设备类型，3 for android, 4 for ios.
				PushMsgToSingleDeviceResponse yun_response = pushClient
						.pushMsgToSingleDevice(yun_request);
			} catch (PushClientException e) {
				/*
				 * ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
				 */
				if (BaiduPushConstants.ERROROPTTYPE) {
					try {
						throw e;
					} catch (PushClientException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					e.printStackTrace();
				}
			} catch (PushServerException e) {
				if (BaiduPushConstants.ERROROPTTYPE) {
					try {
						throw e;
					} catch (PushServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					System.out.println(String.format(
							"requestId: %d, errorCode: %d, errorMessage: %s",
							e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
				}
			}
		}
	}

	//参数为userID
	public void pushToApp(List<String> ids, String data) 
	{
		System.out.println("in push");
		System.out.println("链表长度："+ids.size());
		String message=data;
		System.out.println(message);
		
		String apiKey = "GzddpTYVNK5TyphCL2eAddga";
		String secretKey = "vHoVYaUKmEzLL4F6yivggoDOkS5Dz9k1";
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		//每个channelID都发一次
		for(String userId:ids)
		{
			try 
			{
				String channelIDString = World.getWorld().getPhoneChannelMap().get(userId);
				if(channelIDString!=null&&!channelIDString.equals(null))
				{
					PushMsgToSingleDeviceRequest yun_request = new PushMsgToSingleDeviceRequest()
					.addChannelId(channelIDString)
					.addMsgExpires(new Integer(3600))
					. // 设置消息的有效时间,单位秒,默认3600 x 5.
					addMessageType(0)
					. // 设置消息类型,0表示消息,1表示通知,默认为0.
					addMessage(message)
					.addDeviceType(3); // 设置设备类型，3 for android, 4 for ios.
			PushMsgToSingleDeviceResponse yun_response = pushClient
					.pushMsgToSingleDevice(yun_request);
				}
			}
			catch (PushClientException e)
			{
				/*
				 * ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
				 */
				if (BaiduPushConstants.ERROROPTTYPE) {
					try {
						throw e;
					} catch (PushClientException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					e.printStackTrace();
				}
			} catch (PushServerException e) {
				if (BaiduPushConstants.ERROROPTTYPE) {
					try {
						throw e;
					} catch (PushServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					System.out.println(String.format(
							"requestId: %d, errorCode: %d, errorMessage: %s",
							e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
				}
			}
		}
	}
}
