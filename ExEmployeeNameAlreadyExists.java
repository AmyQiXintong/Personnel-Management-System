public class ExEmployeeNameAlreadyExists extends Exception {

    private static final long serialVersionUID = 1L;

    public ExEmployeeNameAlreadyExists() {
        super("Employee name already exists.");
    }

    public ExEmployeeNameAlreadyExists(String message) {
        super(message);
    }

}
