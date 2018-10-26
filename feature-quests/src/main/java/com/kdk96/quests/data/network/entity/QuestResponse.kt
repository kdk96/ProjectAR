package com.kdk96.quests.data.network.entity

import com.google.gson.annotations.SerializedName

data class QuestResponse(
        val id: String,
        val title: String,
        @SerializedName("start_time")
        val startTime: Long,
        @SerializedName("company_id")
        val companyId: String,
        @SerializedName("company_name")
        val companyName: String,
        @SerializedName("company_logo_url")
        val companyLogoUrl: String,
        @SerializedName("grand_prize_id")
        val grandPrizeId: String,
        @SerializedName("grand_prize")
        val grandPrizeDescription: String
)