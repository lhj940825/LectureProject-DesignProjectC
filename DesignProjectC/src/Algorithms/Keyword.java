package Algorithms;

public class Keyword {

   public Keyword()
   {
      count=0;
   }
   
   private int count;
   private String word;
   
   public void Addcount()
   {
      count++;
   }
   public void setword(String w)
   {
      word = w;
   }
   
   public int getcount()
   {
      return count;
   }
   public String getword()
   {
      return word;
   }
}