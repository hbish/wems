package com.wems.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
  
  String myPassword = null;
  String myHash = null;
  MessageDigest myDigest = null;
  
  public MD5() {}
  
  public String getMD5Hash(String pw) throws NoSuchAlgorithmException {
    myPassword = pw;
    myDigest = MessageDigest.getInstance("MD5");
    
    System.out.print("HASHING - Pre:" + myPassword);
    myDigest.reset();
    myDigest.update(myPassword.getBytes());
    byte[] digest = myDigest.digest();
    BigInteger bigInt = new BigInteger(1,digest);
    myHash = bigInt.toString(16);
    while(myHash.length() < 32 ){
      myHash = "0"+myHash;
    }
    System.out.print("HASHING - Post:" + myHash);
    
    return myHash;
  }
  
}
