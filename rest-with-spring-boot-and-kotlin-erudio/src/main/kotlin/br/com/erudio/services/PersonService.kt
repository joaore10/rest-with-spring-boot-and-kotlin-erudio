package br.com.erudio.services

import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.model.Person
import br.com.erudio.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Procurando Todas as pessoas")
        return repository.findAll()
    }

    fun findById(id: Long): Person {
        logger.info("Procurando uma pessoa!")

        return repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse ID!")}
    }

    fun create(person:Person) : Person{
        logger.info("Criando uma pessoa with name ${person.firstName}!")
        return repository.save(person)
    }

    fun update(person:Person) : Person{
        logger.info("Alterando a pessoa do id ${person.id}")
        val entity = repository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse ID!")}

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.genero = person.genero
        entity.address = person.address

        return repository.save(entity)
    }

    fun delete(id: Long){
        logger.info("Deletando a pessoa do id $id")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse Id!") }

        repository.delete(entity)
    }

}