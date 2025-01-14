package br.com.erudio.controllers

import br.com.erudio.converters.NumberConverter
import br.com.erudio.exceptions.UnsupportedMathOperationException
import br.com.erudio.math.SimpleMath
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.sqrt

@RestController
class MathController {

    private val math: SimpleMath = SimpleMath()

    @RequestMapping(value=["/sum/{numberOne}/{numberTwo}"])
    fun sum(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return math.sum(NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo))
    }

    @RequestMapping(value=["/sub/{numberOne}/{numberTwo}"])
    fun sub(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return math.sub(NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo))
    }

    @RequestMapping(value=["/mut/{numberOne}/{numberTwo}"])
    fun mut(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return math.mut(NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo))
    }

    @RequestMapping(value=["/div/{numberOne}/{numberTwo}"])
    fun div(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return math.div(NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo))
    }

    @RequestMapping(value=["/rquad/{numberOne}"])
    fun rquad(@PathVariable(value = "numberOne") numberOne: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        if (NumberConverter.convertToDouble(numberOne) < 0.0) throw UnsupportedMathOperationException("Numéro não pode ser negativo!")
        return math.rquad(NumberConverter.convertToDouble(numberOne))
    }


}