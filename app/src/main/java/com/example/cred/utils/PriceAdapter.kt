package com.example.cred.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cred.R
import com.example.cred.databinding.PriceItemBinding
import com.example.cred.model.PlanItem

class PriceAdapter(private val context: Context, private val listener: OnPlanItemSelectedListener) :
    RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {
    private var reviews = listOf<PlanItem>()
    fun submitList(newReviews: List<PlanItem>) {
        reviews = newReviews.mapIndexed { index, item ->
            item.copy(isSelected = index == 0)
        }
        notifyItemRangeChanged(0, reviews.size)

        if (reviews.isNotEmpty() && reviews[0].isSelected) {
            listener.onItemSelected(reviews[0].emi, reviews[0].duration)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val binding = PriceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val planItem = reviews[position]
        holder.bind(planItem, position)
        holder.binding.checkbox.isChecked = planItem.isSelected
        val cardColorRes = when (position % 3) {
            0 -> R.color.brownCard
            1 -> R.color.purpleCard
            else -> R.color.blueCard
        }
        holder.binding.cardContainer.setCardBackgroundColor(
            ContextCompat.getColor(context, cardColorRes)
        )
        holder.binding.checkbox.setOnClickListener {
            reviews = reviews.mapIndexed { index, item ->
                item.copy(isSelected = index == position)
            }
            listener.onItemSelected(planItem.emi, planItem.duration)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = reviews.size
    class PriceViewHolder(val binding: PriceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planItem: PlanItem, position: Int) {
            binding.item = planItem
            binding.executePendingBindings()
            binding.recText.visibility = if (position == 1) View.VISIBLE else View.GONE
        }
    }
}