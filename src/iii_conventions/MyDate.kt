package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Iterator<MyDate> {

    override fun hasNext(): Boolean = true

    override fun next(): MyDate = this.nextDate()

}

operator fun MyDate.plus(ti: TimeInterval): MyDate = when (ti) {
    TimeInterval.DAY -> MyDate(this.year, this.month, this.dayOfMonth + 1)
    TimeInterval.WEEK -> MyDate(this.year, this.month, this.dayOfMonth + 7)
    TimeInterval.YEAR -> MyDate(this.year + 1, this.month, this.dayOfMonth)
    else -> MyDate(this.year, this.month, this.dayOfMonth)
}

fun MyDate.nextDate() = MyDate(this.year, this.month, this.dayOfMonth + 1)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.compareTo(other: MyDate): Int {
    if (this.year > other.year) {
        return 1
    } else if (this.year < other.year) {
        return -1
    } else {
        if (this.month > other.month) {
            return 1
        } else if (this.month < other.month) {
            return -1
        } else {
            if (this.dayOfMonth > other.dayOfMonth) {
                return 1
            } else if (this.dayOfMonth < other.dayOfMonth) {
                return -1
            } else {
                return 0
            }
        }
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) {

}

operator fun DateRange.contains(date: MyDate): Boolean = date > this.start && date <= this.endInclusive

operator fun DateRange.iterator(): Iterator<MyDate> = MyDate(this.start.year, this.start.month, this.start.dayOfMonth)