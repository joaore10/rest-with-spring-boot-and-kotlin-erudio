package br.com.erudio.controllers

import br.com.erudio.data.vo.v1.BookVO
import br.com.erudio.services.BookService
import br.com.erudio.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/books/v1")
@Tag(name = "Livros", description = "Endpoint para gerenciar os Livros")
class BookController {

    @Autowired
    private lateinit var service: BookService

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML])
    @Operation(summary = "Finds all Books", description = "Finds all Books", tags = ["Livros"], responses = [
        ApiResponse(description = "Success", responseCode = "200", content = [
            Content(array = ArraySchema(schema = Schema(implementation = BookVO::class)))
        ]),
        ApiResponse(description = "No Content", responseCode = "204", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Bad Request", responseCode = "400", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Unauthorized", responseCode = "401", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Not Found", responseCode = "404", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Internal Error", responseCode = "500", content = [
            Content(schema = Schema(implementation = Unit::class))
        ])
    ])
    fun findAll(): List<BookVO> {
        return service.findAll()
    }

    @GetMapping(value=["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML])
    @Operation(summary = "Finds a Book", description = "Finds a Book", tags = ["Livros"], responses = [
        ApiResponse(description = "Success", responseCode = "200", content = [
            Content(schema = Schema(implementation = BookVO::class))
        ]),
        ApiResponse(description = "No Content", responseCode = "204", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Bad Request", responseCode = "400", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Unauthorized", responseCode = "401", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Not Found", responseCode = "404", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Internal Error", responseCode = "500", content = [
            Content(schema = Schema(implementation = Unit::class))
        ])
    ])
    fun findById(@PathVariable(value = "id") id: Long
    ): BookVO {
        return service.findById(id)
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML])
    @Operation(summary = "Adds a New Book", description = "Adds a New Book", tags = ["Livros"], responses = [
        ApiResponse(description = "Success", responseCode = "200", content = [
            Content(schema = Schema(implementation = BookVO::class))
        ]),
        ApiResponse(description = "Bad Request", responseCode = "400", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Unauthorized", responseCode = "401", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Internal Error", responseCode = "500", content = [
            Content(schema = Schema(implementation = Unit::class))
        ])
    ])
    fun create(@RequestBody BookVO: BookVO): BookVO {
        return service.create(BookVO)
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML], consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML])
    @Operation(summary = "Update a Book Information", description = "Update a Book Information", tags = ["Livros"], responses = [
        ApiResponse(description = "Success", responseCode = "200", content = [
            Content(schema = Schema(implementation = BookVO::class))
        ]),
        ApiResponse(description = "No Content", responseCode = "204", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Bad Request", responseCode = "400", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Unauthorized", responseCode = "401", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Not Found", responseCode = "404", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Internal Error", responseCode = "500", content = [
            Content(schema = Schema(implementation = Unit::class))
        ])
    ])
    fun update(@RequestBody BookVO: BookVO): BookVO {
        return service.update(BookVO)
    }

    @DeleteMapping(value=["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML])
    @Operation(summary = "Deletes a Book", description = "Deletes a Book", tags = ["Livros"], responses = [
        ApiResponse(description = "No Content", responseCode = "204", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Bad Request", responseCode = "400", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Unauthorized", responseCode = "401", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Not Found", responseCode = "404", content = [
            Content(schema = Schema(implementation = Unit::class))
        ]),
        ApiResponse(description = "Internal Error", responseCode = "500", content = [
            Content(schema = Schema(implementation = Unit::class))
        ])
    ])
    fun delete(@PathVariable(value = "id") id: Long) : ResponseEntity<*> {
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}