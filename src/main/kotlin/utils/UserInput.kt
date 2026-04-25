package utils

fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}

fun readNextInt(prompt: String?): Int {
    print(prompt)

    while (true) {
        try {
            return readln().toInt()
        } catch (e: NumberFormatException) {
            print("Please enter a valid number: ")
        }
    }
}

fun readNextDouble(prompt: String?): Double {
    print(prompt)

    while (true) {
        try {
            return readln().toDouble()
        } catch (e: NumberFormatException) {
            print("Please enter a valid decimal number: ")
        }
    }
}
