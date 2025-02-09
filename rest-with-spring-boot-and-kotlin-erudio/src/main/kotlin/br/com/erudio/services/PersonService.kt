package br.com.erudio.services

import br.com.erudio.controllers.PersonController
import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
import br.com.erudio.model.Person
import br.com.erudio.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
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
        val vos = DozerMapper.parseListObjects(persons, PersonVO::class.java)
        for (person in vos){
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): PersonVO {
        logger.info("Procurando uma pessoa com ID $id!")

        var person =  repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse ID!")}
        val personVO: PersonVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(person:PersonVO) : PersonVO{
        logger.info("Criando uma pessoa with name ${person.firstName}!")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)
        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun update(person:PersonVO) : PersonVO{
        logger.info("Alterando a pessoa do id ${person.key}")
        val entity = repository.findById(person.key)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse ID!")}

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.genero = person.genero
        entity.address = person.address

        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun delete(id: Long){
        logger.info("Deletando a pessoa do id $id")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse Id!") }

        repository.delete(entity)
    }

}