package br.com.erudio.unittests.mapper.mocks

import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.model.Person

class MockPerson {
    fun mockEntity(): Person{
        return mockEntity(0)
    }

    fun mockVO(): PersonVO{
        return mockVO(0)
    }

    fun mockEntity(number: Int): Person{
        val person = Person()
        person.address = "Endereço Test$number"
        person.firstName = "Primeiro Nome Test$number"
        person.genero = if (number % 2 == 0) "Macho" else "Femea"
        person.id = number.toLong()
        person.lastName = "Sobrenome Test$number"

        return person
    }

    fun mockVO(number: Int): PersonVO{
        val person = PersonVO()
        person.address = "Endereço Test$number"
        person.firstName = "Primeiro Nome Test$number"
        person.genero = if (number % 2 == 0) "Macho" else "Femea"
        person.key = number.toLong()
        person.lastName = "Sobrenome Test$number"

        return person
    }

    fun mockVOList(): ArrayList<PersonVO> {
        val persons: ArrayList<PersonVO> = ArrayList()
        for(i in 0..13){
            persons.add(mockVO(i))
        }
        return persons
    }

    fun mockEntityList(): ArrayList<Person> {
        val persons: ArrayList<Person> = ArrayList()
        for(i in 0..13){
            persons.add(mockEntity(i))
        }
        return persons
    }

}