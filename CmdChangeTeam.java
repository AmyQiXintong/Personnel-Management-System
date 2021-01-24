
public class CmdChangeTeam extends RecordedCommand {

    private Employee e;
    private String oldTeamName;
    private String newTeamName;

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.changeTeam(newTeamName, oldTeamName, e.getName());
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        c.changeTeam(oldTeamName, newTeamName, e.getName());
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
            if (c.isHeadOfATeam(cmdParts[1]))
                throw new ExHeadLeaves();

            e = c.findEmployee(cmdParts[1]);
            oldTeamName = c.findMyTeam(e).getName();
            if (oldTeamName.equals(cmdParts[2]))
                throw new ExSameTeam();

            newTeamName = cmdParts[2];

            c.changeTeam(oldTeamName, newTeamName, e.getName());
            c.findEmployee(cmdParts[1]).addRecord(newTeamName, SystemDate.getInstance().clone());

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");

        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExSameTeam e) {
            System.out.println(e.getMessage());
        } catch (ExHeadLeaves e) {
            System.out.println(e.getMessage());
        }

    }

}
