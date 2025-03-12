package com.example.ujian_advance.ui.upcoming

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ujian_advance.R
import com.example.ujian_advance.data.entity.EventEntity
import com.example.ujian_advance.data.respone.ListEventsItem
import com.example.ujian_advance.databinding.ItemUpcomingBinding
import com.example.ujian_advance.ui.detail.DetailEventsActivity

class UpcomingAdapter(private val onItemClick: (EventEntity) -> Unit): ListAdapter<ListEventsItem, UpcomingAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(event: ListEventsItem) {
            binding.tvJudul.text = event.name
            binding.tvDate.text = "📆 ${event.beginTime} - ${event.endTime}"
            binding.tvCity.text = "📍 ${event.cityName}"
            binding.tvPengunjung.text = "👥 ${event.registrants}/${event.quota}"
            binding.tvCategory.text = event.category
            binding.tvSummary.text = event.summary
            Glide.with(itemView.context)
                .load(event.mediaCover)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imageView)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailEventsActivity::class.java)
                intent.putExtra(DetailEventsActivity.EVENT_DETAIL, event.id)
                itemView.context.startActivity(intent)
            }
        }
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}