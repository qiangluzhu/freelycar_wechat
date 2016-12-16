package com.geariot.platform.yi.utils.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.geariot.platform.yi.entities.Community;

public class TestQueryCreator  extends TestAndOrQueryCreator{
	protected void init() {
		conditionKeys = new ArrayList<TestConBean>();
		String[] keys={"name","intro"};
		String[] opers={"like","like"};
		 
		for(int i=0;i<opers.length;i++){
			TestConBean bean=new TestConBean(keys[i],opers[i]);
			conditionKeys.add(bean);
		}


		List<String> conditionValueLs = new ArrayList<String>();
		Community stu = (Community) obj;
		if (stu != null) {
			Class<?> demo = null;
			try {
				demo = Class.forName("com.geariot.platform.yi.entities.Community");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			for (TestConBean bean : conditionKeys) {
				String methodName = appendMethodName(bean.getKey());
				Method method = null;
				String conditionValue = null;
				try {
					method = demo.getMethod(methodName);
					Object valueObj = method.invoke(stu);
					if (valueObj instanceof Integer)
						conditionValue = String.valueOf(valueObj);
					else
						conditionValue = (String) valueObj;
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conditionValueLs.add(conditionValue);
			}
			condition = (String[]) conditionValueLs
					.toArray(new String[conditionValueLs.size()]);
		}
	}


	public TestQueryCreator(Community stu,String[] ways) {
		// TODO Auto-generated constructor stub
		super(stu, ways);
	}
}
