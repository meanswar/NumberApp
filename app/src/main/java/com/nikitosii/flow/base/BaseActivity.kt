package com.nikitosii.flow.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<T : ViewBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutRes: Int
) : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: T
    private var absViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        initContentView()
        setViewModel()
    }

    private fun inject() {
        if (AnnotationUtil.hasViewModel(this)) {
            AndroidInjection.inject(this)
        } else if (AnnotationUtil.hasInject(this)) {
            AndroidInjection.inject(this)
        }
    }

    private fun initContentView() {
        binding = DataBindingUtil.inflate(this.layoutInflater, layoutRes, null, false)
        setContentView(binding.root)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setViewModel() {
        val viewModelClass = AnnotationUtil.findViewModelClass(this)
        absViewModel = ViewModelProviders.of(this, factory).get(viewModelClass) as VM
    }

    fun getViewModel(): VM {
        if (absViewModel == null)
            throw IllegalStateException("ViewModel not found")
        return absViewModel as VM
    }
}