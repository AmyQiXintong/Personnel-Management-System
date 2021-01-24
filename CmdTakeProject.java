public class CmdTakeProject extends RecordedCommand {

    private String tName;
    private String pName;
    private String sDay;

    @Override
    public void undoMe() {
        Company c = Company.getInstance();
        c.cancelProject(pName, tName);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company c = Company.getInstance();
        try {
            c.assignProject(pName, tName, sDay);
        } catch (ExOverlap e) {
            System.out.println(e.getMessage() + "(" + e.getProblemValue() + ").");
        }
        addUndoCommand(this);
    }

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 4)
                throw new ExInsufficientArguments();

            Company c = Company.getInstance();
            if (!c.teamExists(cmdParts[1]))
                throw new ExTeamNotFound();
            if (!c.projectExists(cmdParts[2]))
                throw new ExProjectNotFound();
            if (c.isProjectOfATeam(cmdParts[2]))
                throw new ExProjectAssignedToATeamAlready();
            if (!Day.validDay(cmdParts[3]))
                throw new ExInvalidDate();
            if (new Day(cmdParts[3]).earlierThanSystemDate())
                throw new ExEarlierThanStart();

            c.assignProject(cmdParts[2], cmdParts[1], cmdParts[3]);

            tName = cmdParts[1];
            pName = cmdParts[2];
            sDay = cmdParts[3];

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");

        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExProjectAssignedToATeamAlready e) {
            System.out.println(e.getMessage());
        } catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        } catch (ExEarlierThanStart e) {
            System.out.println(e.getMessage());
        } catch (ExOverlap e) {
            System.out.println(e.getMessage() + "(" + e.getProblemValue().toString() + ").");
        }
    }

}
