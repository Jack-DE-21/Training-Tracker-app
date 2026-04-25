package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Exercise
import models.Workout
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class XMLSerializer(private val file: File) : Serializer {
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xstream = XStream(DomDriver())
        xstream.allowTypes(
            arrayOf(
                Workout::class.java,
                Exercise::class.java,
                ArrayList::class.java,
                java.util.HashSet::class.java,
                java.util.LinkedHashSet::class.java,
            ),
        )

        val outputStream = FileWriter(file)
        xstream.toXML(obj, outputStream)
        outputStream.close()
    }

    @Throws(Exception::class)
    override fun read(): Any? {
        if (!file.exists() || file.length() == 0L) {
            return ArrayList<Workout>()
        }

        val xstream = XStream(DomDriver())
        xstream.allowTypes(
            arrayOf(
                Workout::class.java,
                Exercise::class.java,
                ArrayList::class.java,
                java.util.HashSet::class.java,
                java.util.LinkedHashSet::class.java,
            ),
        )

        val inputStream = FileReader(file)
        val obj = xstream.fromXML(inputStream)
        inputStream.close()
        return obj
    }
}
