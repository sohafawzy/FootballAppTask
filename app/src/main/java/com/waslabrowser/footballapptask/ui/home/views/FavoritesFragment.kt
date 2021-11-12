package com.waslabrowser.footballapptask.ui.home.views

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.waslabrowser.domain.models.Match
import com.waslabrowser.footballapptask.R
import com.waslabrowser.footballapptask.databinding.FragmentFavoritesBinding
import com.waslabrowser.footballapptask.models.Error
import com.waslabrowser.footballapptask.ui.base.BaseFragment
import com.waslabrowser.footballapptask.ui.clickListeners.IMatchClickListeners
import com.waslabrowser.footballapptask.ui.home.adapters.FixturesAdapter
import com.waslabrowser.footballapptask.ui.home.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites),IMatchClickListeners {
    @Inject
    lateinit var favoritesViewModel: FavoritesViewModel
    private val favsAdapter by lazy { FixturesAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFav.adapter= favsAdapter
        favoritesViewModel.fixturesViewState.observe(viewLifecycleOwner,{
            setupRecycler(it.matches)
            handleError(it.error)
        })
        favoritesViewModel.getMatches()
    }


    private fun setupRecycler(matches:List<Any>?){
        if (!matches.isNullOrEmpty()){
            favsAdapter.submitList(matches)
        }
    }

    private fun handleError(error: Error?){
        error?.let {
            Snackbar.make(binding.rvFav,it.message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onFavClicked(match: Match) {
        favoritesViewModel.favMatch(match)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            favoritesViewModel.getMatches()
        }
    }
}