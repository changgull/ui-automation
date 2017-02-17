package com.stonecress;
import org.testng.annotations.Test;

public class AppTest {
  @Test(groups = { "functest", "checkintest" })
  public void testMethod1() {
  }
 
  @Test(groups = {"functest", "checkintest"} )
  public void testMethod2() {
  }
 
  @Test(groups = { "functest" })
  public void testMethod3() {
  }
}