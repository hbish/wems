package com.wems.database;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * @Description: This class generated the hash code of few strings
 * 
 */
public class MD5 {

 public static void main(String[] args) {
  String[] inputStrings = { "albertn", "ann", "angelar", "davidc", "davidk", "hongs", "kennyl", "lukec", "pauly", "rogert", "sethk", "stevenw", "admin" };
  System.out.println("String\t\t\tHash Value");
  System.out.println("======\t\t\t==========");
  for (int i = 0; i < inputStrings.length; i++) {
   System.out.println(inputStrings[i] + "\t\t"
     + getMD5HashVal(inputStrings[i]));
  }
 }

 public static String getMD5HashVal(String strToBeEncrypted) {
  String encryptedString = null;
  byte[] bytesToBeEncrypted;
  try {
   // convert string to bytes using a encoding scheme
   bytesToBeEncrypted = strToBeEncrypted.getBytes("UTF-8");
   MessageDigest md = MessageDigest.getInstance("MD5");
   byte[] theDigest = md.digest(bytesToBeEncrypted);
                        // convert each byte to a hexadecimal digit
   Formatter formatter = new Formatter();
   for (byte b : theDigest) {
    formatter.format("%02x", b);
   }
   encryptedString = formatter.toString().toLowerCase();

  } catch (UnsupportedEncodingException e) {
   e.printStackTrace();
  } catch (NoSuchAlgorithmException e) {
   e.printStackTrace();
  }
  return encryptedString;
 }
}