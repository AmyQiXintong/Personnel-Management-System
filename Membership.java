public class Membership implements Comparable<Membership> {
    String teamName;
    Day joinDay;

    Membership(String teamName, Day joinDay) {
        this.teamName = teamName;
        this.joinDay = joinDay;
    }

    @Override
    public int compareTo(Membership m) {
        return this.joinDay.compareTo(m.getJoinDay());
    }

    public String getTeamName() {
        return teamName;
    }

    public Day getJoinDay() {
        return joinDay;
    }
}