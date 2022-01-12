//package com.cock_tail.test_xml.ui.main.mrecommand
package com.example.cocktail_dakk.ui.main.mrecommand

import com.example.cocktail_dakk.databinding.FragmentMainrecimgBinding
import com.example.cocktail_dakk.ui.BaseFragment

//import com.cock_tail.test_xml.databinding.FragmentMainrecimgBinding
//import com.cock_tail.test_xml.ui.BaseFragment

class MainrecimgFragment(val imgRes : Int) : BaseFragment<FragmentMainrecimgBinding>(FragmentMainrecimgBinding::inflate) {
    override fun initAfterBinding() {
        binding.mainRecimgIv.setImageResource(imgRes)
    }
}