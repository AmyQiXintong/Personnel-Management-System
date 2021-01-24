import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee> {
    private String name;
    private ArrayList<Membership> memberships;

    public Employee(String name) {
        this.name = name;
        memberships = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public static Employee searchEmployee(ArrayList<Employee> allEmployees, String nameToSearch) {
        for (Employee e : allEmployees) {
            if (e.getName().equals(nameToSearch)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Employee another) {
        if (this.name.equals(another.name)) {
            return 0;
        } else if (this.name.compareTo(another.name) > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public void addRecord(String teamName, Day aDay) {
        memberships.add(new Membership(teamName, aDay));
        Collections.sort(memberships);
    }

    public void listDetails(boolean isHead) {
        System.out.println("The teams that " + name + " has joined: ");
        if (isHead) {
            System.out.println(memberships.get(memberships.size() - 1).getTeamName() + " ("
                    + memberships.get(memberships.size() - 1).getJoinDay().toString() + " to --), as a leader");
        } else {
            for (int i = 0; i < memberships.size() - 1; i++) {
                System.out.println(memberships.get(i).getTeamName() + " (" + memberships.get(i).getJoinDay().toString()
                        + " to " + memberships.get(i + 1).getJoinDay().previous().toString() + ")");
            }

            System.out.println(memberships.get(memberships.size() - 1).getTeamName() + " ("
                    + memberships.get(memberships.size() - 1).getJoinDay().toString() + " to --)");

            /**
             * REASON WHY NOT USING PRINTF I noticed the use of printf in givenLines.txt but
             * because I didn't treat "--" as part of the object stored in memberships it
             * doesn't actually save me a lot using printf Also, there is not requirement on
             * formatting So I just used println here
             */
        }
    }

    public void recordInATeam(Team t, Project p) {
        for (int i = 0; i < memberships.size(); i++) {
            if (memberships.get(i).getTeamName().equals(t.getName())) {
                Day startDay = memberships.get(i).getJoinDay().compareTo(p.getDayPeriod().getStartDay()) > 0
                        ? memberships.get(i).getJoinDay()
                        : p.getDayPeriod().getStartDay();
                if (i == memberships.size() - 1) {
                    System.out.println(" " + name + " (" + startDay.toString() + " to "
                            + p.getDayPeriod().getEndDay().toString() + ")");
                } else if (memberships.get(i + 1).getJoinDay().compareTo(p.getDayPeriod().getStartDay()) > 0) {
                    Day endDay = memberships.get(i + 1).getJoinDay().previous()
                            .compareTo(p.getDayPeriod().getEndDay()) < 0
                                    ? memberships.get(i + 1).getJoinDay().previous()
                                    : p.getDayPeriod().getEndDay();
                    System.out.println(" " + name + " (" + startDay.toString() + " to " + endDay.toString() + ")");
                }
            }
        }
    }

}
