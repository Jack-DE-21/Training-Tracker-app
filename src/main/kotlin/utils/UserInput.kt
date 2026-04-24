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