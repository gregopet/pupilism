package co.petrin.pupilism.storage

import co.petrin.pupilism.Klass
import co.petrin.pupilism.Time
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.Reader
import java.io.StringReader
import java.io.Writer

private val yamlFactory = YAMLFactory().enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
private val mapper = ObjectMapper(yamlFactory).registerModule(KotlinModule()).registerModule(DeserializationModule)
private val readingMapper = mapper.readerFor(SavedProject::class.java)



/** Reads the input [stream] and deserializes a saved project from it */
fun deserialize(reader: Reader): SavedProject = readingMapper.readValue<SavedProject>(reader)

/** Saves the [project] to the given [writer] */
fun serialize(project: SavedProject, writer: Writer) = mapper.writeValue(writer, project)

/**
 * Reads the input [text] and deserializes it to a saved project.
 * Mainly used for easier testing.
 */
fun deserialize(text: String): SavedProject = deserialize(StringReader(text))

/**
 * Serializes the given [project] and writes it into a String.
 * Mainly used for easier testing.
 */
fun serializeString(project: SavedProject): String = mapper.writeValueAsString(project)



object DeserializationModule: SimpleModule() {
    init {
        addDeserializer(Klass::class.java,  object: JsonDeserializer<Klass>() {
            override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Klass {
                // Simplification, but should be enough for our purposes?
                val className = p.readValueAs(String::class.java)
                return Klass(className[0].toString().toInt(), className[1])
            }
        })
        addSerializer(Klass::class.java, object: JsonSerializer<Klass>() {
            override fun serialize(value: Klass?, gen: JsonGenerator, serializers: SerializerProvider) {
                if (value == null) {
                    gen.writeNull()
                } else {
                    gen.writeString("${value.year}${value.name}")
                }
            }
        })
        addSerializer(Time::class.java, object: JsonSerializer<Time>() {
            override fun serialize(value: Time?, gen: JsonGenerator, serializers: SerializerProvider) {
                if (value == null) gen.writeNull() else gen.writeString(value.toString())
            }
        })
    }
}