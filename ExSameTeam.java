public class ExSameTeam extends Exception {

    private static final long serialVersionUID = 1L;

    public ExSameTeam() {
        super("The old and new teams should not be the same.");
    }

    public ExSameTeam(String message) {
        super(message);
    }
}
