package com.a.aPay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.a.aPay.ChargeExample;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;

/** * @author  作者 E-mail: * @date 创建时间：2015年8月7日 下午2:01:05 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/pay")
public class PayServlet extends HttpServlet{
	@Override
	//支付
	//pay post
	//user_id,shouhuan_id,service_type,
	//返回Charge
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Pingpp.apiKey = "sk_test_qX9u9G4avL88rf9K0G5yDSKG";	
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		Map<String, String>data=new HashMap<String, String>();		
		String user_id =request.getParameter("user_id");
		String shouhuan_id=request.getParameter("shouhuan_id");
		String service_type=request.getParameter("service_type");
		
		//达到charge
		ChargeExample chargeExample=new ChargeExample();
		 Charge charge = chargeExample.charge();
		
		data.put("code", "100");
		data.put("msg", "zhifu");
		data.put("data", charge.toString());
		
		response.getWriter().print(JSONObject.fromObject(data).toString());
		System.out.println(charge.toString());
		
		
		
		
	}

}
