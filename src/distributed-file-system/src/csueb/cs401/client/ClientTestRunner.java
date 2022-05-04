package csueb.cs401.client;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class ClientTestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(AllClientTests.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println(result.wasSuccessful());
   }
}
