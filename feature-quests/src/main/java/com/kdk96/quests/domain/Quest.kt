package com.kdk96.quests.domain

data class Quest(
        val id: String,
        val title: String,
        val startTime: Long,
        val companyName: String,
        val companyLogoUrl: String,
        val grandPrizeDescription: String
)