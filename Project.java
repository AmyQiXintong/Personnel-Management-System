import java.util.ArrayList;

public class Project implements Comparable<Project> {
    private String projectName;
    private int estManpower;
    private DayPeriod dPeriod;

    Project(String pn, int em) {
        projectName = pn;
        estManpower = em;
    }

    public void setPeriod(DayPeriod dp) {
        dPeriod = dp;
    }
    // setters should be avoided, but I don't see a better way to do this

    public String getName() {
        return projectName;
    }

    @Override
    public int compareTo(Project another) {
        return this.projectName.compareTo(another.projectName);
    }

    public String getEstManpower() {
        return estManpower + " man-days";
    }

    public int getManPowerInt() {
        return estManpower;
    }

    public DayPeriod getDayPeriod() {
        return dPeriod;
    }

    public static Project searchProject(ArrayList<Project> allProjects, String nameToSearch) {
        for (Project p : allProjects) {
            if (p.getName().equals(nameToSearch))
                return p;
        }
        return null;
    }

    public void listDetails() {
        System.out.println("Est manpower : " + estManpower + " man-days");
        Company c = Company.getInstance();
        Team t = c.findMyTeam(this);
        System.out.println("Team         : " + t.getName() + " (Leader is " + t.getHead().getName() + ")");
        System.out.println("Work period  : " + this.dPeriod.toString());
        System.out.println("\nMembers: ");
        c.showMemberWork(this);
    }
}
