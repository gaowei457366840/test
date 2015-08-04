package com.ringtop.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtil {
	public UnicodeUtil() {

	}

	public static void main(String[] args) {
		//String unicode = UnicodeUtil.convert("567$%^电话机A1");
		//System.out.println(unicode.length() + "---" + unicode);
		//System.out.println(UnicodeUtil.decodeUnicode(unicode));
		//System.out.println();
	}

	public static String convert(String str) {
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c > 255) {
				sb.append("\\u");
				j = (c >>> 8);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
				j = (c & 0xFF);
				tmp = Integer.toHexString(j);
				if (tmp.length() == 1)
					sb.append("0");
				sb.append(tmp);
			} else {
				sb.append(c);
			}

		}
		return (new String(sb));
	}

	public static String decodeUnicode(String dataStr) {
		Pattern p = Pattern.compile("(\\\\u([a-fA-F0-9]{4}))\\s*");
		Matcher m = p.matcher(dataStr);
		while (m.find()) {
			char letter = (char) Integer.parseInt(m.group(2), 16);
			dataStr = dataStr.replace(m.group(1), new Character(letter).toString());
		}
		return dataStr;
	}

}
