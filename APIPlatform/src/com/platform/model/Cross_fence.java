package com.platform.model;

import java.io.Serializable;
import java.util.Date;

public class Cross_fence implements Serializable
{
	private Integer In_Out;

	public void setIn_Out(Integer In_Out)
	{
		this.In_Out = In_Out;
	}

	public Integer  getIn_Out()
	{
		return In_Out;
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
	private Double lng;

	public void setLng(Double lng)
	{
		this.lng = lng;
	}

	public Double  getLng()
	{
		return lng;
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



	private Integer fence_id;

	public void setFence_id(Integer fence_id)
	{
		this.fence_id = fence_id;
	}

	public Integer  getFence_id()
	{
		return fence_id;
	}


}
