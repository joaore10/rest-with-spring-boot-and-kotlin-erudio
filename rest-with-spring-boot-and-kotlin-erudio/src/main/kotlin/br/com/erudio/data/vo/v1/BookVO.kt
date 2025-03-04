package br.com.erudio.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel

@JsonPropertyOrder("id","author","launchDate","price","title")
data class BookVO(

    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long = 0,
    var author: String = "",
    var launchDate: String = "",
    var price: Double = 0.00,
    var title: String = ""
): RepresentationModel<BookVO>()
