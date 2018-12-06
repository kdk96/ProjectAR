package com.kdk96.questinfo.domain.entity

data class QuestInfo(
        val id: String,
        val title: String,
        val description: String,
        val startTime: Long,
        val companyId: String,
        val companyName: String,
        val companyLogoUrl: String,
        val startPointLat: Double,
        val startPointLng: Double,
        val prizes: List<Prize>
)