public class ExHeadLeaves extends Exception {

    private static final long serialVersionUID = 1L;

    public ExHeadLeaves() {
        super("Head of a team cannot leave.");
    }

    public ExHeadLeaves(String message) {
        super(message);
    }
}
