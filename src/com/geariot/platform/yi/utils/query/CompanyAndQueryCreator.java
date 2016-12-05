package com.geariot.platform.yi.utils.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.geariot.platform.yi.entities.Company;

public class CompanyAndQueryCreator  extends AndQueryCreator{
	protected void init() {
		conditionKeys = new ArrayList<ConBean>();
		ConBean bean1 = new ConBean("name", "like");
		ConBean bean2 = new ConBean("person", "like");
		ConBean bean3 = new ConBean("require", "like");
		ConBean bean4 = new ConBean("phone", "like");

		conditionKeys.add(bean1);
		conditionKeys.add(bean2);
		conditionKeys.add(bean3);
		conditionKeys.add(bean4);

		List<String> conditionValueLs = new ArrayList<String>();
		Company stu = (Company) obj;
		if (stu != null) {
			Class<?> demo = null;
			try {
				demo = Class.forName("com.geariot.platform.yi.entities.Company");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (ConBean bean : conditionKeys) {
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

	public CompanyAndQueryCreator(String... con) {
		// TODO Auto-generated constructor stub
		super(con);
	}

	public CompanyAndQueryCreator(Company stu) {
		// TODO Auto-generated constructor stub
		super(stu);
	}
}
