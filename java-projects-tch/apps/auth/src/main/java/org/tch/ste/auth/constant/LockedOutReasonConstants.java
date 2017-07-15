/**
 * 
 */
package org.tch.ste.auth.constant;

/**
 * LockOutReason constants.
 * @author ramug
 *
 */
public enum LockedOutReasonConstants {
      
      /**
       * One.
       */
      ONE("Failed Auth Attempts"),
      
      /**
       * Two.
       */
      TWO("Successful Auth"),
      
      /**
       * Three.
       */
      THREE("Manual"),
      
      /**
       * Four.
       */
      FOUR("Expired Password"),
      
      /**
       * Five.
       */
      FIVE("Other");
      
      private String responseStr;

      /**
       * Constructor.
       * 
       * @param responseStr
       *            String - The parameter.
       */
      private LockedOutReasonConstants(String responseStr) {
          this.responseStr = responseStr;
      }

      /**
       * Returns the response string.
       * 
       * @return String - The response string.
       */
      public String getResponseStr() {
          return this.responseStr;
      }
}
      
      

