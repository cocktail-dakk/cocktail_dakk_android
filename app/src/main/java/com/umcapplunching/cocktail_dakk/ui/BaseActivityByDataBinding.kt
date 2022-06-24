package com.umcapplunching.cocktail_dakk.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivityByDataBinding<T: ViewDataBinding>
    (@LayoutRes private val layoutId: Int): AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeSetContentView()

        // 필요에 따라 OnCreate, OnStart에
        // Log를 붙여 생명주기를 관찰할 수도 있음
        //Log.d(localClassName,"OnCreate")

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        initView()
        initViewModel()
        initListener()
    }

    protected open fun beforeSetContentView() {}
    protected open fun initView() {}
    protected open fun initViewModel() {}
    protected open fun initListener() {}

}