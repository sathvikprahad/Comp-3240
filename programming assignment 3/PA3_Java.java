import java.util.Scanner;

public class PA3_Java {
/* Sathvik Prahadeeswaran
/* Comp 3240
/* Mrs. Morris
/* 4 April 2023
  
  /* Multiplies matrix 1 by matrix 2
  */
   public static float[][] matrix_mult(float[][] mat1, float[][] mat2) {
      float[][] result = new float[mat1.length][mat2[0].length];
   
   // iterate through each row of matrix1
      for (int i = 0; i < mat1.length; i++) {
      // iterate through each column of matrix2
         for (int j = 0; j < mat2[0].length; j++) {
         // iterate through each element of the current row of matrix1 / current column of matrix2
            for (int k = 0; k < mat1[0].length; k++) {
               result[i][j] += mat1[i][k] * mat2[k][j];
            }
         }
      }
      return result;
   }
   
   /* Create the transition matrix from the given observation points
    */
   public static float[][] calc_transition_matrix(String observation_string) {
      float[][] transition_matrix = new float[2][2];
      float rainy = 0;
      float lessRainy = 0;
      float dry = 0;
      float lessDry = 0;
      float totalD = 0;
      float totalR = 0;
      for (int i = 0; i < observation_string.length() - 1; i++) {
         String twoLetters = observation_string.substring(i, i+2);
         if (twoLetters.equals("RR")) {
            rainy++;
         }
         if (twoLetters.equals("RD")) {
            lessRainy++;
         }
         if (twoLetters.equals("DD")) {
            dry++;
         }
         if (twoLetters.equals("DR")) {
            lessDry++;
         }
      }
      for (int i = 0; i < observation_string.length() - 1; i++) {
         char item = observation_string.charAt(i);
         if (item == 'D') 
            totalD++; 
         if (item == 'R') 
            totalR++;
      }
      if(rainy == 0 || lessRainy == 0 || dry == 0 || lessDry == 0) throw new IllegalArgumentException();
      float rToD = lessRainy / totalR;
      float rToR = rainy / totalR;
      float dToD = dry / totalD;
      float dToR = lessDry / totalD;
      transition_matrix[0][0] = dToD;
      transition_matrix[0][1] = rToD;
      transition_matrix[1][0] = dToR;
      transition_matrix[1][1] = rToR;
      return transition_matrix;
   }
   
   /* Generates the forecast for the next 7 days given the transition matrix and the current weather
      The forecast should be a 2x7 matrix where each row is a forecast for a day
    */
   public static float[][] generate_forecast(float[][] transition_matrix, char curr_weather) {
      float[][] forecast = new float[7][2];
      float[][] currWeather = new float[2][1];
      if (curr_weather == 'D') {
         currWeather[0][0] = 1;
         currWeather[1][0] = 0;
      }
      if (curr_weather == 'R') {
         currWeather[0][0] = 0;
         currWeather[1][0] = 1;
      }
      
      float[][] tTwo = PA3_Java.matrix_mult(transition_matrix, transition_matrix);
      float[][] tThree = PA3_Java.matrix_mult(tTwo, transition_matrix);
      float[][] tFour = PA3_Java.matrix_mult(tThree, transition_matrix);
      float[][] tFive = PA3_Java.matrix_mult(tFour, transition_matrix);
      float[][] tSix = PA3_Java.matrix_mult(tFive, transition_matrix);
      float[][] tSeven = PA3_Java.matrix_mult(tSix, transition_matrix);
      
      float[][] dOne = PA3_Java.matrix_mult(transition_matrix, currWeather);
      float[][] dTwo = PA3_Java.matrix_mult(tTwo, currWeather);
      float[][] dThree = PA3_Java.matrix_mult(tThree, currWeather);
      float[][] dFour = PA3_Java.matrix_mult(tFour, currWeather);
      float[][] dFive = PA3_Java.matrix_mult(tFive, currWeather);
      float[][] dSix = PA3_Java.matrix_mult(tSix, currWeather);
      float[][] dSeven = PA3_Java.matrix_mult(tSeven, currWeather);
      
      // Creates results for the forecast matrix 
      forecast[0][0] = dOne[0][0];
      forecast[0][1] = dOne[1][0];
      forecast[1][0] = dTwo[0][0];
      forecast[1][1] = dTwo[1][0];
      forecast[2][0] = dThree[0][0];
      forecast[2][1] = dThree[1][0];
      forecast[3][0] = dFour[0][0];
      forecast[3][1] = dFour[1][0];
      forecast[4][0] = dFive[0][0];
      forecast[4][1] = dFive[1][0];
      forecast[5][0] = dSix[0][0];
      forecast[5][1] = dSix[1][0];
      forecast[6][0] = dSeven[0][0];
      forecast[6][1] = dSeven[1][0];
      
      return forecast;
   }
   
