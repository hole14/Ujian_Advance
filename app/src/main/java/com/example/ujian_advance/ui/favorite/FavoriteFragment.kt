package com.example.ujian_advance.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ujian_advance.R
import com.example.ujian_advance.data.UiState
import com.example.ujian_advance.data.ViewModelFactory
import com.example.ujian_advance.databinding.FragmentFavoriteBinding
import com.example.ujian_advance.ui.detail.DetailEventsActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var adapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter =FavoriteAdapter{ event ->
            pindahDetail(event.id)
        }
        binding.rvEvents.adapter = adapter
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect() {state ->
                    when(state){
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                        UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.submitList(state.data)
                        }
                    }
                }
            }
        }
    }

    private fun pindahDetail(event: String) {
        val intent = Intent(requireContext(), DetailEventsActivity::class.java).apply {
            putExtra(DetailEventsActivity.EVENT_DETAIL, event)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}