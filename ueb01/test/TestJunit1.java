import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import context.Context;
import expressions.ConstExpression;
import expressions.VarExpression;
import expressions.binary.AddExpression;
import expressions.binary.DivExpression;
import expressions.binary.MulExpression;
import expressions.binary.SubExpression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.IntValue;



public class TestJunit1 {

	
	@Test
	public void simpleAddExpressionWithInt() {
		int res = 0;
		int expected = 4;
		int val1 = 2;
		int val2 = 2;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		AddExpression<IntValue> myExp = new AddExpression<IntValue>(const1,const2);
					
		
		try {
			res = myExp.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}
				
		
		assertEquals(expected, res);
		
	}
	
	
	@Test
	public void complexAddExpressionWithInt() {
		int res = 0;
		int expected = 10;
		int val1 = 1;
		int val2 = 2;
		int val3 = 3;
		int val4 = 4;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
		ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));
		
		
		AddExpression<IntValue> myExp1 = new AddExpression<IntValue>(const1,const2);
		AddExpression<IntValue> myExp2 = new AddExpression<IntValue>(const3,const4);
		
		AddExpression<IntValue> myExp3 = new AddExpression<IntValue>(myExp1,myExp2);

					
		
		try {
			res = myExp3.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}
				
		
		assertEquals(expected, res);
		
	}
	
	
	@Test
	public void complexAddExpressionToStringWithInt() {
		String strRes = "((1 + 2) + (3 + 4))";

		int val1 = 1;
		int val2 = 2;
		int val3 = 3;
		int val4 = 4;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
		ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));
		
		
		AddExpression<IntValue> myExp1 = new AddExpression<IntValue>(const1,const2);
		AddExpression<IntValue> myExp2 = new AddExpression<IntValue>(const3,const4);
		
		AddExpression<IntValue> myExp3 = new AddExpression<IntValue>(myExp1,myExp2);

		
		
		assertEquals(strRes, myExp3.toString());
		
	}
	

	
	@Test
	public void simpleSubExpressionWihtInt() {
		
		int expected = 1;
		int res = 0;
		
		int val1 = 3;
		int val2 = 2;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

		SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1,const2);
		
		try {
			res = myExp1.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}

		assertEquals(expected, res);
		
	}
	
	@Test
	public void complexSubExpressionWithInt() {
		int res = 0;
		int expected = 55;
		int val1 = 100;
		int val2 = 30;
		int val3 = 20;
		int val4 = 5;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
		ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));
		
		
		SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1,const2);
		SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(const3,const4);
		
		SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1,myExp2);

					
		
		try {
			res = myExp3.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}
				
		
		assertEquals(expected, res);
		
	}
	
	@Test
	public void complexSubExpressionToStringWithInt() {
		String strRes = "((20 - 3) - (10 - 2))";

		int val1 = 20;
		int val2 = 3;
		int val3 = 10;
		int val4 = 2;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
		ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));
		
		
		SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1,const2);
		SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(const3,const4);
		
		SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1,myExp2);

		
		
		assertEquals(strRes, myExp3.toString());
		
	}
	
	@Test
	public void simpleMulExpressionWihtInt() {
		
		int expected = 6;
		int res = 0;
		
		int val1 = 3;
		int val2 = 2;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

		MulExpression<IntValue> myExp1 = new MulExpression<IntValue>(const1,const2);
		
		try {
			res = myExp1.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}

		assertEquals(expected, res);
		
	}
	
	@Test
	public void complexMulExpressionWithInt() {
		int res = 0;
		int expected = 8000;
		int val1 = 40;
		int val2 = 2;
		int val3 = 20;
		int val4 = 5;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
		ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));
		
		
		MulExpression<IntValue> myExp1 = new MulExpression<IntValue>(const1,const2);
		MulExpression<IntValue> myExp2 = new MulExpression<IntValue>(const3,const4);
		
		MulExpression<IntValue> myExp3 = new MulExpression<IntValue>(myExp1,myExp2);

					
		
		try {
			res = myExp3.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}
				
		
		assertEquals(expected, res);
		
	}
	

	@Test
	public void complexMulExpressionToStringWithInt() {
		String strRes = "((10 * 10) * (5 * 5))";

		int val1 = 10;
		int val2 = 10;
		int val3 = 5;
		int val4 = 5;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
		ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));
		
		
		MulExpression<IntValue> myExp1 = new MulExpression<IntValue>(const1,const2);
		MulExpression<IntValue> myExp2 = new MulExpression<IntValue>(const3,const4);
		
		MulExpression<IntValue> myExp3 = new MulExpression<IntValue>(myExp1,myExp2);

		
		
		assertEquals(strRes, myExp3.toString());
		
	}
	
	@Test
	public void simpleDivExpressionWithInt() {
		
		int expected = 5;
		int res = 0;
		
		int val1 = 10;
		int val2 = 2;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

		DivExpression<IntValue> myExp1 = new DivExpression<IntValue>(const1,const2);
		
		try {
			res = myExp1.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}

		assertEquals(expected, res);
		
	}
	
	@Test
	public void simpleDivExpressionWithZeroWihtInt() {
		
		Exception exception = new Exception();
		int res = 0;
		
		int val1 = 10;
		int val2 = 0;
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

		DivExpression<IntValue> myExp1 = new DivExpression<IntValue>(const1,const2);
		
		try {
			res = myExp1.evaluate(null).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {	
			exception = e;
		}
		
		String expectedMessage = "Exception caused by division by zero.";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void complexMixExpressionWithIntAndContext() {
		int res = 0;
		int expected = 1044;
		int val1 = 7;
		int val2 = 2;
		int val3 = 3;
		int val4 = 4;
		
		Context<IntValue> c = new Context<IntValue>();
		
		
		
		ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
		ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
		ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
		ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));
		
		VarExpression<IntValue> var1 = new VarExpression<IntValue>("a");
		VarExpression<IntValue> var2 = new VarExpression<IntValue>("b");
		VarExpression<IntValue> var3 = new VarExpression<IntValue>("c");
		VarExpression<IntValue> var4 = new VarExpression<IntValue>("dood");
		
		c.setValue("a", new IntValue(10));
		c.setValue("b", new IntValue(100));
		c.setValue("c", new IntValue(13));
		c.setValue("dood", new IntValue(7));
		
		AddExpression<IntValue> myExp1 = new AddExpression<IntValue>(var1,const2);
		// 10 + 2 = 12
		
		SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(var2,var3);
		// 100 - 13 = 87
		
		MulExpression<IntValue> myExp3 = new MulExpression<IntValue>(myExp1,myExp2);
		//1479
					
		
		try {
			res = myExp3.evaluate(c).getValue();
		} catch (ContextIncompleteException e) {			
			e.printStackTrace();
		} catch (DivByZeroException e) {			
			e.printStackTrace();
		}
				
		
		assertEquals(expected, res);
		
	}
	
	
	
     

}
