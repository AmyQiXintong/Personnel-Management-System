public class ExEmployeeJoinedATeamAlready extends Exception {

    private static final long serialVersionUID = 1L;

    public ExEmployeeJoinedATeamAlready() {
        super("Employee has joined a team already.");
    }

    public ExEmployeeJoinedATeamAlready(String message) {
        super(message);
    }
}
