package com.elong.nbapi.prod.logentity.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.elong.nb.common.util.CommonsUtil;

public class CategoryUtil {

	private static Map<String, String> m = null;

	static {
		Properties p = CommonsUtil
				.loadProperties("conf/custom/env/agentCategory.properties");
		m = new HashMap(p);
	}

	public static String category(String username) {
		if (m.containsKey(username))
			return m.get(username).toString();
		else
			return "其他";
	}

	public static void main(String[] args){
		Properties p = CommonsUtil
				.loadProperties("conf/custom/env/agentCategory.properties");
		Collection s = p.values();
		System.out.println(s.iterator().next());
	}
}
