package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var right = 0
    var wrong = 0

    if (secret == guess) {
        return Evaluation(4,0)
    }

    val countMap = mutableMapOf<Char,Int>()

    for(i in secret.indices) {
        countMap[secret[i]]=0
    }

    for(i in secret.indices) {
        countMap[secret[i]] = countMap.getOrDefault(secret[i],0) + 1
    }

    for(i in guess.indices) {
        if(guess[i] == secret[i]) {
            right+=1
            countMap[guess[i]] = countMap.getOrDefault(guess[i], 0) - 1
        }
    }

    for(i in guess.indices) {
        if(countMap.getOrDefault(guess[i], 0) > 0) {
            if(guess[i] != secret[i]) {
                wrong+=1
                countMap[guess[i]] = countMap.getOrDefault(guess[i], 0) - 1
            }
        }

    }

    return Evaluation(right,wrong)
}