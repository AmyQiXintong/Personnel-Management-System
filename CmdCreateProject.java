
public class CmdCreateProject extends RecordedCommand {

    private Project p;

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeProject(p);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addProject(p);
        addUndoCommand(this);
    }

    @Override
    public void execute(String[] cmdParts) throws NumberFormatException {
        try {
            if (cmdParts.length < 3)
                throw new ExInsufficientArguments();

            Company c = Company.getInstance();
            if (c.projectExists(cmdParts[1]))
                throw new ExProjectCodeAlreadyExists();

            int estManpower = Integer.parseInt(cmdParts[2]);
            if (estManpower <= 0)
                throw new ExInvalidExtManpower("Estimated manpower should not be zero or negative.", estManpower);

            p = c.createProject(cmdParts[1], estManpower);

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");

        } catch (NumberFormatException e) {
            System.out.println("Wrong number format -- Please enter an integer.");
        } catch (ExInvalidExtManpower e) {
            System.out.println(e.getMessage() + "\n" + "Consider changing " + e.getProblemValue()
                    + " to a positive non-zero amount.");
        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        } catch (ExProjectCodeAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }

}
