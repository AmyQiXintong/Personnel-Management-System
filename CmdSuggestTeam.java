public class CmdSuggestTeam implements Command {

    @Override
    public void execute(String[] cmdParts) {
        Company c = Company.getInstance();
        c.suggestTeam(cmdParts[1]);
    }

}
