package br.com.erudio.services

import br.com.erudio.controllers.BookController
import br.com.erudio.data.vo.v1.BookVO
import br.com.erudio.exceptions.RequiredObjectsIsNullException
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
import br.com.erudio.model.Book
import br.com.erudio.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookVO> {
        logger.info(">>>>>>>>>> Procurando todos os LIVROS")
        val books =  repository.findAll()
        val vos = DozerMapper.parseListObjects(books, BookVO::class.java)
        for (book in vos){
            val withSelfRel = linkTo(BookController::class.java).slash(book.key).withSelfRel()
            book.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): BookVO {
        logger.info(">>>>>>>>> Procurando um Livro com ID $id!")

        var book =  repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse ID!") }
        val bookVO: BookVO = DozerMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun create(book: BookVO?) : BookVO {
        if (book == null) throw RequiredObjectsIsNullException()
        logger.info(">>>>>>>>>> Criando uma livro com titulo ${book.title}!")
        var entity: Book = DozerMapper.parseObject(book, Book::class.java)
        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun update(book: BookVO?) : BookVO {
        if (book == null) throw RequiredObjectsIsNullException()
        logger.info(">>>>>>>> Alterando o livro do id ${book.key}")
        val entity = repository.findById(book.key)
            .orElseThrow { ResourceNotFoundException(">>>>>> Não há registro para esse ID!") }

        entity.author = book.author
        entity.launchDate = book.launchDate
        entity.price = book.price
        entity.title = book.title

        val bookVo: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVo.key).withSelfRel()
        bookVo.add(withSelfRel)
        return bookVo
    }

    fun delete(id: Long){
        logger.info(">>>>>>>> Deletando o livro de id $id")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Não há registro para esse Id!") }

        repository.delete(entity)
    }

}