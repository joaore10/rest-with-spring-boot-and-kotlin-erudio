package br.com.erudio.unittests.mocks

import br.com.erudio.data.vo.v1.BookVO
import br.com.erudio.model.Book
import kotlin.collections.ArrayList


class MockBooks {

    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockVO(): BookVO {
        return mockVO(0)
    }

    fun mockEntity(number: Int): Book {
        val entity = Book()
        entity.author = "Autor Teste $number"
        entity.price = if (number % 2 == 0) 20.00 else 30.00
        entity.id = number.toLong()
        entity.title = "Titulo Teste $number"

        return entity
    }

    fun mockVO(number: Int): BookVO {
        val vo = BookVO()
        vo.author = "Autor Teste $number"
        vo.price = if (number % 2 == 0) 20.00 else 30.00
        vo.key = number.toLong()
        vo.title = "Titulo Teste $number"

        return vo
    }

    fun mockVOList(): ArrayList<BookVO> {
        val books: ArrayList<BookVO> = ArrayList()
        for(i in 0..13){
            books.add(mockVO(i))
        }
        return books
    }

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList()
        for(i in 0..13){
            books.add(mockEntity(i))
        }
        return books
    }
}