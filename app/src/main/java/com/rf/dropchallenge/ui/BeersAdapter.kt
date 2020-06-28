package com.rf.dropchallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rf.dropchallenge.R
import com.rf.dropchallenge.databinding.ListItemBeerBinding
import com.rf.dropchallenge.domain.model.Beer

class BeersAdapter(private val onItemSelectedListener: (Beer) -> Unit) :
    RecyclerView.Adapter<BeersAdapter.BeerViewHolder>() {

    private var beerList: List<Beer> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = ListItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding, onItemSelectedListener)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(beerList[position])
    }

    override fun getItemCount(): Int = beerList.size

    class BeerViewHolder(
        private val binding: ListItemBeerBinding,
        private val listener: (Beer) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var item: Beer

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(beer: Beer) {
            item = beer
            binding.txtName.text = beer.name
            binding.txtAbv.text = binding.root.context.getString(R.string.beer_abv, beer.abv)
            binding.txtType.text = beer.type
            Glide.with(binding.image)
                .load(beer.imageUrl)
                .into(binding.image)
        }

        override fun onClick(v: View?) {
            listener(item)
        }
    }

    fun setItems(beers: List<Beer>) {
        this.beerList = beers
        notifyDataSetChanged()
    }
}
