package nicestring

fun containSubStr(line: String) : Boolean {
    return ("bu" in line || "ba" in line || "be" in line)
}

fun doubleLetter(line: String) : Boolean {
    for(i in 0 until line.length-1) {
        if(line[i] == line[i+1]) return true;
    }

    return false;
}

fun String.isNice(): Boolean {
    var conditions: Int = 0
    val decision1: Int = if(containSubStr(this)) 0 else 1

    val hasVowels: (Char) -> Boolean = {it=='a' || it=='e' || it=='i' || it=='o' || it=='u'}
    val filtered = this.filter(hasVowels).toCharArray()
    val decision2: Int = if (filtered.size >= 3) 1 else 0

    val decision3: Int = if(doubleLetter(this)) 1 else 0

    conditions = decision1 + decision2 + decision3

    if(conditions >=2) return true
    return false
}