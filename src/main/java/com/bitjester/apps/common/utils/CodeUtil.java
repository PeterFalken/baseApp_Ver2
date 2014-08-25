package com.bitjester.apps.common.utils;

import java.util.Random;

public abstract class CodeUtil {
	public static Random r;
	
	public static String generateCode(char z) {
		if (null == r)
			r = new Random();
		String base = "xxxx-xxx" + z + "-" + z + "xxx-xxxx";
		return base.replace("x", Integer.toHexString(r.nextInt(16))).toUpperCase();
	}
}
