
// collection of all time zones that are in america. Strip off the "America/" prefix and sort the result

import java.util.TimeZone

val thisIsAmerica = "America/"
val alls = TimeZone.getAvailableIDs
val amis = alls.filter(_.startsWith(thisIsAmerica))
val init = amis.map(_.substring(thisIsAmerica.length))
init.sorted
