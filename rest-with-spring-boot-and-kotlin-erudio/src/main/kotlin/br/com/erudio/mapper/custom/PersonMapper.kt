package br.com.erudio.mapper.custom

import br.com.erudio.data.vo.v2.PersonVO
import br.com.erudio.model.Person
import org.springframework.stereotype.Service
import java.util.Date

@Service
class PersonMapper {

    fun mapEnjtityToVO(person: Person): PersonVO {
        val vo = PersonVO()
        vo.id = person.id
        vo.firstName = person.firstName
        vo.lastName = person.lastName
        vo.genero = person.genero
        vo.address = person.address
        vo.dataNascimento = Date()

        return vo
    }

    fun mapVOToEntity(person: PersonVO): Person {
        val entity = Person()
        entity.id = person.id
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.genero = person.genero
        entity.address = person.address
        //entity.dataNascimento = Date()
        return entity
    }
}