public class Day implements Cloneable, Comparable<Day> {

	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	public void set(String sDay) {
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]);
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
	}

	public Day(String sDay) {
		set(sDay);
	}

	@Override
	public String toString() {
		return day + "-" + MonthNames.substring((month - 1) * 3, (month) * 3) + "-" + year;
	}

	@Override
	public Day clone() {
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}

	@Override
	public int compareTo(Day anotherDay) {
		int thisDayInt = year * 10000 + month * 100 + day;
		int anotherDayInt = anotherDay.getYear() * 10000 + anotherDay.getMonth() * 100 + anotherDay.getDay();
		if (thisDayInt == anotherDayInt)
			return 0;
		else if (thisDayInt > anotherDayInt)
			return 1;
		else
			return -1;
	}

	public Day(int y, int m, int d) {
		this.year = y;
		this.month = m;
		this.day = d;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	static public boolean isLeapYear(int y) {
		if (y % 400 == 0)
			return true;
		else if (y % 100 == 0)
			return false;
		else if (y % 4 == 0)
			return true;
		else
			return false;
	}

	static public boolean valid(int y, int m, int d) {
		if (m < 1 || m > 12 || d < 1)
			return false;
		switch (m) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return d <= 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return d <= 30;
			case 2:
				if (isLeapYear(y))
					return d <= 29;
				else
					return d <= 28;
		}
		return false;
	}

	public Day advance(int days) {
		if (days == 1)
			return this;

		Day end = this.next(); // to make the compiler happy
		for (int i = 0; i < days - 2; i++) {
			end = end.next();
		}
		return end;
	}

	public boolean isEndOfAMonth() {
		if (valid(year, month, day + 1))
			return false;
		else
			return true;
	}

	public Day next() {
		if (isEndOfAMonth())
			if (month == 12)
				return new Day(year + 1, 1, 1);
			else
				return new Day(year, month + 1, 1);
		else
			return new Day(year, month, day + 1);
	}

	public Day previous() {
		if (day == 1) {
			if (month == 1)
				return new Day(year - 1, 12, 31);
			else {
				switch (month) {
					case 5:
					case 7:
					case 10:
					case 12:
						return new Day(year, month - 1, 30);
					case 2:
					case 4:
					case 6:
					case 8:
					case 9:
					case 11:
						return new Day(year, month - 1, 31);
					case 3:
						if (isLeapYear(year))
							return new Day(year, month - 1, 29);
						else
							return new Day(year, month - 1, 28);
				}
			}
		}
		return new Day(year, month, day - 1);
	}

	public static boolean validDay(String strOfDay) throws ExInvalidDate {
		String[] sDayParts = strOfDay.split("-");
		if (sDayParts.length < 3)
			throw new ExInvalidDate();
		int day = Integer.parseInt(sDayParts[0]);
		int year = Integer.parseInt(sDayParts[2]);
		if (!MonthNames.contains(sDayParts[1]))
			throw new ExInvalidDate();
		int month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
		return Day.valid(year, month, day);
	}

	public boolean earlierThanSystemDate() {
		return this.compareTo(SystemDate.getInstance()) <= 0;
	}

}
