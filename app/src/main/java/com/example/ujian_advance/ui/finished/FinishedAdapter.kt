package com.example.ujian_advance.ui.finished

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ujian_advance.R
import com.example.ujian_advance.data.entity.EventEntity
import com.example.ujian_advance.databinding.ItemFinishedBinding

class FinishedAdapter(private val onItemClick: (EventEntity) -> Unit): ListAdapter<EventEntity, FinishedAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(val binding: ItemFinishedBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(event: EventEntity) {
            binding.tvJudul.text = event.name
            binding.tvDate.text = "📆 ${event.beginTime} - ${event.endTime}"
            binding.tvCity.text = "📍 ${event.cityName}"
            binding.tvPengunjung.text = "👥 ${event.registrants}/${event.quota}"
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.ivFinished)

            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<EventEntity> =
            object : DiffUtil.ItemCallback<EventEntity>() {
                override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}