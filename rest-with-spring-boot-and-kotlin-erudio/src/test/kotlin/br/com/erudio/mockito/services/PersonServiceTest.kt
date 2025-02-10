package br.com.erudio.mockito.services

import br.com.erudio.exceptions.RequiredObjectsIsNullException
import br.com.erudio.repository.PersonRepository
import br.com.erudio.services.PersonService
import br.com.erudio.unittests.mapper.mocks.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class PersonServiceTest {

    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository

    @BeforeEach
    fun setUpMock() {
      inputObject = MockPerson()
      MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
    }

    @Test
    fun findById() {
     val person = inputObject.mockEntity(1)
     person.id = 1L
     `when`(repository.findById(1)).thenReturn(Optional.of(person))

     val result = service.findById(1)

     assertNotNull(result)
     assertNotNull(result.key)
     assertNotNull(result.links)
     assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
     assertEquals("Endereço Test1",result.address)
     assertEquals("Primeiro Nome Test1",result.firstName)
     assertEquals("Sobrenome Test1",result.lastName)
     assertEquals("Femea",result.genero)
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.create(vo)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Endereço Test1",result.address)
        assertEquals("Primeiro Nome Test1",result.firstName)
        assertEquals("Sobrenome Test1",result.lastName)
        assertEquals("Femea",result.genero)
    }

    @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectsIsNullException::class.java
        ) { service.create(null) }

        val expectedMessage = "Não é permitido persistir um objeto nulo"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.update(vo)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Endereço Test1",result.address)
        assertEquals("Primeiro Nome Test1",result.firstName)
        assertEquals("Sobrenome Test1",result.lastName)
        assertEquals("Femea",result.genero)
    }

    @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectsIsNullException::class.java
        ) { service.update(null) }

        val expectedMessage = "Não é permitido persistir um objeto nulo"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}