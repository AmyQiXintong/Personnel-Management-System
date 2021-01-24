public class ExTeamNameAlreadyExists extends Exception {

    private static final long serialVersionUID = 1L;

    public ExTeamNameAlreadyExists() {
        super("Team name already exists.");
    }

    public ExTeamNameAlreadyExists(String message) {
        super(message);
    }

}
