package songPlayerDemo;

import java.time.LocalDate;

public class ShowLocalDate {  

  public static void main(String[] args) {
    // Store today  current date 
    LocalDate lastDatePlayed = LocalDate.now();
    lastDatePlayed = lastDatePlayed.minusDays(3);
      
    LocalDate today = LocalDate.now();
    
    System.out.println (today.compareTo(lastDatePlayed) == 0); 
  }
} 