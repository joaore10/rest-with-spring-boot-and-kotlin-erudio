package br.com.erudio

import br.com.erudio.exceptions.UnsupportedMathOperationException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.sqrt


@RestController
class MathController {

    @RequestMapping(value=["/sum/{numberOne}/{numberTwo}"])
    fun sum(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return convertToDouble(numberOne) + convertToDouble(numberTwo)
    }

    @RequestMapping(value=["/sub/{numberOne}/{numberTwo}"])
    fun sub(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return convertToDouble(numberOne) - convertToDouble(numberTwo)
    }

    @RequestMapping(value=["/mut/{numberOne}/{numberTwo}"])
    fun mut(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return convertToDouble(numberOne) * convertToDouble(numberTwo)
    }

    @RequestMapping(value=["/div/{numberOne}/{numberTwo}"])
    fun div(@PathVariable(value = "numberOne") numberOne: String?,
            @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        return convertToDouble(numberOne) / convertToDouble(numberTwo)
    }

    @RequestMapping(value=["/rquad/{numberOne}"])
    fun rquad(@PathVariable(value = "numberOne") numberOne: String?
    ): Double {
        if (!isNumeric(numberOne)) throw UnsupportedMathOperationException("Por favor, preencha com um valor numérico.")
        if (convertToDouble(numberOne) < 0.0) throw UnsupportedMathOperationException("Numéro não pode ser negativo!")
        return sqrt(convertToDouble(numberOne))
    }

    private fun convertToDouble(strNumber: String?): Double {
        if (strNumber.isNullOrBlank()) return 0.0
        val number = strNumber.replace(",".toRegex(), replacement = ".")
        return if(isNumeric(number)) number.toDouble() else 0.0
    }

    private fun isNumeric(strNumber: String?): Boolean {
        if (strNumber.isNullOrBlank()) return false
        val number = strNumber.replace(",".toRegex(), replacement = ".")
        return number.matches(regex = """[-+]?[0-9]*\.?[0-9]+""".toRegex())
    }
}