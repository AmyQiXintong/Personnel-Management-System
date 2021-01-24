
public class CmdShowEmployeeDetails implements Command {

    @Override
    public void execute(String[] cmdParts) {
        try {

            if (cmdParts.length < 2)
                throw new ExInsufficientArguments();

            Company c = Company.getInstance();
            c.showEmployeeDetails(cmdParts[1]);

        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        }
    }

}
