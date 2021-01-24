import java.util.ArrayList;

public abstract class RecordedCommand implements Command {
    private static ArrayList<RecordedCommand> undolist = new ArrayList<>();
    private static ArrayList<RecordedCommand> redolist = new ArrayList<>();

    public abstract void undoMe();

    public abstract void redoMe();

    protected static void addUndoCommand(RecordedCommand cmd) {
        undolist.add(cmd);
    }

    protected static void addRedoCommand(RecordedCommand cmd) {
        redolist.add(cmd);
    }

    protected static void clearRedoList() {
        redolist.clear();
    }

    public static void undoOneCommand() {
        if (!undolist.isEmpty())
            undolist.remove(undolist.size() - 1).undoMe();
        else
            System.out.println("Nothing to undo.");
    }

    public static void redoOneCommand() {
        if (!redolist.isEmpty())
            redolist.remove(redolist.size() - 1).redoMe();
        else
            System.out.println("Nothing to redo.");
    }

    public abstract void execute(String[] cmdParts);

}
