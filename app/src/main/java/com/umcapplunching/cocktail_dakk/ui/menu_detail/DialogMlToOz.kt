package com.umcapplunching.cocktail_dakk.ui.menu_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.DialogChangeUserinfoBinding
import com.umcapplunching.cocktail_dakk.databinding.DialogMlToOzBinding

class DialogMlToOz : BottomSheetDialogFragment() {
    lateinit var binding : DialogMlToOzBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_ml_to_oz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<ImageView>(R.id.menu_detail_mltooz_close_iv)?.setOnClickListener {
            dismiss()
        }
    }

}
