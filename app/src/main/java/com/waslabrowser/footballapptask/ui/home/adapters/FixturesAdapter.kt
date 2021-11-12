package com.waslabrowser.footballapptask.ui.home.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waslabrowser.domain.models.Match
import com.waslabrowser.footballapptask.databinding.ItemGroupDateBinding
import com.waslabrowser.footballapptask.databinding.ItemMatchBinding
import com.waslabrowser.footballapptask.ui.clickListeners.IMatchClickListeners
import com.waslabrowser.footballapptask.ui.home.viewmodels.MatchViewModel

enum class ViewType {
    DATE_HEADER,
    MATCH
}

class FixturesAdapter(val clickListeners: IMatchClickListeners) :
    ListAdapter<Any, RecyclerView.ViewHolder>(DiffUtilCampaignItem()) {

    private class DiffUtilCampaignItem : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Any, newItem: Any) =
            if (oldItem is Match && newItem is Match)
                oldItem.id == newItem.id
            else false
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is String -> ViewType.DATE_HEADER.ordinal
            is Match -> ViewType.MATCH.ordinal
            else -> throw ClassNotFoundException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.DATE_HEADER.ordinal ->
                DateViewHolder(ItemGroupDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ViewType.MATCH.ordinal ->
                 MatchViewHolder(ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassNotFoundException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> {
                holder.binding.tvDate.text = currentList[position].toString()
            }
            is MatchViewHolder -> {
                val match = getItem(position) as Match
                holder.bind(match,position)
            }
        }
    }

    inner class DateViewHolder(val binding: ItemGroupDateBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class MatchViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = MatchViewModel()
        fun bind(match: Match,position: Int) {
            viewModel.bind(match)
            binding.matchVM = viewModel
            binding.ivFav.setOnClickListener {
                match.isFavorite = !match.isFavorite
                notifyItemChanged(position)
                clickListeners.onFavClicked(match)
            }
        }
    }
}