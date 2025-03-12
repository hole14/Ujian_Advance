package com.example.ujian_advance.ui.favorite

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

class FavoriteAdapter(private val onItemClick: (EventEntity) -> Unit) :
    ListAdapter<EventEntity, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemFinishedBinding) : RecyclerView.ViewHolder(binding.root) {
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

            itemView.setOnClickListener {onItemClick(event)}
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}