package com.gradescope.validator;
/**
*@author Sathvik Prahadeeswaran 
"COMP 3240 Section 002 Programming Assignment 1"
*/
// Notes on how to use evaluator():
// Call the evaluator with Evaluator.evaluate(<premise>, <variable_dict>). "premise"
// is a single string defining the premise or conclusion to test. "variable_dict" is a
// Hashtable<Character, Boolean>() object with the variable name as the key and true/false
// as the value.

// The only valid operators for premise strings are '^' (and), 'V' (or--CAPITAL v), '~' (not),
// and '>' (implies), and you can use parentheses to override the order of operations as usual.
// All variables should be lowercase letters and each should only be one character long. Finally,
// do not include spaces in the string.

// For example, if you want to test the premise 'p implies q or not r', you should use 'p>qV~r' as
// your premise string.
import java.util.*;

public class Validator {
    // All of the logic to complete this assignment should be written in this function.
    // This method accepts two things: An array of strings called premiseList and a
    // single string called conclusion. These strings should be formatted according to
    // the structure definded above. Also, this needs to return a boolean variable: true if
    // the argument form is valid, and false if it is not valid.
   public boolean validate(String[] premiseList, String conclusion) {
   //instantiations for all the different variables I used 
   //throughout the code
      boolean absolutelyInvalid = false;
      int workingAmount = 0;
      // used a for loop to loop through premiseList
      // and print the premise and conclusion so that
      // I could see what output values were returning
      for (String premise : premiseList) {
         System.out.println(premise);
      }
      System.out.println();
      System.out.println(conclusion);
      boolean evaluator = true;
      boolean eval = true;
      boolean conEvaluator = true;
      boolean conEval = true;
      try
      {
        // Instantiated a hash table
        // This hash table is going to be used to store all
        // my premise values
         Hashtable<Character, Boolean> values = new Hashtable<Character, Boolean>();
         for (String premise : premiseList) {
            for (int a = 0; a < premise.length(); a++) {
               if (Character.isLowerCase(premise.charAt(a))) {
                  values.put(premise.charAt(a), false);
                  System.out.println(premise);
               }
            }
         }
        // putting variables for conclusion in same hash table as premises
         for (int a = 0; a < conclusion.length(); a++) {
            if (Character.isLowerCase(conclusion.charAt(a))) {
               values.put(conclusion.charAt(a), false);
            }
         }
        // gets the size of the hash table and stores it into 
        // a new int array
         int[] numArray = new int[values.size()];
        // creates the variable for the number of values that need
        // to be evaluated in the truth table
         int numOfValues = (int) Math.pow(2, numArray.length);
         // set an int to the size of the values hash table
         int size = values.size();
         // creates array list for the values in the hashtable so that
         // keySet method can set a key for all the entries into the table
         List<Character> letterSet = new ArrayList<Character>(values.keySet());
         // adds leading 0 to binary value 
         for (int a = 0; a < numOfValues; a++)
         {
            String binary = Integer.toBinaryString((Integer) a);
            while (binary.length() != size) {
               binary = 0 + binary;
            }
            System.out.println(letterSet);
          // iterates through a for loop with binary values 
         // and returns true if the program detects a 1 
         // and returns false if the program detects a 0
            for (int b = 0; b < binary.length(); b++)
            {
               System.out.println(binary.charAt(b));
               if (binary.charAt(b) == '1') {
                  values.put(letterSet.get(b), true);
               }
               else {
                  values.put(letterSet.get(b), false);
               }
               System.out.println(values);
            }
                  // Evaluates every premise in the premise List
                  // For every premise that is false, the variable
                  // eval will set the variable eval to false in the program
                  // For every conclusion that is true, the variable
                  // conEval will set the variable conEval to false in the program
                  // the variable absolutelyInvalid is used to represent invalid
                  // arguments 
                  // the workingAmount variable is used to increase the count of valid
                  // arguments if both the premise and conclusion return true
            System.out.println(values);
            for (int c = 0; c < premiseList.length; c++)
            {
               try {
                  evaluator = Evaluator.evaluate(premiseList[c], values);
                  // This System.out.println was used while I was testing to 
                  // see what the evaluator method would print for my premises
               //System.out.println(Evaluator.evaluate(premiseList[c], values));
                  eval = eval && evaluator;
                  conEvaluator = Evaluator.evaluate(conclusion, values);
                  // This System.out.println was used while I was testing to 
                  // see what the conEvaluator method would print for my conclusion
               //System.out.println(Evaluator.evaluate(conclusion, values));
                  conEval = conEval && conEvaluator;
                  if (eval == true && conEval == false) {
                     absolutelyInvalid = true;
                  }  
                  if (eval == true && conEval == true) {
                     workingAmount++;
                  }
               }
               catch(Exception e) {
                  return false;
               }
            } 
         }
      
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return false;
      
      }
      boolean res = false;
        //If an argument is invalid then the code will return
        //false for those scenarios
        //However, if the scenarios return a valid argument, then
        //the program will return true
      if (evaluator == true && conEvaluator == false || absolutelyInvalid == true
      || workingAmount == 0) {
         res = false; }
      else {
         res = true;
      }
      // System.out.println used to output whether the total argument 
      // is true or false
      System.out.println("Result is: ");
      return res; 
   }
}