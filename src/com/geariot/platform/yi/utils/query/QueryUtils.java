package com.geariot.platform.yi.utils.query;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * query生成辅助
 * @author huaqing
 *
 */
public class QueryUtils {

	private static final Logger logger = Logger.getLogger(QueryUtils.class);

	public static Query createLocalQuery(Session session, StringBuffer basicQueryStr, String creatorStr)
	{
		Query query;
		String prefix = " and ";
		if(basicQueryStr.indexOf("where") == -1)
			prefix = " where ";
		if(creatorStr != null && !creatorStr.isEmpty())
		{
			basicQueryStr.append(prefix);
			basicQueryStr.append(creatorStr);
		}
		logger.info("复合查询sql:" + basicQueryStr.toString());
		query = session.createQuery(basicQueryStr.toString());
		return query;
	}
}
