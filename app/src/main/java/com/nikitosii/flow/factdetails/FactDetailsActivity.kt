package com.nikitosii.flow.factdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.Gson
import com.nikitosii.R
import com.nikitosii.core.database.entity.NumberFact
import com.nikitosii.databinding.ActivityFactDetailsBinding
import com.nikitosii.flow.base.BaseActivity
import com.nikitosii.util.annotation.RequiresViewModel
import com.nikitosii.util.ext.onClick

@RequiresViewModel(FactDetailsViewModel::class)
class FactDetailsActivity :
    BaseActivity<ActivityFactDetailsBinding, FactDetailsViewModel>(R.layout.activity_fact_details) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with (binding) {
            val fact = Gson().fromJson(intent.getStringExtra(FACT_INTENT), NumberFact::class.java)
            this.fact = fact
            btnBack.onClick { onBackPressed() }
        }
    }

    companion object {
        fun start(fact: NumberFact, activity: Activity) {
            val intent = Intent(activity, FactDetailsActivity::class.java)
            intent.putExtra(FACT_INTENT, Gson().toJson(fact))
            startActivity(activity, intent, null)
        }

        private const val FACT_INTENT = "FACT_INTENT"
    }
}