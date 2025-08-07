import java.util.concurrent.ThreadLocalRandom;
public class PA4_Java {
/* Sathvik Prahadeeswaran
/* Comp 3240
/* Mrs. Morris
/* 13 April 2023
  
   /* Perform one instance of the Monty Hall Problem
    *
    * should_switch: set to true if running an experiment where the contestant should
    *    switch their guess every time. Set to false if not
    *
    * Returns true if the contestant selected the door with the car behind it. Returns
    *    false otherwise.
    */
   public static int door(int choice, int car_door) {
      int open_door = -1;
     
      if (choice == car_door) {
         do {
            open_door = ThreadLocalRandom.current().nextInt(0,3);
         } while (open_door == car_door);
      } else {
         open_door = 3 - (car_door + choice);
      }
     
      return open_door;
   }
   public static boolean run_trial(boolean should_switch) {
      int door = ThreadLocalRandom.current().nextInt(0, 3);
      int choice = ThreadLocalRandom.current().nextInt(0, 3);
     
      int open_door = PA4_Java.door(choice, door);
     
      if (should_switch)
         choice = 3 - (choice + open_door);
     
      return door == choice;
   }
   
   /* Execute an entire experiment (i.e., execute the specified number of trials)
    * and return the desired results
    * 
    * num_trials: number of trials to execute in this experiment
    * should_switch: set to true if running an experiment where the contestant should
    *    switch their guess every time. Set to false if not
    *
    * Returns the percentage of games won (i.e., number of wins / number of trials)
    */
   public static double run_experiment(int num_trials, boolean should_switch) {
      int total_successes = 0;
     
      for (int i = 0; i < num_trials; i++) {
         if (PA4_Java.run_trial(should_switch))
            total_successes++;
      }
     
      return (double)total_successes / num_trials;
   }
   /* This is a stub that you can use to test the rest of the program. The code in this
    * method will not be executed during grading, so you don't need to worry about user
    * input.
    */
   public static void main(String[] args) {
      int num_trials = 1000;
      
      double percentage_s = PA4_Java.run_experiment(num_trials, true);
      System.out.println("Switch Win Rate: " + percentage_s);
     
      double percentage_n = PA4_Java.run_experiment(num_trials, false);
      System.out.print("Switch Win Rate: " + percentage_n);
   }
}
