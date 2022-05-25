package com.umcapplunching.cocktail_dakk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragmentByDataBinding<T : ViewDataBinding>(
    @LayoutRes val layoutId : Int
): Fragment() {
    lateinit var binding : T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        initViewModel()
        initView()
        initListener()
        afterViewCreated()
    }

    protected open fun afterViewCreated(){}

    protected open fun initListener(){}

    protected open fun initViewModel(){}

    protected open fun initView(){}

    protected fun showToast(msg: String) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}