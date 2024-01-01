package com.nikitosii.di.util

import android.content.Context
import com.nikitosii.di.AppComponent
import com.nikitosii.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class NumberApp : DaggerApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val appInjector: AppComponent =
        DaggerAppComponent.builder()
            .application(this)
            .build()


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        INSTANCE = this
        appInjector.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        appInjector

    companion object {
        private var INSTANCE: NumberApp? = null
        @JvmStatic
        fun get(): NumberApp = INSTANCE!!

        @JvmStatic
        fun getAppComponent(): AppComponent {
            return get().appInjector
        }
    }
}