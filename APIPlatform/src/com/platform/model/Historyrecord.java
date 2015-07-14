package com.platform.model;

import java.io.Serializable;
import java.util.Date;

public class Historyrecord implements Serializable
{
	private Boolean from_type;

	public void setFrom_type(Boolean from_type)
	{
		this.from_type = from_type;
	}

	public Boolean  getFrom_type()
	{
		return from_type;
	}


	private String from_id;

	public void setFrom_id(String from_id)
	{
		this.from_id = from_id;
	}

	public String  getFrom_id()
	{
		return from_id;
	}


	private String record_url;

	public void setRecord_url(String record_url)
	{
		this.record_url = record_url;
	}

	public String  getRecord_url()
	{
		return record_url;
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


	private Date time;

	public void setTime(Date time)
	{
		this.time = time;
	}

	public Date  getTime()
	{
		return time;
	}


}