package com.wems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExTest {

  /*public static void main(String[] args) {
   List<String> validEmails = new ArrayList<String>();
   // valid emails
   validEmails.add("dharam@gmail.com");
   validEmails.add("dharam.singh@gmail.com");
   validEmails.add("dharam.sing_chahar@gmail.com");
   validEmails.add("1985dharam@gmail.com");
   validEmails.add("1985dharam@gmail.com");
   validEmails.add("dharam@gmail-youtube.co.in");
   for (String string : validEmails) {
    System.out.println("Email: "+string+" is "+isEmail(string));
   }
   System.out.println();
   // invalid emails
   List<String> inValidEmails = new ArrayList<String>();
   inValidEmails.add("@dharam@gmail.com");
   inValidEmails.add("dharam.singh@gmail.");
   inValidEmails.add("dharam.sing_chahar@gmail");
   inValidEmails.add("1985dharam-gmail.com");
   inValidEmails.add("#####@gmail.com");
   inValidEmails.add("dharam@gmail-youtube.co.in_uk");
   for (String string : inValidEmails) {
    System.out.println("Email: "+string+" is "+isEmail(string));
   }
  }*/

 public static boolean isEmail(String email)
 {
  /*
   * Set the email pattern string
   * Email must start with either A-Z or a-z or a number Before @ sign any
   * number of dots can come
   */
  Pattern p = Pattern
    .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[-A-Za-z0-9]+(\\.[A-Za-z]{1,})+$");
  // Match the given string with the pattern
  Matcher m = p.matcher(email);
  // check whether match is found
  return m.matches() ? true : false;
 }
}