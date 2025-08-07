/**
*@author Sathvik Prahadeeswaran 
"COMP 3240 Programming Assignment 2"
*/
public class Assignment2_Java {
   /* This method should accept the number to test and the maximum number of iterations
    * to try before halting execution. If num is NOT magic (or the maximum number
    * of iterations has been reached), return (-1 * num) (i.e., the negative of num).
    * If num IS magic, return the number of iterations it took to reduce num to 1.
    * 
    * Remember that a number is magic if it can be reduced to 1 by dividing it by 2 if
    * it is even or multiplying it by 3 and adding 1 if it is odd.
    */ 
   public static int isMagic(int num, int max_iterations) {
   // initializes count of iterations to 0
      int count = 0;
   // The program divides the int num by 2 to see 
   // if the modulus is 0 so the num can keep dividing the 
   // num by two. It only runs through if the number is 
   // not equal to 1 already. If the number's modulus is 1
   // then the number gets multiplied by 3 and adds 1. Each
   // step the iteration count goes up by one. Then, it hits the
   // last case if the count is bigger than the parameter
   // for max iterations, -1 * the number is returned.
   // The code finally returns the iteration count. 
      while (num != 1 && count < max_iterations) {
         if (num % 2 == 0) {
            num = num / 2;
            count++;
            continue;
         }
         if (num % 2 == 1) {
            num = 3 * num + 1;
            count++;
            continue;
         }
      }
      if (count > max_iterations) {
         return (-1 * num); 
      }
      return count;
   }
   
   /* This method should be used to check if each number in the range [start, stop]
    * is a magic number. If all numbers in the range are magic, return the number of
    * iterations that it took to reduce the number passed into "stop" to 1. If you 
    * find a number that is NOT magic, this method should return the negative of
    * that number.
    */
   public static int TestRange(int start, int stop, int max_iterations) {
   // While the int start is not equal to the int stop
   // the program runs the method isMagic stated from above.
   // If the int is negative, the number will never reach 1
   // therefore, I made a return value for a if any number 
   // in the range were to become negative.
   // If all the values pass to be Magic, I return only the 
   // amount of iterations for the value of the int passed 
   // through stop to reach one.
      while (start != stop) {
         int a = Assignment2_Java.isMagic(start, max_iterations);
         if (a < 0) {
            return a;
         }
         start++;
      }
      return Assignment2_Java.isMagic(stop, max_iterations); 
      
   }
   
   public static void main(String[] args) {
      int start = 5;
      int stop = 20;
      int max_iterations = 500;
      
      int result = TestRange(start, stop, max_iterations);
      System.out.println(result);
   }
}