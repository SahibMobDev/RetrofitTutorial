package com.example.retrofittutorial.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofittutorial.R
import com.example.retrofittutorial.databinding.ProductItemBinding
import com.example.retrofittutorial.retrofit.Product

class ProductAdapter : ListAdapter<Product, ProductAdapter.Holder>(Comparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ProductItemBinding.bind(view)
        fun bind(product: Product) = with(binding) {
            title.text = product.title
            description.text = product.description
        }
    }

    class Comparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

}