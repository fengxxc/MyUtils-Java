package test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class T {
	public static void main(String[] args) {
//		String string = "";
//		String[] split = string.split(",");
//		System.out.println(Arrays.toString(split));
		
//		float percent = (float) (21/23.0 * 100);
//		System.out.println("21/23 * 100=");
//		System.out.println(percent);
//		
//		BigDecimal percent2 = new BigDecimal(percent).setScale(2, RoundingMode.UP);
//		System.out.println(percent2.doubleValue());
//		
//		System.out.println(String.format("%.2f", percent));
//		
//		String pre = "{{";
//		String suf = "}}";
//		String _pre = ESC2Regex(pre);
//		String _suf = ESC2Regex(suf);
//		System.out.println(_pre);
//		System.out.println(_suf);
//		String str = _pre+"[A-Za-z0-9]+"+_suf;
//		String exp = str;
//		System.out.println(exp);
//		matchStringByRegularExpression("48-{{02}}*2+{{05}}", exp);
		
		
	}
	
	private static String ESC2Regex(String keyword) {
		String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
        for (String key : fbsArr) {  
            if (keyword.contains(key)) {  
                keyword = keyword.replace(key, "\\" + key);
            }  
        }
        return keyword;
	}

	public static void matchStringByRegularExpression( String parent,String child ) {
		 System.out.println("原："+parent);
        int count = 0;
        Pattern p = Pattern.compile( child );
        Matcher m = p.matcher(parent);
        while( m.find() ) {
            count++;
            System.out.println( "匹配项" + count+"：" + m.group() ); //group方法返回由以前匹配操作所匹配的输入子序列。
            parent = parent.replace(m.group(), "7");
        }
        System.out.println( "匹配个数为"+count );                              //结果输出
        System.out.println("替换成了："+parent);
    }
}
