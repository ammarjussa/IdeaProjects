package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
    val allTrips: List<Trip> = this.trips
    var driverList = mutableSetOf<Driver>();
    for(trip in allTrips) {
        driverList.add(trip.driver)
    }

    println(driverList)
    val realDriver: (Driver) -> Boolean = {it !in driverList}

    val allDri = this.allDrivers

    val ans = allDri.filter(realDriver)
    return ans.toSet()
}


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    val passengerRides = mutableMapOf<Passenger, Int>()
    val allOfPassengers = this.allPassengers

    for(passenger in allOfPassengers) {
        passengerRides[passenger] = 0
    }

    val allTrips = this.trips

    for(trip in allTrips) {
        for(passenger in trip.passengers) {
            passengerRides[passenger]=passengerRides.getOrDefault(passenger,0) + 1
        }
    }

    val faithful : (MutableMap.MutableEntry<Passenger,Int>) -> Boolean = {it.value >= minTrips}
    val finalSet = mutableSetOf<Passenger>()
    for(passengerRide in passengerRides) {
        val decision = faithful(passengerRide)
        if (decision) finalSet.add(passengerRide.key)
    }

    return finalSet.toSet()

}


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger>  {
    val findThoseTrips: (Trip) -> Boolean = {it.driver == driver}
    val selectedTrips = this.trips.filter(findThoseTrips)

    val finalSet = mutableSetOf<Passenger>()
    val hashSet = mutableSetOf<Passenger>()

    for(trip in selectedTrips) {
        for(passenger in trip.passengers) {
            if(passenger in hashSet) {
                finalSet.add(passenger)
            }
            else {
                hashSet.add(passenger)
            }
        }
    }

    return finalSet.toSet()
}


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    var passengerData = mutableMapOf<Passenger, Array<Int>>()
    for(passenger in this.allPassengers) {
        var array = arrayOf<Int>(0,0)
        passengerData[passenger] = array
    }


    for(trip in this.trips) {
        var first = trip.discount==null || trip.discount==0.0
        for(passenger in trip.passengers) {
            if(first) {
                var arr = passengerData.getOrDefault(passenger, arrayOf())
                arr[0]=arr[0]+1
                passengerData[passenger]=arr
            }

            else {
                var arr = passengerData.getOrDefault(passenger, arrayOf())
                arr[1]=arr[1]+1
                passengerData[passenger]=arr
            }
        }
    }

    val finalSet = mutableSetOf<Passenger>()
    for(data in passengerData) {
        if(data.value[1] > data.value[0]) {
            finalSet.add(data.key)
        }
    }

    return finalSet.toSet()
}


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    var maxDuration = 0

    if(this.trips.isEmpty()) return null

    for(trip in this.trips) {
        if(trip.duration > maxDuration) maxDuration=trip.duration
    }

    val bucket = arrayOfNulls<Int>((maxDuration/10)+1)
    for(i in bucket.indices) {
        bucket[i]=0
    }

    for(trip in this.trips) {
//        println(trip.duration)
        var index = trip.duration/10
        bucket[index]= bucket[index]?.plus(1)
    }

    var mostFrequent = 0
    var finalIndex = 0
    for(i in bucket.indices) {
        if(bucket[i]!! > mostFrequent) {
            mostFrequent= bucket[i]!!
            finalIndex=i
        }
    }

    return IntRange(finalIndex*10,(finalIndex+1)*10-1)

}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val driverCost = mutableMapOf<Driver, Double>();
    var totalCost = 0.0
    val driverNo = this.allDrivers.size

    for(driver in this.allDrivers) {
        driverCost[driver]= 0.0
    }

    if(this.trips.isEmpty()) return false;
    for(trip in this.trips) {
        driverCost[trip.driver] = driverCost.getOrDefault(trip.driver, 0.0) + trip.cost
        totalCost+=trip.cost
    }

    val sorted = driverCost.toSortedMap(compareByDescending { driverCost[it] })

    val driver20p = driverNo/5
    val cost80p = 4*totalCost/5

    var checkd=0
    var checkc = 0.0

    for(vals in sorted.values) {
        println(vals)
        if(checkd > driver20p) {
            return false;
        }

        if(checkc >=cost80p) {
            return true;
        }

        checkd+=1
        checkc+=vals
    }

    return false;

}