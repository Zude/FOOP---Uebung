import static org.junit.Assert.assertEquals;

import org.junit.Test;

import expressions.ConstExpression;
import expressions.binary.AddExpression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.IntValue;



public class TestJunit1 {

	
	@Test
	public void simpleAddExpression() {
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
	public void complexAddExpression() {
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
	public void complexAddExpressionToString() {
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
     

}
