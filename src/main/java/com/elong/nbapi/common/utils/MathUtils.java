/**   
 * @(#)MathUtils.java	2016年7月4日	下午3:49:03	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nbapi.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.math.NumberUtils;

import com.elong.nbapi.statistic.constants.Const;

/**
 * (类型功能说明描述)
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年7月4日 下午3:49:03   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
public class MathUtils {

	public static void main(String[] args) {
		Integer aa = null;
		Integer bb = 12;
		String a = percent(aa, bb);
		System.out.println(a);
	}

	public static String percent(Integer a, Integer b) {
		if (b == null || NumberUtils.INTEGER_ZERO.equals(b))
			return Const.BLANK;
		if (a == null) {
			a = NumberUtils.INTEGER_ZERO;
		}
		NumberFormat formatter = new DecimalFormat("0.00");
		return formatter.format((((double) a / b)) * 100) + "%";
	}

	public static String percent(Long a, Long b) {
		if (b == null || NumberUtils.LONG_ZERO.equals(b))
			return null;
		if (a == null) {
			a = NumberUtils.LONG_ZERO;
		}
		NumberFormat formatter = new DecimalFormat("0.00");
		return formatter.format((((double) a / b) - 1) * 100) + "%";
	}

	public static Double percentD(Integer a, Integer b) {
		if (b == null || NumberUtils.INTEGER_ZERO.equals(b))
			return NumberUtils.DOUBLE_ZERO;
		if (a == null) {
			a = NumberUtils.INTEGER_ZERO;
		}
		NumberFormat formatter = new DecimalFormat("0.0000");
		return NumberUtils.createDouble(formatter.format(((double) a / b)));
	}

	public static Double percentD(Long a, Long b) {
		if (b == null || NumberUtils.LONG_ZERO.equals(b))
			return NumberUtils.DOUBLE_ZERO;
		if (a == null) {
			a = NumberUtils.LONG_ZERO;
		}
		NumberFormat formatter = new DecimalFormat("0.0000");
		return NumberUtils.createDouble(formatter.format((((double) a / b) - 1)));
	}

}
