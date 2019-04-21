package com.kdk96.quests.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kdk96.glide.GlideApp
import com.kdk96.quests.R
import com.kdk96.quests.domain.entity.QuestShortInfo
import kotlinx.android.synthetic.main.item_quest.view.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class QuestsAdapter(
    private val clickListener: (id: String) -> Unit
) : ListAdapter<QuestShortInfo, QuestsAdapter.ViewHolder>(DiffItemCallback()) {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm, d MMM")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quest, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var quest: QuestShortInfo

        init {
            itemView.setOnClickListener { clickListener(quest.id) }
        }

        fun bind(quest: QuestShortInfo) {
            this.quest = quest
            with(itemView) {
                titleTV.text = quest.title
                companyNameTV.text = quest.companyName
                grandPrizeTV.text = quest.grandPrizeDescription
                val startTime = Instant.ofEpochMilli(quest.startTime)
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.systemDefault())
                startTimeTV.text = startTime.format(timeFormatter)
                GlideApp.with(this).load(quest.companyLogoUrl)
                    .into(companyLogoIV)
            }
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<QuestShortInfo>() {
        override fun areItemsTheSame(oldItem: QuestShortInfo, newItem: QuestShortInfo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: QuestShortInfo, newItem: QuestShortInfo): Boolean =
            oldItem == newItem
    }
}