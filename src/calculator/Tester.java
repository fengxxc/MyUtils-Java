package calculator;

import org.junit.Test;

public class Tester {
	private static String NaN = "NaN";
	
	private void test(String expr, String expe) {
		double result = Calculator.conversion(expr);
		double dExpe;
		if (NaN.equals(expe))
			dExpe = 0.0 / 0.0;
		else
			dExpe = Double.valueOf(expe);
		System.out.println("--------------------------------");
		System.out.println("正确结果：" + expr + " = " + dExpe);
		System.out.println("测试结果：" + expr + " = " + result);
	}
	
	@Test
	public void testCase1() {
		String expression = "(0*1-3)-5/-4-(3*(-2.13))";
		String expect = "4.64";
		test(expression, expect);
	}
	
	@Test
	public void testCase2() {
		String expression = "-7+3*2";
		String expect = "-1";
		test(expression, expect);
	}
	
	@Test
	public void testCase3() {
		String expression = "-2+-1*(-3E-2)-(-1)";
		String expect = "-0.97";
		test(expression, expect);
	}
	
	@Test
	public void testCase4() {
		String expression = "1/0";
		String expect = NaN;
		test(expression, expect);
	}
	
	@Test
	public void testCase5() {
		String expression = "+3-1";
		String expect = NaN;
		test(expression, expect);
	}
	
	@Test
	public void testCase6() {
		String expression = "3--3";
		String expect = "6";
		test(expression, expect);
	}
	
	@Test
	public void testCase7() {
		String expression = "3--(3)";
		String expect = NaN;
		test(expression, expect);
	}
	
	@Test
	public void testCase8() {
		String expression = "10/3";
		String expect = "3.3333333333333333";
		test(expression, expect);
	}
	
	@Test
	public void testCase9() {
		String expression = "1/7";
		String expect = "0.14285714285714285714285714285714";
		test(expression, expect);
	}
}
