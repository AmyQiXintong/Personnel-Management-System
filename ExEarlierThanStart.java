
public class ExEarlierThanStart extends Exception {

    private static final long serialVersionUID = 1L;

    public ExEarlierThanStart() {
        super("The earliest start day is tomorrow.");
    }

    public ExEarlierThanStart(String message) {
        super(message);
    }
}
