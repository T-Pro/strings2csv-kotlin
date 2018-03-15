import java.io.File

fun main(args: Array<String>) {

    val file = File(args.firstOrNull() ?: "Localizable.strings")

    if (!file.exists()) {
        println("Please put a file called Localizable.strings or set as first parameter")
        return
    }

    val bufferedReader = file.bufferedReader()
    val lineList = mutableListOf<String>()

	bufferedReader.useLines { lines ->
        for (line in lines) {
            val cleanLine = line.trim()
            if (cleanLine.startsWith("//") || cleanLine == "") {
                continue
            }
            lineList.add(cleanLine.dropLast(1))
        }
    }

    val tokens: MutableList<Pair<String, String>> = mutableListOf()

	lineList.forEach {
        val keyValue = it.split(" = ")
        tokens.add(keyValue[0].drop(1).dropLast(1) to keyValue[1].drop(1).dropLast(1))
    }

    File("Localizable.csv").printWriter().use { out ->
        tokens.forEach { (key, value) -> 
            out.println("${key}, ${value}")
        }
    }

}