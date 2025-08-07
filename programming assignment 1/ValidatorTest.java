
public class ValidatorTest {
   public static void main(String args[]) throws Exception{
      Validator v = new Validator();
      String[] premises = {"p^(~qVr)"};
      String conclusion = "p>r";
   
      boolean isValid = v.validate(premises, conclusion);
      System.out.println(isValid);
   }
}