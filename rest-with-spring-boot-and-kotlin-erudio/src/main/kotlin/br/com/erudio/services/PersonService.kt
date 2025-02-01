package br.com.erudio.services

import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.data.vo.v2.PersonVO as PersonVOV2
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
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

    fun findAll(): List<PersonVO> {
        logger.info("Procurando Todas as pessoas")
        val persons =  repository.findAll()
        return DozerMapper.parseListObjects(persons, PersonVO::class.java)
    }

    fun findById(id: Long): PersonVO {
        logger.info("Procurando uma pessoa!")

        var person =  repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse ID!")}
        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun create(person:PersonVO) : PersonVO{
        logger.info("Criando uma pessoa with name ${person.firstName}!")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)
        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun createV2(person:PersonVOV2) : PersonVOV2{
        logger.info("Criando uma pessoa with name ${person.firstName}!")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)
        return DozerMapper.parseObject(repository.save(entity), PersonVOV2::class.java)
    }

    fun update(person:PersonVO) : PersonVO{
        logger.info("Alterando a pessoa do id ${person.id}")
        val entity = repository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse ID!")}

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.genero = person.genero
        entity.address = person.address

        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun delete(id: Long){
        logger.info("Deletando a pessoa do id $id")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse Id!") }

        repository.delete(entity)
    }

}