package com.geariot.platform.freelycar.wxutils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	
	/**
	 * 将map包含的参数封装成xml格式的字符串
	 * @param map
	 * @return
	 */
	public static String getXmlFromMap(Map<String, Object> map) {
		Object[] key_arr = map.keySet().toArray();
		String xml = "<xml>";
		for (Object key : key_arr){
			xml += "<"+ key + ">" + map.get(key) + "</" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}

	/**
	 * 将xml格式的字符串封装成对应键值对形式的map
	 * @param xmlString
	 * @return
	 */
	public static Map<String, Object> getMapFromXML(String xmlString) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = getStringStream(xmlString);
			if (is == null) {
				return map;
			}
			Document document = builder.parse(is);

			// 获取到document里面的全部结点
			NodeList allNodes = document.getFirstChild().getChildNodes();
			Node node;
			int i = 0;
			while (i < allNodes.getLength()) {
				node = allNodes.item(i);
				if (node instanceof Element) {
					map.put(node.getNodeName(), node.getTextContent());
				}
				i++;
			}
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;

	}

	public static InputStream getStringStream(String sInputString) {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return tInputStringStream;
	}
	
	/* 将请求转成xml格式的字符串 */
	public static Map<String, Object> requestToXml(HttpServletRequest request) {
		try {
			StringBuffer sb = new StringBuffer(1000);
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
			return getMapFromXML(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
