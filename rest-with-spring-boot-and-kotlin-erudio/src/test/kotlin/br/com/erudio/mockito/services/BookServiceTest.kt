package br.com.erudio.mockito.services

import br.com.erudio.exceptions.RequiredObjectsIsNullException
import br.com.erudio.repository.BookRepository
import br.com.erudio.services.BookService
import br.com.erudio.unittests.mapper.mocks.MockBooks
import br.com.erudio.unittests.mapper.mocks.MockPerson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class BookServiceTest {

    private lateinit var inputObject: MockBooks

    @InjectMocks
    private lateinit var service: BookService

    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockBooks()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val books = service.findAll()

        assertNotNull(books)
        assertEquals(14,books.size)

        val bookOne = books[1]

        assertNotNull(bookOne)
        assertNotNull(bookOne.key)
//        assertNotNull(personOne.links)
//        assertTrue(personOne.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Autor Teste 1",bookOne.author)
        assertEquals("2017-11-07 15:09:01.674000",bookOne.launchDate)
        assertEquals(30.00,bookOne.price)
        assertEquals("Titulo Teste 1",bookOne.title)

        val bookFour = books[4]

        assertNotNull(bookFour)
        assertNotNull(bookFour.key)
//        assertNotNull(personFour.links)
//        assertTrue(personFour.links.toString().contains("</api/person/v1/4>;rel=\"self\""))
        assertEquals("Autor Teste 4",bookFour.author)
        assertEquals("2017-11-07 15:09:01.674000",bookFour.launchDate)
        assertEquals(20.00,bookFour.price)
        assertEquals("Titulo Teste 4",bookFour.title)

        val bookSeven = books[7]

        assertNotNull(bookSeven)
        assertNotNull(bookSeven.key)
//        assertNotNull(bookSeven.links)
//        assertTrue(bookSeven.links.toString().contains("</api/person/v1/7>;rel=\"self\""))
        assertEquals("Autor Teste 7",bookSeven.author)
        assertEquals("2017-11-07 15:09:01.674000",bookSeven.launchDate)
        assertEquals(30.00,bookSeven.price)
        assertEquals("Titulo Teste 7",bookSeven.title)
    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(1)
        book.id = 1L
        `when`(repository.findById(1)).thenReturn(Optional.of(book))

        val result = service.findById(1)

        assertNotNull(result)
        assertNotNull(result.key)
//        assertNotNull(result.links)
//        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Autor Teste 1",result.author)
        assertEquals("2017-11-07 15:09:01.674000",result.launchDate)
        assertEquals(30.00,result.price)
        assertEquals("Titulo Teste 1",result.title)
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
//        assertNotNull(result.links)
//        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Autor Teste 1",result.author)
        assertEquals("2017-11-07 15:09:01.674000",result.launchDate)
        assertEquals(30.00,result.price)
        assertEquals("Titulo Teste 1",result.title)
    }

    @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectsIsNullException::class.java
        ) { service.create(null) }

        val expectedMessage = "Não é permitido persistir um objeto nulo!"
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
//        assertNotNull(result.links)
//        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Autor Teste 1",result.author)
        assertEquals("2017-11-07 15:09:01.674000",result.launchDate)
        assertEquals(30.00,result.price)
        assertEquals("Titulo Teste 1",result.title)
    }

    @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectsIsNullException::class.java
        ) { service.update(null) }

        val expectedMessage = "Não é permitido persistir um objeto nulo!"
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