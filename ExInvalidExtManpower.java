public class ExInvalidExtManpower extends Exception {

    private static final long serialVersionUID = 1L;

    private int problemValue;

    public ExInvalidExtManpower() {
        super("Estimated manpower should not be zero or negative.");
    }

    public ExInvalidExtManpower(String message) {
        super(message);
    }

    public ExInvalidExtManpower(int pv) {
        problemValue = pv;
    }

    public ExInvalidExtManpower(String message, int pv) {
        super(message);
        problemValue = pv;
    }

    public int getProblemValue() {
        return problemValue;
    }
}