   /* Generates the climate prediction (i.e., steady state vector) given the transition matrix, current 
	  weather, and precision
    */
   public static float[] generate_climate_prediction(float[][] transition_matrix, char curr_weather, float precision) {
      // Making sure that the precision value is within conditions
      if (precision > 0.1 || precision < 0) {
         throw new IllegalArgumentException();
      }
      float[] steady_state = new float[2];
      float[][] currWeather = new float[2][1];
      // Creates matrix values for when currWeather is D or R 
      if (curr_weather == 'D') {
         currWeather[0][0] = 1;
         currWeather[1][0] = 0;
      }
      if (curr_weather == 'R') {
         currWeather[0][0] = 0;
         currWeather[1][0] = 1;
      }
      
      float delta00 = 1f;
      float delta01 = 1f;
      float delta10 = 1f;
      float delta11 = 1f;
      
      int count = 0;
      float[][] exponentMatrixOne = transition_matrix;
      float[][] exponentMatrixTwo = transition_matrix;
   // while loop which allows program to muliply matrix only when precision is greater than delta values
      while (delta00 > precision && delta01 > precision && delta10 > precision && delta11 > precision)  {
                  
         int exponent = 0;
         int exponent2 = 0;
         while(exponent < count) {
            exponentMatrixOne = PA3_Java.matrix_mult(exponentMatrixOne, transition_matrix);
            exponent++;
         }
         while(exponent2 < count + 1) {
            exponentMatrixTwo = PA3_Java.matrix_mult(exponentMatrixTwo, transition_matrix);
            exponent2++;
         }
      // How delta values are found
         delta00 = Math.abs(exponentMatrixOne[0][0] - exponentMatrixTwo[0][0]);
         delta01 = Math.abs(exponentMatrixOne[0][1] - exponentMatrixTwo[0][1]);
         delta10 = Math.abs(exponentMatrixOne[1][0] - exponentMatrixTwo[1][0]);
         delta11 = Math.abs(exponentMatrixOne[1][1] - exponentMatrixTwo[1][1]);
         count++;
      }
      // Gets the result of the exponent matrix created and the weather of the current day
      float[][] res = PA3_Java.matrix_mult(exponentMatrixOne, currWeather);
      
      steady_state[0] = res[0][0];
      steady_state[1] = res[1][0];
       
      return steady_state;
   }
   
   /* Print the forecasted weather predictions 
    */
   public static void print_predictions(float[][] forecast) {
      // Print first line
      System.out.println("[[" + forecast[0][0] + "," + forecast[0][1] + "],");
      
      // Print middle 5 lines
      for (int i = 1; i < forecast.length - 1; i++) {
         System.out.println(" [" + forecast[i][0] + "," + forecast[i][1] + "],");
      }
       
      // Print the last line
      System.out.println(" [" + forecast[6][0] + "," + forecast[6][1] + "]]");
   }
   
   /* Print the steady state vector containing the climate prediction
    */
   public static void print_steady_state(float[] steady_state) {
      System.out.println(steady_state[0]);
      System.out.println(steady_state[1]);
   }
   
   public static void main(String[] args) {
   // Creates scanner to take in user input 
      Scanner scan = new Scanner(System.in);
      float decimal;
      String str;
      
      // Telling user to input a float value with certain conditions
      System.out.print("Enter a float value greater than 0 but less than 0.1: ");
      decimal = scan.nextFloat();
       // Telling user to input a string with certain conditions
      System.out.println("Nice! now input a string of capital letters of only R's and D's");
      System.out.println("R represents rainy days and D represents dry days.");
      
      System.out.println("Make sure that your string has at least one R to R transition, R to D transition,"
         + "D to D transition, and D to R transition or else you will have to reenter the string");
      
      System.out.print("Enter a string value that follows those restrictions: ");
      str = scan.next();
      System.out.println(str);
      // Instantiates a new matrix which calculates the value of user input
      float[][] newMatrix = new float[2][2];
      newMatrix = calc_transition_matrix(str);
      // Takes user input string to create weather forecast 
      char currWeather = str.charAt(str.length() - 1);
      float[][] forecast = new float [7][2];
      System.out.println("Here is the weather forecast for the next seven days: ");
      print_predictions(generate_forecast(newMatrix, currWeather));
      // Takes user input string to create climate prediction  
      System.out.println("Here is the climate prediction with the user precision: ");
      print_steady_state(generate_climate_prediction(newMatrix, currWeather, decimal));
      
   }
}
   
