/*
 * ApplicationException.java
 *
 */
package exception;

/**
 *
 Occurs when there is some error with the data itself
 */
public class ApplicationException extends Exception {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String s) {
        super(s);
    }
}
