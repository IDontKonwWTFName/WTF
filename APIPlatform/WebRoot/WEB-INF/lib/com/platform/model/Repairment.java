package com.platform.model;

import java.util.Date;

public class Repairment
{
	private Integer repairment_id;

	public void setRepairment_id(Integer repairment_id)
	{
		this.repairment_id = repairment_id;
	}

	public Integer  getRepairment_id()
	{
		return repairment_id;
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


	private Date repair_time;

	public void setRepair_time(Date repair_time)
	{
		this.repair_time = repair_time;
	}

	public Date  getRepair_time()
	{
		return repair_time;
	}


	private String repair_info;

	public void setRepair_info(String repair_info)
	{
		this.repair_info = repair_info;
	}

	public String  getRepair_info()
	{
		return repair_info;
	}


	private Integer repair_payment;

	public void setRepair_payment(Integer repair_payment)
	{
		this.repair_payment = repair_payment;
	}

	public Integer  getRepair_payment()
	{
		return repair_payment;
	}


}
