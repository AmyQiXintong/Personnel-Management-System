public class CmdJoinTeam extends RecordedCommand {

    private String eNameToFind;
    private String tNameToFind;

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.quitTeam(tNameToFind, eNameToFind);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.joinTeam(tNameToFind, eNameToFind);
        addUndoCommand(this);
    }

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 3)
                throw new ExInsufficientArguments();

            Company c = Company.getInstance();
            if (!c.employeeExists(cmdParts[1]))
                throw new ExEmployeeNotFound();
            if (!c.teamExists(cmdParts[2]))
                throw new ExTeamNotFound();
            if (c.isMemberOfATeam(cmdParts[1]))
                throw new ExEmployeeJoinedATeamAlready();

            eNameToFind = cmdParts[1];
            tNameToFind = cmdParts[2];
            c.joinTeam(tNameToFind, eNameToFind);
            c.findEmployee(eNameToFind).addRecord(tNameToFind, SystemDate.getInstance().clone());

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");

        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeJoinedATeamAlready e) {
            System.out.println(e.getMessage());
        }

    }

}
