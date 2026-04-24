package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import models.Exercise
import models.Workout
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JSONSerializer(private val file: File) : Serializer {

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xstream = XStream(JettisonMappedXmlDriver())
        xstream.setMode(XStream.NO_REFERENCES)
        xstream.alias("workout", Workout::class.java)
        xstream.alias("exercise", Exercise::class.java)
        xstream.allowTypes(arrayOf(Workout::class.java, Exercise::class.java, ArrayList::class.java))

        val outputStream = FileWriter(file)
        xstream.toXML(obj, outputStream)
        outputStream.close()
    }

    @Throws(Exception::class)
    override fun read(): Any? {
        val xstream = XStream(JettisonMappedXmlDriver())
        xstream.setMode(XStream.NO_REFERENCES)
        xstream.alias("workout", Workout::class.java)
        xstream.alias("exercise", Exercise::class.java)
        xstream.allowTypes(arrayOf(Workout::class.java, Exercise::class.java, ArrayList::class.java))

        val inputStream = FileReader(file)
        val obj = xstream.fromXML(inputStream)
        inputStream.close()
        return obj
    }
}
