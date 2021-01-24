public class ExProjectCodeAlreadyExists extends Exception {

    private static final long serialVersionUID = 1L;

    public ExProjectCodeAlreadyExists() {
        super("Project code already exists.");
    }

    public ExProjectCodeAlreadyExists(String message) {
        super(message);
    }

}
