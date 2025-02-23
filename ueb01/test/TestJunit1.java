import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import context.Context;
import expressions.ConstExpression;
import expressions.ExpressionWrapper;
import expressions.VarExpression;
import expressions.binary.AddExpression;
import expressions.binary.DivExpression;
import expressions.binary.MulExpression;
import expressions.binary.SubExpression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.DoubleValue;
import values.IntValue;
import values.MatrixValue;

public class TestJunit1 {

    @Test
    public void simpleAddExpressionWithInt() {
        int res = 0;
        int expected = 4;
        int val1 = 2;
        int val2 = 2;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
        AddExpression<IntValue> myExp = new AddExpression<IntValue>(const1, const2);

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

        AddExpression<IntValue> myExp1 = new AddExpression<IntValue>(const1, const2);
        AddExpression<IntValue> myExp2 = new AddExpression<IntValue>(const3, const4);

        AddExpression<IntValue> myExp3 = new AddExpression<IntValue>(myExp1, myExp2);

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

        AddExpression<IntValue> myExp1 = new AddExpression<IntValue>(const1, const2);
        AddExpression<IntValue> myExp2 = new AddExpression<IntValue>(const3, const4);

        AddExpression<IntValue> myExp3 = new AddExpression<IntValue>(myExp1, myExp2);

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

        SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1, const2);

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

        SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1, const2);
        SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(const3, const4);

        SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1, myExp2);

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

        SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1, const2);
        SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(const3, const4);

        SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1, myExp2);

        assertEquals(strRes, myExp3.toString());

    }

    @Test
    public void simpleMulExpressionWihtInt() throws ContextIncompleteException, DivByZeroException {

        int expected = 6;
        int res = 0;

        int val1 = 3;
        int val2 = 2;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

        MulExpression<IntValue> myExp1 = new MulExpression<IntValue>(const1, const2);

        res = myExp1.evaluate(null).getValue();

        assertEquals(expected, res);

    }

    @Test
    public void complexMulExpressionWithInt()
            throws ContextIncompleteException, DivByZeroException {
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

        MulExpression<IntValue> myExp1 = new MulExpression<IntValue>(const1, const2);
        MulExpression<IntValue> myExp2 = new MulExpression<IntValue>(const3, const4);

        MulExpression<IntValue> myExp3 = new MulExpression<IntValue>(myExp1, myExp2);

        res = myExp3.evaluate(null).getValue();

        assertEquals(expected, res);

    }

    @Test
    public void complexMulExpressionToStringWithDouble() {
        String strRes = "((10.0 * 10.0) * (5.0 * 5.001))";

        double val1 = 10.0;
        double val2 = 10.0;
        double val3 = 5.0;
        double val4 = 5.001;

        ConstExpression<DoubleValue> const1 =
                new ConstExpression<DoubleValue>(new DoubleValue(val1));
        ConstExpression<DoubleValue> const2 =
                new ConstExpression<DoubleValue>(new DoubleValue(val2));
        ConstExpression<DoubleValue> const3 =
                new ConstExpression<DoubleValue>(new DoubleValue(val3));
        ConstExpression<DoubleValue> const4 =
                new ConstExpression<DoubleValue>(new DoubleValue(val4));

        MulExpression<DoubleValue> myExp1 = new MulExpression<DoubleValue>(const1, const2);
        MulExpression<DoubleValue> myExp2 = new MulExpression<DoubleValue>(const3, const4);

        MulExpression<DoubleValue> myExp3 = new MulExpression<DoubleValue>(myExp1, myExp2);

        assertEquals(strRes, myExp3.toString());

    }

    @Test
    public void simpleDivExpressionWithInt() throws ContextIncompleteException, DivByZeroException {

        int expected = 5;
        int res = 0;

        int val1 = 10;
        int val2 = 2;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

        DivExpression<IntValue> myExp1 = new DivExpression<IntValue>(const1, const2);

        res = myExp1.evaluate(null).getValue();

        assertEquals(expected, res);

    }

    @Test(expected = DivByZeroException.class)
    public void simpleDivExpressionWithZeroWihtInt()
            throws ContextIncompleteException, DivByZeroException {
        int val1 = 10;
        int val2 = 0;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

        DivExpression<IntValue> myExp1 = new DivExpression<IntValue>(const1, const2);

        int res = myExp1.evaluate(null).getValue();

    }

    @Test
    public void complexMixExpressionWithIntAndContext()
            throws ContextIncompleteException, DivByZeroException {
        int res = 0;
        int expected = 1044;
        int val2 = 2;

        Context<IntValue> c = new Context<IntValue>();

        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

        VarExpression<IntValue> var1 = new VarExpression<IntValue>("a");
        VarExpression<IntValue> var2 = new VarExpression<IntValue>("b");
        VarExpression<IntValue> var3 = new VarExpression<IntValue>("c");

        c.setValue("a", new IntValue(10));
        c.setValue("b", new IntValue(100));
        c.setValue("c", new IntValue(13));
        c.setValue("dood", new IntValue(7));

        AddExpression<IntValue> myExp1 = new AddExpression<IntValue>(var1, const2);
        // 10 + 2 = 12

        SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(var2, var3);
        // 100 - 13 = 87

        MulExpression<IntValue> myExp3 = new MulExpression<IntValue>(myExp1, myExp2);
        // 1479

        res = myExp3.evaluate(c).getValue();

        assertEquals(expected, res);

    }

    @Test
    public void complexMixExpressionWithDoubleAndContext()
            throws ContextIncompleteException, DivByZeroException {

        double res = 0.0;
        double expected = 1104.343;
        double epsilon = 0.01;

        double val2 = 2;

        Context<DoubleValue> c = new Context<DoubleValue>();

        ConstExpression<DoubleValue> const2 =
                new ConstExpression<DoubleValue>(new DoubleValue(val2));

        VarExpression<DoubleValue> var1 = new VarExpression<DoubleValue>("a");
        VarExpression<DoubleValue> var2 = new VarExpression<DoubleValue>("b");
        VarExpression<DoubleValue> var3 = new VarExpression<DoubleValue>("c");

        c.setValue("a", new DoubleValue(10.7));
        c.setValue("b", new DoubleValue(100.1232));
        c.setValue("c", new DoubleValue(13.167));
        c.setValue("dood", new DoubleValue(7.32));

        AddExpression<DoubleValue> myExp1 = new AddExpression<DoubleValue>(var1, const2);
        // 10 + 2 = 12

        SubExpression<DoubleValue> myExp2 = new SubExpression<DoubleValue>(var2, var3);
        // 100 - 13 = 87

        MulExpression<DoubleValue> myExp3 = new MulExpression<DoubleValue>(myExp1, myExp2);
        // 1479

        res = myExp3.evaluate(c).getValue();

        assertEquals(res, expected, epsilon);

    }

    @Test
    public void simpleAddExpressionWithMatrix()
            throws ContextIncompleteException, DivByZeroException {

        int row1 = 5;
        int col1 = 5;
        double val1 = 10.0;
        MatrixValue res = new MatrixValue(row1, col1);
        MatrixValue expct = new MatrixValue(row1, col1);

        MatrixValue mx1 = new MatrixValue(row1, col1);

        for (int i = 0; i < row1; ++i) {
            for (int j = 0; j < col1; ++j) {

                mx1.setValue(val1, i, j);
            }
        }

        int row2 = 5;
        int col2 = 5;
        double val2 = 2.5;

        MatrixValue mx2 = new MatrixValue(row2, col2);

        for (int i = 0; i < row2; i++) {
            for (int j = 0; j < col2; j++) {

                mx2.setValue(val2, i, j);
            }
        }

        int row3 = 5;
        int col3 = 5;
        double val3 = 12.5;

        MatrixValue expected = new MatrixValue(row3, col3);

        for (int i = 0; i < row3; i++) {
            for (int j = 0; j < col3; j++) {

                expected.setValue(val3, i, j);
            }
        }

        ConstExpression<MatrixValue> const1 = new ConstExpression<MatrixValue>(mx1);
        ConstExpression<MatrixValue> const2 = new ConstExpression<MatrixValue>(mx2);

        AddExpression<MatrixValue> myExp = new AddExpression<MatrixValue>(const1, const2);

        ConstExpression<MatrixValue> exp = new ConstExpression<MatrixValue>(expected);

        res = myExp.evaluate(null);
        expct = exp.evaluate(null);

        // System.out.print(res.toString());

        assertEquals(expct.toString(), res.toString());

    }

    @Test
    public void simpleMultExpressionWithMatrix() {

        int row1 = 3;
        int col1 = 3;
        double val1 = 10.0;
        MatrixValue res = new MatrixValue(row1, col1);
        MatrixValue expct = new MatrixValue(row1, col1);

        MatrixValue mx1 = new MatrixValue(row1, col1);

        for (int i = 0; i < row1; ++i) {
            for (int j = 0; j < col1; ++j) {

                mx1.setValue(val1, i, j);
            }
        }

        int row2 = 3;
        int col2 = 2;
        double val2 = 2.5;

        MatrixValue mx2 = new MatrixValue(row2, col2);

        for (int i = 0; i < row2; i++) {
            for (int j = 0; j < col2; j++) {

                mx2.setValue(val2, i, j);
            }
        }

        int row3 = 3;
        int col3 = 2;
        double val3 = 75.0;

        MatrixValue expected = new MatrixValue(row3, col3);

        for (int i = 0; i < row3; i++) {
            for (int j = 0; j < col3; j++) {

                expected.setValue(val3, i, j);
            }
        }

        ConstExpression<MatrixValue> const1 = new ConstExpression<MatrixValue>(mx1);
        ConstExpression<MatrixValue> const2 = new ConstExpression<MatrixValue>(mx2);

        MulExpression<MatrixValue> myExp = new MulExpression<MatrixValue>(const1, const2);

        ConstExpression<MatrixValue> exp = new ConstExpression<MatrixValue>(expected);

        try {
            res = myExp.evaluate(null);
            expct = exp.evaluate(null);
        } catch (ContextIncompleteException e) {
            e.printStackTrace();
        } catch (DivByZeroException e) {
            e.printStackTrace();
        }

        assertEquals(expct.toString(), res.toString());

    }

    @Test
    public void hasCyclesTestNoCycle() {
        int val1 = 20;
        int val2 = 3;
        int val3 = 10;
        int val4 = 2;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
        ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
        ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));

        SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1, const2);
        SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(const3, const4);

        SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1, myExp2);

        assertFalse(myExp3.hasCycles());

    }

    @Test
    public void hasCyclesTestHasCycle() {
        int val1 = 20;
        int val2 = 3;
        int val3 = 10;
        int val4 = 2;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));
        ConstExpression<IntValue> const3 = new ConstExpression<IntValue>(new IntValue(val3));
        ConstExpression<IntValue> const4 = new ConstExpression<IntValue>(new IntValue(val4));

        SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1, const2);
        SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(const3, const4);

        SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1, myExp2);

        myExp3.setLeftExpression(myExp3);

        assertTrue(myExp3.hasCycles());

    }

    @Test
    public void hasCyclesTestHasCycle2() {
        int val1 = 1;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));

        SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1, const1);

        SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1, myExp1);
        SubExpression<IntValue> myExp4 = new SubExpression<IntValue>(myExp1, myExp3);
        // SubExpression<IntValue> myExp5 = new SubExpression<IntValue>(myExp3, myExp1);

        myExp3.setLeftExpression(myExp4);

        ExpressionWrapper<IntValue> wrap = new ExpressionWrapper<>(myExp4);

        // System.out.print(wrap.toString());

        assertTrue(wrap.hasCycles());

    }

    @Test(expected = ContextIncompleteException.class)
    public void ContextIsMissingValue() throws ContextIncompleteException, DivByZeroException {
        int val1 = 20;
        int val2 = 3;
        int val3 = 10;
        int val4 = 2;

        ConstExpression<IntValue> const1 = new ConstExpression<IntValue>(new IntValue(val1));
        ConstExpression<IntValue> const2 = new ConstExpression<IntValue>(new IntValue(val2));

        Context<IntValue> c = new Context<IntValue>();
        VarExpression<IntValue> var1 = new VarExpression<IntValue>("a");
        VarExpression<IntValue> var2 = new VarExpression<IntValue>("c");

        c.setValue("a", new IntValue(val3));
        c.setValue("b", new IntValue(val4));

        SubExpression<IntValue> myExp1 = new SubExpression<IntValue>(const1, var2);
        SubExpression<IntValue> myExp2 = new SubExpression<IntValue>(const2, var1);

        SubExpression<IntValue> myExp3 = new SubExpression<IntValue>(myExp1, myExp2);

        int res = myExp3.evaluate(c).getValue();

    }

    @Test
    public void EmptyMatrixAdd() throws ContextIncompleteException, DivByZeroException {
        ConstExpression<MatrixValue> const1 =
                new ConstExpression<MatrixValue>(new MatrixValue(2, 2));
        ConstExpression<MatrixValue> const2 =
                new ConstExpression<MatrixValue>(new MatrixValue(2, 2));

        ConstExpression<MatrixValue> res = new ConstExpression<MatrixValue>(new MatrixValue(2, 2));

        AddExpression<MatrixValue> add = new AddExpression<>(const1, const2);

        assertEquals(res.toString(), add.evaluate(null).toString());

    }

    @Test
    public void EmptyMatrixSub() throws ContextIncompleteException, DivByZeroException {
        ConstExpression<MatrixValue> const1 =
                new ConstExpression<MatrixValue>(new MatrixValue(2, 2));
        ConstExpression<MatrixValue> const2 =
                new ConstExpression<MatrixValue>(new MatrixValue(2, 2));

        ConstExpression<MatrixValue> res = new ConstExpression<MatrixValue>(new MatrixValue(2, 2));

        SubExpression<MatrixValue> exp = new SubExpression<>(const1, const2);

        assertEquals(res.toString(), exp.evaluate(null).toString());

    }

    @Test
    public void EmptyMatrixMul() throws ContextIncompleteException, DivByZeroException {
        ConstExpression<MatrixValue> const1 =
                new ConstExpression<MatrixValue>(new MatrixValue(2, 2));
        ConstExpression<MatrixValue> const2 =
                new ConstExpression<MatrixValue>(new MatrixValue(2, 2));

        ConstExpression<MatrixValue> res = new ConstExpression<MatrixValue>(new MatrixValue(2, 2));

        MulExpression<MatrixValue> exp = new MulExpression<>(const1, const2);

        assertEquals(res.toString(), exp.evaluate(null).toString());

    }

}
