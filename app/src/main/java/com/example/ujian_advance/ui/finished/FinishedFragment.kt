package com.example.ujian_advance.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ujian_advance.EventsViewmodel
import com.example.ujian_advance.R
import com.example.ujian_advance.data.Result
import com.example.ujian_advance.data.ViewModelFactory
import com.example.ujian_advance.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {
    private lateinit var binding: FragmentFinishedBinding
    private val viewModel: EventsViewmodel by viewModels { ViewModelFactory.getInstance(requireContext()) }
    private lateinit var adapter: FinishedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event->
                Toast.makeText(requireContext(), "Mencari: ${textView.text}", Toast.LENGTH_SHORT).show()
                searchBar.setText(searchView.text)
                searchView.hide()
                true
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FinishedAdapter
    }
}