package br.com.erudio.data.vo.v2

import java.util.Date

data class PersonVO (

    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var genero: String = "",
    var dataNascimento: Date? = null
)