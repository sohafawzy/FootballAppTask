package com.waslabrowser.footballapptask.ui.home.views

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import com.waslabrowser.domain.models.Match
import com.waslabrowser.footballapptask.R
import com.waslabrowser.footballapptask.databinding.FragmentFixturesBinding
import com.waslabrowser.footballapptask.models.Error
import com.waslabrowser.footballapptask.ui.base.BaseFragment
import com.waslabrowser.footballapptask.ui.clickListeners.IMatchClickListeners
import com.waslabrowser.footballapptask.ui.home.adapters.FixturesAdapter
import com.waslabrowser.footballapptask.ui.home.viewmodels.FixturesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FixturesFragment:BaseFragment<FragmentFixturesBinding>(R.layout.fragment_fixtures), IMatchClickListeners{
    @Inject lateinit var fixturesViewModel: FixturesViewModel
    private val fixturesAdapter by lazy { FixturesAdapter(this) }
    private val skeleton by lazy { binding.rvMatches.applySkeleton(R.layout.item_match,10) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMatches.adapter= fixturesAdapter
        fixturesViewModel.fixturesViewState.observe(viewLifecycleOwner,{
            loading(it.isLoading)
            setupRecycler(it.matches)
            handleError(it.error)
        })
        fixturesViewModel.getMatches()
    }

    private fun loading(isLoading:Boolean){
        if (isLoading) skeleton.showSkeleton()
        else skeleton.showOriginal()
    }

    private fun setupRecycler(matches:List<Any>?){
        if (!matches.isNullOrEmpty()){
            fixturesAdapter.submitList(matches)
            binding.rvMatches.scrollToPosition(fixturesViewModel.scrollToPosition)
        }
    }

    private fun handleError(error:Error?){
        error?.let {
            Snackbar.make(binding.rvMatches,it.message,Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onFavClicked(match: Match) {
        fixturesViewModel.favMatch(match)
    }


}