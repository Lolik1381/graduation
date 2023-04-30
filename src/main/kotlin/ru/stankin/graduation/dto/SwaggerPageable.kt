package ru.stankin.graduation.dto

import io.swagger.annotations.ApiModelProperty

class SwaggerPageable(

    @ApiModelProperty(name = "page", dataType = "integer", value = "Порядковый номер страницы (0..N)", example = "0")
    val page: Int?,

    @ApiModelProperty(name = "size", dataType = "integer", value = "Количество элементов на странице", example = "20")
    val size: Int?,

    @ApiModelProperty(
        name = "sort",
        dataType = "array",
        value = """
            Порядок сортировки в формате: property(,asc|desc).
            По умолчанию сортировка - asc. 
            Возможно многократное указание параметра. 
            Пример: `?sort=id,asc&sort=name,desc&sort=description`
        """
    )
    var sort: Array<String>?
)