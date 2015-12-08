/*
 * ApplicationException.java
 *
 */
package exception;

/**
 *
 Occurs when there is a problem with the database
 */
public class DBException extends Exception {

    public DBException() {
        super();
    }

    public DBException(String s) {
        super(s);
    }
}
