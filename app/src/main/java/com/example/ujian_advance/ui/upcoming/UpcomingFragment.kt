package com.example.ujian_advance.ui.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ujian_advance.EventsViewmodel
import com.example.ujian_advance.data.UiState
import com.example.ujian_advance.data.ViewModelFactory
import com.example.ujian_advance.databinding.FragmentUpcomingBinding
import kotlinx.coroutines.launch

class UpcomingFragment : Fragment() {
    private lateinit var binding: FragmentUpcomingBinding
    private val viewModel: EventsViewmodel by viewModels { ViewModelFactory.getInstance(requireContext()) }
    private lateinit var adapter: UpcomingAdapter
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UpcomingAdapter()
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUpcoming.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect{state ->
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