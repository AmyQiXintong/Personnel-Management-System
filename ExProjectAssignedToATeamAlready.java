
public class ExProjectAssignedToATeamAlready extends Exception {

    private static final long serialVersionUID = 1L;

    public ExProjectAssignedToATeamAlready() {
        super("Project has already been assigned to a team.");
    }

    public ExProjectAssignedToATeamAlready(String message) {
        super(message);
    }
}
