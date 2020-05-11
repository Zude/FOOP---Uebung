import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import context.Context;
import expressions.AbstractExpression;
import expressions.Expression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;

/**
 * Klasse für JUnit 4.x, mit der alle grundlegenden Tests durchgeführt werden.
 * 
 * @author kar, mhe
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestJunit1.class
})




public class TestSuite {
    /**
     * Methode zum einfachen Starten des Tests von der Kommandozeile.
     * 
     * @param args Kommandozeilenargumente.
     */
    public static void main(final String[] args) {
        org.junit.runner.JUnitCore.main(TestSuite.class.getName());
    }
}


