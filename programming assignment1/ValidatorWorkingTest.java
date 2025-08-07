import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ValidatorWorkingTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void defaultTest() {
      ValidatorWorking v = new ValidatorWorking();
      String[] premises = {"p^b", "p"};
      String conclusion = "b";
   
      boolean isValid = v.validate(premises, conclusion);
      System.out.println(isValid);
      Assert.assertequals
   }
}

