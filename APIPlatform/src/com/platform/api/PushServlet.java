package com.platform.api;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.*;

import java.util.*;
import java.io.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.platform.model.User;

	/**
	 * Servlet implementation class LoginServlet
	 */
	@WebServlet("/push")
	
public class PushServlet extends HttpServlet{
	
	
		private static final long serialVersionUID = 1L;
	       
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public PushServlet() {
	        super();
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in push");
		
			String apiKey = "GzddpTYVNK5TyphCL2eAddga";
			String secretKey = "vHoVYaUKmEzLL4F6yivggoDOkS5Dz9k1";  
			PushKeyPair pair = new PushKeyPair(apiKey,secretKey);
			try{
			BaiduPushClient pushClient = new BaiduPushClient(pair,
					BaiduPushConstants.CHANNEL_REST_URL);
			pushClient.setChannelLogHandler (new YunLogHandler () {
			    @Override
			    public void onHandle (YunLogEvent event) {
			        System.out.println(event.getMessage());
			    }
			});
			PushMsgToSingleDeviceRequest yun_request = new PushMsgToSingleDeviceRequest().
				    addChannelId("4587906591108883625").
				    addMsgExpires(new Integer(3600)).   //������Ϣ����Чʱ��,��λ��,Ĭ��3600 x 5.
				    addMessageType(1).                  //������Ϣ����,0��ʾ��Ϣ,1��ʾ֪ͨ,Ĭ��Ϊ0.
				    addMessage("{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}").
				    addDeviceType(3);          //�����豸���ͣ�3 for android, 4 for ios.
			PushMsgToSingleDeviceResponse yun_response = pushClient.
					pushMsgToSingleDevice(yun_request);
			} catch (PushClientException e) {
			    /*ERROROPTTYPE ���������쳣�Ĵ�����ʽ -- �׳��쳣�Ͳ����쳣,
			    *'true' ��ʾ�׳�, 'false' ��ʾ����
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

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			HttpSession s= request.getSession();
			String ch= s.getAttribute("channal").toString();
			String apiKey = "GzddpTYVNK5TyphCL2eAddga";
			String secretKey = "vHoVYaUKmEzLL4F6yivggoDOkS5Dz9k1";  
			PushKeyPair pair = new PushKeyPair(apiKey,secretKey);
			try{
			BaiduPushClient pushClient = new BaiduPushClient(pair,
					BaiduPushConstants.CHANNEL_REST_URL);
			pushClient.setChannelLogHandler (new YunLogHandler () {
			    @Override
			    public void onHandle (YunLogEvent event) {
			        System.out.println(event.getMessage());
			    }
			});
			PushMsgToSingleDeviceRequest yun_request = new PushMsgToSingleDeviceRequest().
				    addChannelId("4587906591108883625").
				    addMsgExpires(new Integer(3600)).   //������Ϣ����Чʱ��,��λ��,Ĭ��3600 x 5.
				    addMessageType(1).                  //������Ϣ����,0��ʾ��Ϣ,1��ʾ֪ͨ,Ĭ��Ϊ0.
				    addMessage("{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}").
				    addDeviceType(3);          //�����豸���ͣ�3 for android, 4 for ios.
			PushMsgToSingleDeviceResponse yun_response = pushClient.
					pushMsgToSingleDevice(yun_request);
			} catch (PushClientException e) {
			    /*ERROROPTTYPE ���������쳣�Ĵ�����ʽ -- �׳��쳣�Ͳ����쳣,
			    *'true' ��ʾ�׳�, 'false' ��ʾ����
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
		
		/**
		 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
		 */
		protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
		}

		/**
		 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
		 */
		protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
		}

	

}