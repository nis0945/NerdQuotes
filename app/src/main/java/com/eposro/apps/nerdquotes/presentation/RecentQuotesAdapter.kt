package com.eposro.apps.nerdquotes.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eposro.apps.nerdquotes.R
import com.eposro.apps.nerdquotes.databinding.RecentQuotesItemBinding
import com.eposro.apps.nerdquotes.domain.entities.ProgrammingQuote

class RecentQuotesAdapter : RecyclerView.Adapter<RecentQuotesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RecentQuotesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecentQuotesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private var list = listOf<ProgrammingQuote>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<ProgrammingQuote>) {
        list = newList

        /*
            DO NOT use DiffUtil because it has issues updating the view when new
            data is passed.
         */
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curItem = list[position]
        holder.binding.apply {
            "${curItem.en}\n${
                root.context.getString(
                    R.string.author_with_name,
                    curItem.author
                )
            }".also { tvRecentQuote.text = it }
        }
    }

    override fun getItemCount(): Int = list.size
}