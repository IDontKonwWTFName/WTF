package com.platform.model;

import java.io.Serializable;

public class Relation implements Serializable
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


	private String user_id;

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String  getUser_id()
	{
		return user_id;
	}


	private Integer power;

	public void setPower(Integer power)
	{
		this.power = power;
	}

	public Integer  getPower()
	{
		return power;
	}


	private Integer administor;

	public void setAdministor(Integer administor)
	{
		this.administor = administor;
	}

	public Integer  getAdministor()
	{
		return administor;
	}


	private String relation;

	public void setRelation(String relation)
	{
		this.relation = relation;
	}

	public String  getRelation()
	{
		return relation;
	}


}
