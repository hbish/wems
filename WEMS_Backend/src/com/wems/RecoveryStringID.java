package com.wems;

import java.security.SecureRandom; 
import java.math.BigInteger; 
 
public final class RecoveryStringID
{
  private static SecureRandom random = new SecureRandom(); 
 
  public static void main(String[] args)
  {
      System.out.println(nextStringID());
  }
  
  public static String nextStringID() 
  { 
      return new BigInteger(130, random).toString(16); 
  }
} 