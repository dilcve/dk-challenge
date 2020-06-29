package com.rf.dropchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rf.dropchallenge.R
import com.rf.dropchallenge.databinding.FragmentBeersBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeersFragment : Fragment() {

    private val viewModel by viewModel<BeersViewModel>()
    private val sharedViewModel by sharedViewModel<BeerSharedViewModel>()
    private lateinit var binding: FragmentBeersBinding
    private lateinit var beersAdapter: BeersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeersBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFileAndGetSolution()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        beersAdapter = BeersAdapter {
            sharedViewModel.selectedBeer.value = it
            findNavController().navigate(R.id.action_beersFragment_to_beerDetailFragment)
        }

        binding.beerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = beersAdapter
        }

        subscribe()

    }

    private fun subscribe() {
        viewModel.beers.observe(viewLifecycleOwner, Observer {
            beersAdapter.setItems(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                setAction("Ok") { this.dismiss() }
                    .show()
            }

        })
    }

}