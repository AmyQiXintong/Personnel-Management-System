
public class ExOverlap extends Exception {

    private static final long serialVersionUID = 1L;

    private DayPeriod problemDP;

    public ExOverlap() {
        super("The team is not available during the period ");
    }

    public ExOverlap(String message) {
        super(message);
    }

    public ExOverlap(DayPeriod dp) {
        problemDP = dp;
    }

    public ExOverlap(String message, DayPeriod dp) {
        super(message);
        problemDP = dp;
    }

    public DayPeriod getProblemValue() {
        return problemDP;
    }
}
