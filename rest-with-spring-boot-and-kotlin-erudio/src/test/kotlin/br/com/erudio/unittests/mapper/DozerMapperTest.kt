package br.com.erudio.unittests.mapper

import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.mapper.DozerMapper
import br.com.erudio.model.Person
import br.com.erudio.unittests.mapper.mocks.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DozerMapperTest {

    var inputObject: MockPerson? = null

    @BeforeEach
    fun setUp(){
        inputObject = MockPerson()
    }

    @Test
    fun parseEntityToVOTest() {
        val output: PersonVO = DozerMapper.parseObject(inputObject!!.mockEntity(), PersonVO::class.java)
        assertEquals(0, output.id)
        assertEquals("Primeiro Nome Test0", output.firstName)
        assertEquals("Sobrenome Test0", output.lastName)
        assertEquals("Endereço Test0", output.address)
        assertEquals("Macho", output.genero)

    }

    @Test
    fun parseEntityToVOListTest() {
        val outputList: ArrayList<PersonVO> = DozerMapper.parseListObjects(inputObject!!.mockEntityList(), PersonVO::class.java)

        val outputZero: PersonVO = outputList[0]
        assertEquals(0, outputZero.id)
        assertEquals("Primeiro Nome Test0", outputZero.firstName)
        assertEquals("Sobrenome Test0", outputZero.lastName)
        assertEquals("Endereço Test0", outputZero.address)
        assertEquals("Macho", outputZero.genero)

        val sevenZero: PersonVO = outputList[7]
        assertEquals(7, sevenZero.id)
        assertEquals("Primeiro Nome Test7", sevenZero.firstName)
        assertEquals("Sobrenome Test7", sevenZero.lastName)
        assertEquals("Endereço Test7", sevenZero.address)
        assertEquals("Femea", sevenZero.genero)


        val dozeZero: PersonVO = outputList[12]
        assertEquals(12, dozeZero.id)
        assertEquals("Primeiro Nome Test12", dozeZero.firstName)
        assertEquals("Sobrenome Test12", dozeZero.lastName)
        assertEquals("Endereço Test12", dozeZero.address)
        assertEquals("Macho", dozeZero.genero)

        val elevenZero: PersonVO = outputList[11]
        assertEquals(11, elevenZero.id)
        assertEquals("Primeiro Nome Test11", elevenZero.firstName)
        assertEquals("Sobrenome Test11", elevenZero.lastName)
        assertEquals("Endereço Test11", elevenZero.address)
        assertEquals("Femea", elevenZero.genero)

    }

    @Test
    fun parseVOToEntity() {
        val output: Person = DozerMapper.parseObject(inputObject!!.mockVO(), Person::class.java)
        assertEquals(0, output.id)
        assertEquals("Primeiro Nome Test0", output.firstName)
        assertEquals("Sobrenome Test0", output.lastName)
        assertEquals("Endereço Test0", output.address)
        assertEquals("Macho", output.genero)

    }


    @Test
    fun parseVOToEntityListTest() {
        val outputList: ArrayList<Person> = DozerMapper.parseListObjects(inputObject!!.mockVOList(), Person::class.java)

        val outputZero: Person = outputList[0]
        assertEquals(0, outputZero.id)
        assertEquals("Primeiro Nome Test0", outputZero.firstName)
        assertEquals("Sobrenome Test0", outputZero.lastName)
        assertEquals("Endereço Test0", outputZero.address)
        assertEquals("Macho", outputZero.genero)

        val sevenZero: Person = outputList[7]
        assertEquals(7, sevenZero.id)
        assertEquals("Primeiro Nome Test7", sevenZero.firstName)
        assertEquals("Sobrenome Test7", sevenZero.lastName)
        assertEquals("Endereço Test7", sevenZero.address)
        assertEquals("Femea", sevenZero.genero)


        val dozeZero: Person = outputList[12]
        assertEquals(12, dozeZero.id)
        assertEquals("Primeiro Nome Test12", dozeZero.firstName)
        assertEquals("Sobrenome Test12", dozeZero.lastName)
        assertEquals("Endereço Test12", dozeZero.address)
        assertEquals("Macho", dozeZero.genero)

        val elevenZero: Person = outputList[11]
        assertEquals(11, elevenZero.id)
        assertEquals("Primeiro Nome Test11", elevenZero.firstName)
        assertEquals("Sobrenome Test11", elevenZero.lastName)
        assertEquals("Endereço Test11", elevenZero.address)
        assertEquals("Femea", elevenZero.genero)

    }
}