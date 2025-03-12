package com.example.ujian_advance.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ujian_advance.R
import com.example.ujian_advance.data.UiState
import com.example.ujian_advance.data.ViewModelFactory
import com.example.ujian_advance.databinding.ActivityDetailEventsBinding
import kotlinx.coroutines.launch

class DetailEventsActivity : AppCompatActivity() {
    companion object {
        const val EVENT_DETAIL = "event_detail"
    }

    private lateinit var binding: ActivityDetailEventsBinding

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_events)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val eventId = intent.getStringExtra(EVENT_DETAIL) ?: return finish()

        viewModel.fetchEventDetails(eventId)

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is UiState.Success -> {
                        binding.progressBar.isVisible = false
                        state.data?.let { event ->
                            with(binding) {
                                Glide.with(this@DetailEventsActivity)
                                    .load(event.mediaCover)
                                    .into(ivDetail)

                                tvJudul.text = event.name
                                tvDate.text = "📆 ${event.beginTime} - ${event.endTime}"
                                tvCategory.text = event.category
                                tvCity.text = "📍 $event.cityName}"
                                tvPengunjung.text = "👥 ${event.registrants}/${event.quota}"
                                tvDeskripsi.text = HtmlCompat.fromHtml(
                                    event.description ?: "",
                                    HtmlCompat.FROM_HTML_MODE_COMPACT
                                )
                                btnRegister.setOnClickListener {
                                    event.link?.let { url ->
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    }

                    is UiState.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(this@DetailEventsActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.isFavorite.collect { isFavorite ->
                binding.ivFavorite.setImageResource(
                    if (isFavorite) R.drawable.ic_love_change else R.drawable.ic_love
                )
            }
        }
        binding.ivFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }
}