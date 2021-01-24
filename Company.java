import java.util.ArrayList;
import java.util.Collections;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;

    private static Company instance = new Company();

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
        allProjects = new ArrayList<>();
    }

    public static Company getInstance() {
        return instance;
    }

    public void listTeams() {
        Team.list(allTeams);
    }

    public void listEmployees() {
        for (Employee employee : allEmployees) {
            if (isMemberOfATeam(employee.getName()))
                System.out.println(employee.getName() + " (" + findMyTeam(employee).getName() + ")");
            else
                System.out.println(employee.getName());
        }
    }

    public void listProjects() {
        System.out.printf("%-9s%-14s%-13s%-13s%-13s\n", "Project", "Est manpower", "Team", "Start Day", "End Day");
        for (Project project : allProjects) {
            if (isProjectOfATeam(project.getName())) {
                System.out.printf("%-9s%-14s%-13s%-13s%-13s\n", project.getName(), project.getEstManpower(),
                        findMyTeam(project).getName(), project.getDayPeriod().getStartDay(),
                        project.getDayPeriod().getEndDay());
            } else
                System.out.printf("%-9s%-14s%-13s\n", project.getName(), project.getEstManpower(), "(Not Assigned)");
        }
    }

    public boolean employeeExists(String aNameToCheck) {
        for (Employee e : allEmployees) {
            if (e.getName().equals(aNameToCheck))
                return true;
        }
        return false;
    }

    public boolean teamExists(String aNameToCheck) {
        for (Team t : allTeams) {
            if (t.getName().equals(aNameToCheck))
                return true;
        }
        return false;
    }

    public boolean projectExists(String aCodeToCheck) {
        for (Project p : allProjects) {
            if (p.getName().equals(aCodeToCheck))
                return true;
        }
        return false;
    }

    public boolean isHeadOfATeam(String aName) {
        for (Team t : allTeams) {
            if (t.getHead().getName().equals(aName))
                return true;
        }
        return false;
    }

    public boolean isMemberOfATeam(String aName) {
        for (Team t : allTeams) {
            if (t.hasMember(aName))
                return true;
        }
        return false;
    }

    public boolean isProjectOfATeam(String aName) {
        for (Team t : allTeams) {
            if (t.hasProject(aName))
                return true;
        }
        return false;
    }

    public Employee createEmployee(String name) {
        Employee e = new Employee(name);
        allEmployees.add(e);
        Collections.sort(allEmployees);
        return e;
    }

    public Team createTeam(String teamName, String headName) {
        Employee e = Employee.searchEmployee(allEmployees, headName);
        Team t = new Team(teamName, e);
        allTeams.add(t);
        Collections.sort(allTeams);
        return t;
    }

    public Project createProject(String projectName, int estManpower) {
        Project p = new Project(projectName, estManpower);
        allProjects.add(p);
        Collections.sort(allProjects);
        return p;
    }

    public void addEmployee(Employee e) {
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }

    public void removeEmployee(Employee e) {
        allEmployees.remove(e);
    }

    public void addTeam(Team t) {
        allTeams.add(t);
        Collections.sort(allTeams);
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    public void addProject(Project p) {
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    public void removeProject(Project p) {
        allProjects.remove(p);
    }

    public Team findMyTeam(Employee e) {
        for (Team t : allTeams) {
            if (t.hasMember(e.getName())) {
                return t;
            }
        }
        return null;
    }

    public Team findMyTeam(Project p) {
        for (Team t : allTeams) {
            if (t.hasProject(p.getName())) {
                return t;
            }
        }
        return null;
    }

    public Employee findEmployee(String name) {
        return Employee.searchEmployee(allEmployees, name);
    }

    public Team findTeam(String name) {
        return Team.searchTeam(allTeams, name);
    }

    public Project findProject(String code) {
        return Project.searchProject(allProjects, code);
    }

    public void joinTeam(String tName, String eName) {
        Employee e = Employee.searchEmployee(allEmployees, eName);
        Team t = Team.searchTeam(allTeams, tName);
        t.addMember(e);
    }

    public void quitTeam(String tName, String eName) {
        Employee e = Employee.searchEmployee(allEmployees, eName);
        Team t = Team.searchTeam(allTeams, tName);
        t.removeMember(e);
    }

    public void changeTeam(String otName, String ntName, String eName) {
        quitTeam(otName, eName);
        joinTeam(ntName, eName);
    }

    public void assignProject(String pName, String tName, String sDay) throws ExOverlap {
        Project p = Project.searchProject(allProjects, pName);
        Team t = Team.searchTeam(allTeams, tName);
        Day s = new Day(sDay);
        int days = (p.getManPowerInt() / t.numOfMembers()) + ((p.getManPowerInt() % t.numOfMembers() == 0) ? 0 : 1);
        Day e = s.advance(days);
        DayPeriod dp = new DayPeriod(s, e);
        if (!t.notOccupied(dp))
            throw new ExOverlap("The team is not available during the period ", dp);
        p.setPeriod(dp);
        t.takeProject(p, dp);
    }

    public void cancelProject(String pName, String tName) {
        Project p = Project.searchProject(allProjects, pName);
        Team t = Team.searchTeam(allTeams, tName);
        DayPeriod dp = p.getDayPeriod();
        t.dropProject(p, dp);
    }

    public void suggestTeam(String pName) {
        Day suggestEnd = null; // to make the compiler happy
        int days;

        Project p = Project.searchProject(allProjects, pName);

        for (int i = 0; i < allTeams.size(); i++) {
            Team t = allTeams.get(i);
            days = (p.getManPowerInt() / t.numOfMembers()) + ((p.getManPowerInt() % t.numOfMembers() == 0) ? 0 : 1);
            Day s = t.getEarliestUnoccupiedDay();
            Day e = s.advance(days);
            DayPeriod dp = new DayPeriod(s, e);

            while (!t.notOccupied(dp))
                dp = dp.advance();
            if (i == 0 || dp.getEndDay().compareTo(suggestEnd) < 0) {
                suggestEnd = dp.getEndDay();
            }
        }

        for (Team t : allTeams) {
            days = (p.getManPowerInt() / t.numOfMembers()) + ((p.getManPowerInt() % t.numOfMembers() == 0) ? 0 : 1);
            Day suggestStart = null; // to make the compiler happy
            if (days == 1)
                suggestStart = suggestEnd;
            else {
                suggestStart = suggestEnd.previous();
                for (int i = 0; i < days - 2; i++)
                    suggestStart = suggestStart.previous();
            }
            DayPeriod suggestDayPeriod = new DayPeriod(suggestStart, suggestEnd);
            if (SystemDate.getInstance().compareTo(suggestStart) < 0 && t.notOccupied(suggestDayPeriod))
                System.out.println(t.getName() + " (Work period: " + suggestDayPeriod.toString() + ")");
        }
    }

    public void showEmployeeDetails(String eName) {
        Employee e = findEmployee(eName);
        if (this.isHeadOfATeam(eName))
            e.listDetails(true);
        else
            e.listDetails(false);
    }

    public void showProjectWorkerDetails(String pCode) {
        Project p = findProject(pCode);
        p.listDetails();
    }

    public void showMemberWork(Project p) {
        for (Employee employee : allEmployees) {
            if (!isHeadOfATeam(employee.getName()))
                employee.recordInATeam(findMyTeam(p), p);
        }
    }
}
