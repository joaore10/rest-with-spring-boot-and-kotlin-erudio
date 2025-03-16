package br.com.erudio.integrationtests.controller.withyaml.mapper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.databind.ObjectMapper as JacksonObjectMapper
import io.restassured.mapper.ObjectMapper
import io.restassured.mapper.ObjectMapperDeserializationContext
import io.restassured.mapper.ObjectMapperSerializationContext
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException


class YAMLMapper : ObjectMapper {

    private val objectMapper: JacksonObjectMapper = JacksonObjectMapper(YAMLFactory())
    private val typeFactory: TypeFactory = TypeFactory.defaultInstance()

    init {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserialize(p0: ObjectMapperDeserializationContext): Any? {
        try {
            val dataToSerialize = p0.dataToDeserialize.asString()
            val type = p0.type as Class<*>
            return objectMapper.readValue(dataToSerialize, typeFactory.constructType(type))
        } catch (e: JsonMappingException){
            e.printStackTrace()
        } catch (e: JsonProcessingException){
            e.printStackTrace()
        }

        return null
    }

    override fun serialize(p0: ObjectMapperSerializationContext): Any? {
        try {
            return objectMapper.writeValueAsString(p0.objectToSerialize)
        } catch (e: JsonProcessingException){
            e.printStackTrace()
        }

        return null
    }
}