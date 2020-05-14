package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    val newList = mutableListOf<T>()
    var i=0
    while(i < this.size) {
        if(this[i]==null) {
            i++
        }
        else {
            val current= this[i]
            var j=i+1
            while(j<this.size && this[j]==null) {
                j+=1
                i+=1
            }

            if(j==this.size) {
                current?.let { newList.add(it) }
                break
            }

            else if(this[j]!=current) {
                current?.let { newList.add(it) }
                i+=1
            }

            else {
                val newval = this[j]?.let { merge(it) }
                if (newval != null) {
                    newList.add(newval)
                }
                i+=2
            }

        }
    }
    return newList

}

