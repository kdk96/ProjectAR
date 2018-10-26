package com.kdk96.quests.domain.entity

data class QuestShortInfo(
        val id: String,
        val title: String,
        val startTime: Long,
        val companyName: String,
        val companyLogoUrl: String,
        val grandPrizeDescription: String
)