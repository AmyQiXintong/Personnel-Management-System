import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team> {
    private String teamName;
    private Employee head;
    private Day dateSetup;
    private ArrayList<Employee> allMembers;
    private ArrayList<Project> allProjects;
    private ArrayList<DayPeriod> occupied;

    public String getName() {
        return teamName;
    }

    public Employee getHead() {
        return head;
    }

    public int numOfMembers() {
        return allMembers.size() + 1;
    }

    public static Team searchTeam(ArrayList<Team> allTeams, String nameTosearch) {
        for (Team t : allTeams) {
            if (t.getName().equals(nameTosearch))
                return t;
        }
        return null;
    }

    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        dateSetup = SystemDate.getInstance().clone();
        allMembers = new ArrayList<>();
        allProjects = new ArrayList<>();
        occupied = new ArrayList<>();
    }

    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName);
    }

    public static void list(ArrayList<Team> teamsToList) {
        System.out.printf("%-15s%-10s%-14s%-20s\n", "Team Name", "Leader", "Setup Date", "Members");
        for (Team t : teamsToList) {
            System.out.printf("%-15s%-10s%-14s%-19s\n", t.teamName, t.head.getName(), t.dateSetup.toString(),
                    t.listMembers());
        }
    }

    public String listMembers() {
        if (allMembers.isEmpty())
            return "(no member)";
        else {
            String stringOfMembers = ""; // to make the compiler happy
            for (Employee anEmployee : allMembers) {
                stringOfMembers += (anEmployee.getName() + " ");
            }
            return stringOfMembers;
        }
    }
    /**
     * use string concactnation here instead of system out them one by one
     * because it makes more senses to treat them as a whole
     * which is also why I constructed this function
     * instead of printing them in public static void list(...)
     */

    public boolean hasMember(String aNameToCheck) {
        if (head.getName().equals(aNameToCheck))
            return true;

        for (Employee e : allMembers) {
            if (e.getName().equals(aNameToCheck))
                return true;
        }
        return false;
    }

    public void addMember(Employee e) {
        allMembers.add(e);
        Collections.sort(allMembers);
    }

    public void removeMember(Employee e) {
        allMembers.remove(e);
    }

    public boolean hasProject(String aNameToCheck) {

        for (Project p : allProjects) {
            if (p.getName().equals(aNameToCheck))
                return true;
        }
        return false;
    }

    public void takeProject(Project p, DayPeriod dp) {
        allProjects.add(p);
        occupied.add(dp);
        Collections.sort(allProjects);
        Collections.sort(occupied);
    }

    public void dropProject(Project p, DayPeriod dp) {
        allProjects.remove(p);
        occupied.remove(dp);
    }

    public boolean notOccupied(DayPeriod dp) {
        if (occupied.isEmpty())
            return true;
        for (DayPeriod dayperiod : occupied) {
            if (dayperiod.hasOverlap(dp))
                return false;
        }
        return true;
    }

    public Day getEarliestUnoccupiedDay() {
        Day earliest = SystemDate.getInstance().next();
        for (DayPeriod dp : occupied) {
            if (dp.containDay(earliest)) {
                earliest.advance(1);
                continue;
            } else
                return earliest;
        }
        return null; // to make the compiler happy
    }

}
