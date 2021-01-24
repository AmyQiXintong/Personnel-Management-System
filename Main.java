import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InputMismatchException {

        Scanner in = new Scanner(System.in);

        System.out.print("Please input the file pathname: ");
        String filepathname = in.nextLine();

        Scanner inFile = null;

        try {
            inFile = new Scanner(new File(filepathname));

            String cmdLine1 = inFile.nextLine();
            String[] cmdLine1Parts = cmdLine1.split("\\|");
            System.out.println("\n> " + cmdLine1);
            SystemDate.createTheInstance(cmdLine1Parts[1]);

            while (inFile.hasNext()) {
                String cmdLine = inFile.nextLine().trim();
                if (cmdLine.equals(""))
                    continue;

                System.out.println("\n> " + cmdLine);
                String[] cmdParts = cmdLine.split("\\|");

                if (cmdParts[0].equals("listEmployees"))
                    (new CmdListEmployees()).execute(cmdParts);
                else if (cmdParts[0].equals("listProjects"))
                    (new CmdListProjects()).execute(cmdParts);
                else if (cmdParts[0].equals("listTeams"))
                    (new CmdListTeams()).execute(cmdParts);
                else if (cmdParts[0].equals("hire"))
                    (new CmdHire()).execute(cmdParts);
                else if (cmdParts[0].equals("setupTeam"))
                    (new CmdSetupTeam()).execute(cmdParts);
                else if (cmdParts[0].equals("createProject"))
                    (new CmdCreateProject()).execute(cmdParts);
                else if (cmdParts[0].equals("startNewDay"))
                    (new CmdStartNewDay()).execute(cmdParts);
                else if (cmdParts[0].equals("joinTeam"))
                    (new CmdJoinTeam()).execute(cmdParts);
                else if (cmdParts[0].equals("changeTeam"))
                    (new CmdChangeTeam()).execute(cmdParts);
                else if (cmdParts[0].equals("takeProject"))
                    (new CmdTakeProject()).execute(cmdParts);
                else if (cmdParts[0].equals("suggestTeam"))
                    (new CmdSuggestTeam()).execute(cmdParts);
                else if (cmdParts[0].equals("showEmployeeDetails"))
                    (new CmdShowEmployeeDetails()).execute(cmdParts);
                else if (cmdParts[0].equals("showProjectWorkerDetails"))
                    (new CmdShowProjectWorkerDetails()).execute(cmdParts);
                else if (cmdParts[0].equals("undo"))
                    RecordedCommand.undoOneCommand();
                else if (cmdParts[0].equals("redo"))
                    RecordedCommand.redoOneCommand();
                else
                    continue;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (InputMismatchException e) {
            System.out.println("File content problem. Program terminated.");
        } finally {
            if (inFile != null) {
                inFile.close();
            }
            in.close();
        }
    }
}
