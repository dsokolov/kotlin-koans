package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int =
            if (year == other.year) {
                if (month == other.month) {
                    if (dayOfMonth == other.dayOfMonth) {
                        0
                    } else {
                        dayOfMonth.compareTo(other.dayOfMonth)
                    }
                } else {
                    month.compareTo(other.month)
                }
            } else {
                year.compareTo(other.year)
            }

}


operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(n: Int) = RepeatedTimeInterval(this, n)

}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

operator fun MyDate.plus(other: TimeInterval) = this.addTimeIntervals(other, 1)

operator fun MyDate.plus(other: RepeatedTimeInterval) = this.addTimeIntervals(other.ti, other.n)

class DateRange(
        override val start: MyDate,
        override val endInclusive: MyDate
) : ClosedRange<MyDate>, Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {

            private var current = start.copy()

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val result = current.copy()
                current = current.nextDay()
                return result
            }

        }
    }

}
