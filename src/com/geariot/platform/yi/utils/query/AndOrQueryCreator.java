package com.geariot.platform.yi.utils.query;

import java.util.List;

public class AndOrQueryCreator {
protected String[] condition ;
	
	protected List<ConBean> conditionKeys ;
	
	protected Object obj;
	
	private static final String PREFIX = "get";
	
	protected void init()
	{
		
	}
	
	public AndOrQueryCreator(String... condition)
	{
		this.condition = condition;
		this.init();
	}
	
	public AndOrQueryCreator(Object obj)
	{
		this.obj = obj;
		this.init();
	}
	
	/**
	 * @param signal 0代表and查询，1代表or查询
	 * @return
	 */
	public String createStatement(int signal)
	{
		StringBuffer content = new StringBuffer("");
		if(condition == null || condition.length == 0 )
			return "";
		for(int i=0; i< condition.length; i++)
		{
			String value = condition[i];
			if(value == null || value.isEmpty() || value.trim().isEmpty() || value.equals("-1") || value.equals("-2"))
				continue;
			else
			{
				if(i != 0 && content.length() > 0){
					if(signal==0){
						content.append(" and ");
					}else{
						content.append(" or ");
					}
				}
				content.append(conditionKeys.get(i).toString());
				if(conditionKeys.get(i).getOperator().equals("like"))
				{
					content.append("'%");
					content.append(value);
					content.append("%'");
				}
				else if(conditionKeys.get(i).getOperator().equals("=="))
				{
					content.append(Integer.parseInt(value));
					
				}
				else if(conditionKeys.get(i).getOperator().equals("=")||conditionKeys.get(i).getOperator().equals(">")||conditionKeys.get(i).getOperator().equals("<"))
				{
					content.append("'" + value + "'");
				}
				else
				{
					content.append(value);
				}
			}
		}
		return content.toString();
	}
	
	public String appendMethodName(String key)
	{
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(PREFIX);
		key = key.substring(0, 1).toUpperCase() + key.substring(1);
		buffer.append(key);
		return buffer.toString();
	}
}
