package com.platform.model;

import java.util.Date;

public class Shouhuan_log
{
	private Integer log_type;

	public void setLog_type(Integer log_type)
	{
		this.log_type = log_type;
	}

	public Integer  getLog_type()
	{
		return log_type;
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


	private Integer shouhuan_log_id;

	public void setShouhuan_log_id(Integer shouhuan_log_id)
	{
		this.shouhuan_log_id = shouhuan_log_id;
	}

	public Integer  getShouhuan_log_id()
	{
		return shouhuan_log_id;
	}


	private Date time;

	public void setTime(Date time)
	{
		this.time = time;
	}

	public Date getTime()
	{
		return time;
	}


	private String event;

	public void setEvent(String event)
	{
		this.event = event;
	}

	public String  getEvent()
	{
		return event;
	}


}
