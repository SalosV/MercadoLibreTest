package com.santiagolozada.meliapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.santiagolozada.domain.models.DetailProductModel
import com.santiagolozada.meliapp.R
import com.santiagolozada.meliapp.databinding.FragmentDetailProductBinding
import com.santiagolozada.meliapp.utils.ImageCarouselAdapter
import com.santiagolozada.meliapp.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private lateinit var binding: FragmentDetailProductBinding

    private val viewModel by viewModels<DetailProductViewModel>()

    private lateinit var picturesAdapter: ImageCarouselAdapter

    private val args: DetailProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        setupToolbar()
        initPicturesList()
        initListener()

        viewModel.fetchProduct(args.idProduct)
        initObservables()

        return binding.root
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title = getString(R.string.text_product_detail)
    }

    private fun initPicturesList() {
        picturesAdapter = ImageCarouselAdapter()
        binding.recyclerViewPictures.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPictures.adapter = picturesAdapter
    }

    private fun initObservables() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            if (uiState.flow == DETAIL_PRODUCT) {
                                val products = uiState.data as DetailProductModel
                                showProductInformation(products)
                            }
                        }

                        is UiState.Error -> {
                            Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingState.collect {
                    binding.loading.isVisible = it
                }
            }
        }
    }

    private fun initListener() {
        binding.buttonPurchase.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.button_purchase),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showProductInformation(productModel: DetailProductModel) {
        with(productModel) {
            binding.textViewTitle.text = productModel.title
            binding.textViewPrice.text = productModel.price
            picturesAdapter.imageUrls = pictures
        }
    }

}