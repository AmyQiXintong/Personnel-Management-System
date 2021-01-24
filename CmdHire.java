public class CmdHire extends RecordedCommand {
    private Employee e;

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeEmployee(e);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addEmployee(e);
        addUndoCommand(this);
    }

    @Override
    public void execute(String[] cmdParts) {

        try {
            if (cmdParts.length < 2)
                throw new ExInsufficientArguments();

            Company c = Company.getInstance();
            if (c.employeeExists(cmdParts[1]))
                throw new ExEmployeeNameAlreadyExists();

            e = new Employee(cmdParts[1]);
            c.addEmployee(e);

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");

        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNameAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }

}
