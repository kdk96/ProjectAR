package com.kdk96.auth.domain

enum class FieldName {
    EMAIL, PASSWORD, NAME, PASSWORD_CONFIRMATION
}

abstract class FieldException(val fields: Set<FieldName>) : RuntimeException(
        StringBuilder().apply {
            append("fields: ")
            fields.forEachIndexed { index, fieldName ->
                if (index == fields.size - 1) {
                    append(fieldName.name)
                } else {
                    append("${fieldName.name}, ")
                }
            }
        }.toString()
)

class EmptyFieldException(fields: Set<FieldName>) : FieldException(fields)

class InvalidFieldException(fields: Set<FieldName>) : FieldException(fields)