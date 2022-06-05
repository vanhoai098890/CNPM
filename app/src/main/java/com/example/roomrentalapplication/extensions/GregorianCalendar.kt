package com.example.roomrentalapplication.extensions

import java.util.*

fun GregorianCalendar.calcDatesWith(date: GregorianCalendar): Int {
    val firstDate = GregorianCalendar(this.get(Calendar.YEAR),this.get(Calendar.MONTH),this.get(Calendar.DATE))
    val secondDate = GregorianCalendar(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DATE))
    var result = 1
    while (firstDate.before(secondDate)) {
        result += 1
        firstDate.add(Calendar.DAY_OF_MONTH, 1);
    }
    return result
}
