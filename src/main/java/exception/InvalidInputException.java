package exception;

/**
* Created by kirill on 16.04.17.
*/
public class InvalidInputException extends Exception {
    public InvalidInputException(String m1, String m2) {
        super((m1 + ": " + m2));
    }
}
