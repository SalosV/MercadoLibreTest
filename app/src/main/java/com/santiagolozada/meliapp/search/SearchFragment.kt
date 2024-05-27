package com.santiagolozada.meliapp.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.santiagolozada.domain.models.ProductModel
import com.santiagolozada.meliapp.R
import com.santiagolozada.meliapp.databinding.FragmentSearchBinding
import com.santiagolozada.meliapp.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var searchAdapter: SearchItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupProductsListView()

        initListeners()
        initObservables()

        return binding.root
    }

    private fun setupProductsListView() {
        searchAdapter = SearchItemAdapter(::handleProductSelect)
        binding.recyclerViewProducts.adapter = searchAdapter
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun initObservables() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            if (uiState.flow == SEARCH_PRODUCTS) {
                                val products = uiState.data as List<ProductModel>
                                binding.imageViewEmptyState.isVisible = products.isEmpty()
                                searchAdapter.products = products
                            }
                        }

                        is UiState.Error -> {
                            Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingState.collectLatest {
                    binding.loading.isVisible = it
                }
            }
        }
    }

    private fun handleProductSelect(product: ProductModel) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToDetailProductFragment(product.id)
        findNavController().navigate(action)
    }
}