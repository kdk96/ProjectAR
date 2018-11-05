package com.kdk96.prizes.domain

data class Prize(
        val id: String,
        val description: String,
        val companyName: String,
        val questTitle: String,
        val prizeImageUrl: String,
        val receivingCode: String
)