public class DayPeriod implements Comparable<DayPeriod> {
    private Day start;
    private Day end;

    public DayPeriod() {
    };

    public DayPeriod(Day start, Day end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start.toString() + " to " + end.toString();
    }

    public Day getStartDay() {
        return start;
    }

    public Day getEndDay() {
        return end;
    }

    public boolean hasOverlap(DayPeriod anotherPeriod) {
        if ((this.start.compareTo(anotherPeriod.getStartDay()) < 0)
                && (this.end.compareTo(anotherPeriod.getStartDay()) < 0))
            return false;
        else if ((this.end.compareTo(anotherPeriod.getEndDay()) > 0)
                && (this.start.compareTo(anotherPeriod.getEndDay()) > 0))
            return false;
        return true;
    }

    @Override
    public int compareTo(DayPeriod another) {
        if (this.start.compareTo(another.getStartDay()) == 0)
            return 0;
        else if (this.start.compareTo(another.getStartDay()) < 0)
            return -1;
        return 1;
    }

    public boolean containDay(Day aDayToCheck) {
        if (aDayToCheck.compareTo(start) >= 0 && aDayToCheck.compareTo(end) <= 0) {
            return true;
        }
        return false;
    }

    public DayPeriod advance() {
        return new DayPeriod(start.next(), end.next());
    }

}
