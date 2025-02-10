package br.com.erudio.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectsIsNullException: RuntimeException {
    constructor(): super("Não é permitido persistir um objeto nulo")
    constructor(exception: String?): super(exception)
}