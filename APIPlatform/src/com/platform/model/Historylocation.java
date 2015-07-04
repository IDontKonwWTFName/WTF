package com.platform.model;

import java.io.Serializable;
import java.util.*;

public class Historylocation implements Serializable
{
	private String shouhuan_id;

	public void setShouhuan_id(String shouhuan_id)
	{
		this.shouhuan_id = shouhuan_id;
	}

	public String  getShouhuan_id()
	{
		return shouhuan_id;
	}


	private Double lng;

	public void setLng(Double lng)
	{
		this.lng = lng;
	}

	public Double  getLng()
	{
		return lng;
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


	private Double lat;

	public void setLat(Double lat)
	{
		this.lat = lat;
	}

	public Double  getLat()
	{
		return lat;
	}


}
