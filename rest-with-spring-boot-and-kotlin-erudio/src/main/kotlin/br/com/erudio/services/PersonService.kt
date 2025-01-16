package br.com.erudio.services

import br.com.erudio.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {

    private val counter: AtomicLong = AtomicLong()

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Procurando Todas as pessoas")

        val persons: MutableList<Person> = ArrayList()

        for (i in 0..7){
            val person = mockPerson(i)
            persons.add(person)

        }

        return persons
    }

    fun findById(id: Long): Person {
        logger.info("Procurando uma pessoa!")

        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "Leandro"
        person.lastName = "Almeida"
        person.genero = "Macho"
        person.address = "Rua mato Grosso - Campinas - São paulo"

        return person
    }

    fun create(person:Person) : Person{
        //
        return person
    }

    fun update(person:Person) : Person{
        //
        return person
    }

    fun delete(id: Long){
    }

    private fun mockPerson(i: Int): Person {
        val person = Person()

        person.id = counter.incrementAndGet()
        person.firstName = "Nome $i"
        person.lastName = "Sobrenome $i"
        person.genero = "Macho"
        person.address = "Rua mato Grosso, $i - Campinas - São paulo"

        return person
    }
}