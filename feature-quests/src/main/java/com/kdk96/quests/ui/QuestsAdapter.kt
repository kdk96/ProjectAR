package com.kdk96.quests.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kdk96.glide.GlideApp
import com.kdk96.quests.R
import com.kdk96.quests.domain.Quest
import kotlinx.android.synthetic.main.item_quest.view.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class QuestsAdapter : ListAdapter<Quest, QuestsAdapter.ViewHolder>(DiffItemCallback()) {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm, d MMM")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quest, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(quest: Quest) {
            itemView.titleTV.text = quest.title
            itemView.companyNameTV.text = quest.companyName
            itemView.grandPrizeTV.text = quest.grandPrizeDescription
            Date(quest.startTime)
            val startTime = Instant.ofEpochMilli(quest.startTime)
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.systemDefault())
            itemView.startTimeTV.text = startTime.format(timeFormatter)
            GlideApp.with(itemView).load(quest.companyLogoUrl)
                    .into(itemView.companyLogoIV)
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<Quest>() {
        override fun areItemsTheSame(oldItem: Quest, newItem: Quest): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Quest, newItem: Quest): Boolean =
                oldItem == newItem
    }
}