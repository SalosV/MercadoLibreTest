package com.santiagolozada.meliapp.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.santiagolozada.domain.models.ProductModel
import com.santiagolozada.meliapp.databinding.ProductItemBinding
import com.santiagolozada.meliapp.utils.loadUrl
import kotlin.properties.Delegates

class SearchItemAdapter(private val onClick: (ProductModel) -> Unit) :
    RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    private lateinit var productItemBinding: ProductItemBinding

    var products: List<ProductModel> by Delegates.observable(
        emptyList()
    ) { _, _, _ ->
        notifyDataSetChanged()
    }

    class ViewHolder(val view: ProductItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        productItemBinding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(productItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = products[position]
        with(holder.view) {
            textViewProductName.text = item.title
            imageViewIcon.loadUrl(item.thumbnail)
        }
        holder.view.root.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = products.size
}