package com.platform.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable
{
	private Integer service_type;

	public void setService_type(Integer service_type)
	{
		this.service_type = service_type;
	}

	public Integer  getService_type()
	{
		return service_type;
	}


	private String shouhuan_id;

	public void setShouhuan_id(String shouhuan_id)
	{
		this.shouhuan_id = shouhuan_id;
	}

	public String  getShouhuan_id()
	{
		return shouhuan_id;
	}


	private String user_id;

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String  getUser_id()
	{
		return user_id;
	}


	private Integer payment_id;

	public void setPayment_id(Integer payment_id)
	{
		this.payment_id = payment_id;
	}

	public Integer  getPayment_id()
	{
		return payment_id;
	}


	private Date end_time;

	public void setEnd_time(Date end_time)
	{
		this.end_time = end_time;
	}

	public Date  getEnd_time()
	{
		return end_time;
	}


	private Date begin_time;

	public void setBegin_time(Date begin_time)
	{
		this.begin_time = begin_time;
	}

	public Date  getBegin_time()
	{
		return begin_time;
	}


}
