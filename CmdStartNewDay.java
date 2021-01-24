public class CmdStartNewDay extends RecordedCommand {

    private String oldD = SystemDate.getInstance().clone().toString();
    private String newD;

    @Override
    public void undoMe() {
        SystemDate.getInstance().set(oldD);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        SystemDate.getInstance().set(newD);
        addUndoCommand(this);
    }

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 2)
                throw new ExInsufficientArguments();

            SystemDate.getInstance().set(cmdParts[1]);

            addUndoCommand(this);
            clearRedoList();

            newD = cmdParts[1];

            System.out.println("Done.");

        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        }
    }

}