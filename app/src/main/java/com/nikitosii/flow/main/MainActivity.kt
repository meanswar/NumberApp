package com.nikitosii.flow.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.nikitosii.R
import com.nikitosii.core.base.channel.Status
import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.databinding.ActivityMainBinding
import com.nikitosii.domain.base.WorkResult
import com.nikitosii.flow.base.BaseActivity
import com.nikitosii.flow.factdetails.FactDetailsActivity
import com.nikitosii.util.annotation.RequiresViewModel
import com.nikitosii.util.ext.hide
import com.nikitosii.util.ext.onClick
import com.nikitosii.util.ext.show
import timber.log.Timber

@RequiresViewModel(MainViewModel::class)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    private val adapter by lazy { FactsAdapter(actionOnFactClick) }
    private val actionOnFactClick: (NumberFact) -> Unit = {
        FactDetailsActivity.start(it, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        subscribe()
    }

    private fun initViews() {
        with(binding) {
            viewModel = this@MainActivity.getViewModel()
            btnFact.onClick { this@MainActivity.getViewModel().getNewFact() }
            btnRandomFact.onClick { this@MainActivity.getViewModel().getRandomFact() }
            rvFactHistory.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        getViewModel().getFacts()
    }

    private fun subscribe() {
        with(getViewModel()) {
            facts.observe(this@MainActivity, factsObserver)
            fact.observe(this@MainActivity, factObserver)
        }
    }

    private val factsObserver: Observer<WorkResult<List<NumberFact>>> = Observer {
        when (it.status) {
            WorkResult.Status.ERROR -> {
                Timber.e(it.exception)
                hideLoader()
            }
            WorkResult.Status.LOADING -> {
                showLoader()
            }
            WorkResult.Status.SUCCESS -> {
                adapter.submitList(it.data)
                hideLoader()
            }
        }
    }

    private val factObserver: Observer<WorkResult<NumberFact>> = Observer {
        when (it.status) {
            WorkResult.Status.ERROR -> {
                Timber.e(it.exception)
                hideLoader()
            }
            WorkResult.Status.LOADING -> {
                showLoader()
            }
            WorkResult.Status.SUCCESS -> {
                hideLoader()
                getViewModel().insertFact()
                it.data?.let { actionOnFactClick(it) }
            }
        }
    }

    private fun showLoader() {
        with(binding) {
            clContent.alpha = 0.8f
            clContent.isEnabled = false
            flLoader.show()
        }
    }

    private fun hideLoader() {
        with(binding) {
            clContent.alpha = 1f
            clContent.isEnabled = true
            flLoader.hide()
        }
    }
}