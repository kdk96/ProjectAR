package com.kdk96.prizes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kdk96.glide.GlideApp
import com.kdk96.prizes.R
import com.kdk96.prizes.domain.Prize
import kotlinx.android.synthetic.main.item_prize.view.*

class PrizesAdapter : ListAdapter<Prize, PrizesAdapter.ViewHolder>(DiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prize, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(prize: Prize) {
            itemView.prizeDescriptionTV.text = prize.description
            itemView.companyNameTV.text = prize.companyName
            val questPrefix = itemView.context.getString(R.string.quest)
            val questTitle = "$questPrefix: ${prize.questTitle}"
            itemView.questTitleTV.text = questTitle
            val receivingCodePrefix = itemView.context.getString(R.string.receiving_code)
            val receivingCode = "$receivingCodePrefix: ${prize.receivingCode}"
            itemView.receivingCodeTV.text = receivingCode
            GlideApp.with(itemView).load(prize.prizeImageUrl)
                .into(itemView.prizeImageIV)
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<Prize>() {
        override fun areItemsTheSame(oldItem: Prize, newItem: Prize) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Prize, newItem: Prize) =
            oldItem == newItem
    }
}