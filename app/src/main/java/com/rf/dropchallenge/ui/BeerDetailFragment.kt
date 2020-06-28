package com.rf.dropchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rf.dropchallenge.R
import com.rf.dropchallenge.databinding.FragmentBeerDetailBinding
import com.rf.dropchallenge.domain.model.Beer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BeerDetailFragment : Fragment() {

    private val sharedViewModel by sharedViewModel<BeerSharedViewModel>()
    private lateinit var binding: FragmentBeerDetailBinding
    private val hopsAdapter = TextAdapter()
    private lateinit var hopsHeaderAdapter: HeaderAdapter
    private val maltsAdapter = TextAdapter()
    private lateinit var maltsHeaderAdapter: HeaderAdapter
    private val methodsAdapter = TextAdapter()
    private lateinit var methodHeaderAdapter: HeaderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        hopsHeaderAdapter = HeaderAdapter(getString(R.string.header_hops))
        maltsHeaderAdapter = HeaderAdapter(getString(R.string.header_malts))
        methodHeaderAdapter = HeaderAdapter(getString(R.string.header_methods))

        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ConcatAdapter(
                hopsHeaderAdapter,
                hopsAdapter,
                maltsHeaderAdapter,
                maltsAdapter,
                methodHeaderAdapter,
                methodsAdapter
            )
        }

        subscribe()

    }

    private fun fillUi(beer: Beer) {
        Glide.with(binding.beerDetailImg)
            .load(beer.imageUrl)
            .into(binding.beerDetailImg)

        binding.beerDetailName.text = getString(R.string.beer_name, beer.name)
        binding.beerDetailAbv.text = getString(R.string.beer_abv, beer.abv)
        binding.beerDetailDescription.text = getString(R.string.beer_description, beer.description)

        hopsAdapter.setItems(beer.hops.map { "${it.name} ${it.amount}" })
        maltsAdapter.setItems(beer.malts.map { "${it.name} ${it.amount}" })
        val methods = emptyList<String>().toMutableList()
        methods.addAll(beer.methods.mashTemp.map {
            getString(
                R.string.beer_mash_temp,
                it.temp.value,
                it.temp.unit,
                it.duration
            )
        }
            .toMutableList())
        methods.add(
            getString(
                R.string.beer_fermentation,
                beer.methods.fermentation.temp.value,
                beer.methods.fermentation.temp.unit
            )
        )
        beer.methods.twist?.run {
            methods.add(getString(R.string.beer_twist, this))
        }
        methodsAdapter.setItems(methods)

    }

    private fun subscribe() {
        sharedViewModel.selectedBeer.observe(viewLifecycleOwner, Observer {
            fillUi(it)
        })
    }
}