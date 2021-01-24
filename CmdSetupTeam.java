public class CmdSetupTeam extends RecordedCommand {

    private Team t;

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.removeTeam(t);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.addTeam(t);
        addUndoCommand(this);
    }

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 3)
                throw new ExInsufficientArguments();

            Company c = Company.getInstance();

            if (c.teamExists(cmdParts[1]))
                throw new ExTeamNameAlreadyExists();
            if (!c.employeeExists(cmdParts[2]))
                throw new ExEmployeeNotFound();
            if (c.isMemberOfATeam(cmdParts[2]))
                throw new ExEmployeeJoinedATeamAlready();

            t = c.createTeam(cmdParts[1], cmdParts[2]);
            c.findEmployee(cmdParts[2]).addRecord(cmdParts[1], SystemDate.getInstance().clone());

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");

        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNameAlreadyExists e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeJoinedATeamAlready e) {
            System.out.println(e.getMessage());
        }
    }

}
