package com.kdk96.auth.domain

enum class FieldName {
    EMAIL, PASSWORD, NAME, PASSWORD_CONFIRMATION
}

abstract class FieldException(val fields: Set<FieldName>) : RuntimeException(
        StringBuilder().apply {
            append("fields: ")
            for (field in fields) {
                append("${field.name}, ")
            }
            delete(length - 2, length)
        }.toString()
)

class EmptyFieldException(fields: Set<FieldName>) : FieldException(fields)

class InvalidFieldException(fields: Set<FieldName>) : FieldException(fields)