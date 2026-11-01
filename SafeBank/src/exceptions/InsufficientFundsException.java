package exceptions;

public class InsufficientFundsException extends Throwable {
    public InsufficientFundsException(String insufficientAccountBalance) {
        super(insufficientAccountBalance);
    }
}
