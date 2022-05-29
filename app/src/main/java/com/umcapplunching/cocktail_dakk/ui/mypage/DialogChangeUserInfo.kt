package com.umcapplunching.cocktail_dakk.ui.mypage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication

import com.umcapplunching.cocktail_dakk.data.entities.UserInfo
import com.umcapplunching.cocktail_dakk.databinding.DialogChangeUserinfoBinding

class DialogChangeUserInfo(
    context: Context, val setUser: (UserInfo) -> Unit,
    val fragment: Fragment
) : Dialog(context) {

    private var flag : Boolean = true
    private lateinit var binding: DialogChangeUserinfoBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: MypageViewpagerAdapter
    private val information = arrayListOf("    주량    ", "    기주    ", "  키워드  ")
    private lateinit var tempUserInfo : UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogChangeUserinfoBinding.inflate(layoutInflater)
        tempUserInfo = CocktailDakkApplication.userInfo
        setContentView(binding.root)
        initViews()
        initListener()
    }

    private fun initListener() {
        binding.mypageResettingWhiteboardLa.setOnClickListener {
            // 아무것도 안함. 배경 클릭과의 대비를 두기 위한 코드. 지우지 말것!
        }
        binding.mypageResettingBackgroundLa.setOnClickListener {
            dismiss()
        }
        binding.mypageResettingExitIv.setOnClickListener {
            dismiss()
        }
    }

    private fun initViews() = with(binding) {
        // background를 투명하게 만듦
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        adapter = MypageViewpagerAdapter(fragment,setUser={
            if(it.userDrinks.isEmpty() or it.userKeywords.isEmpty()){
                Toast.makeText(context,"기주, 키워드는 1개~5개를 선택해야 합니다.",Toast.LENGTH_SHORT).show()
                flag = false
            }else{
                flag = true
                tempUserInfo = it
            }
        })

        viewPager = binding.mypageResettingViewpagerVp
        viewPager.adapter = adapter

        TabLayoutMediator(
            binding.mypageResettingTablayoutTl,
            binding.mypageResettingViewpagerVp
        ) { tab, position ->
            tab.text = information[position]
        }.attach()

        binding.mypageResettingOkOnTv.setOnClickListener {
            if(flag){
                val tempGijuSize : Int = (tempUserInfo.userDrinks.split(",") as ArrayList<String>).size
                val tempKeywordSize : Int = (tempUserInfo.userKeywords.split(",") as ArrayList<String>).size
                if ((tempGijuSize >= 1) and (tempGijuSize <= 5) and (tempKeywordSize >= 1) and (tempKeywordSize <= 5 )){
                    val userInfo = UserInfo(
                        CocktailDakkApplication.userInfo.age,
                        tempUserInfo.alcoholLevel,
                        CocktailDakkApplication.userInfo.nickname,
                        CocktailDakkApplication.userInfo.sex,
                        tempUserInfo.userDrinks,
                        tempUserInfo.userKeywords
                    )
                    CocktailDakkApplication.userInfo = userInfo
                    setUser(tempUserInfo)
                    dismiss()
                }else{
                    Toast.makeText(context,"기주, 키워드는 1개~5개를 선택해야 합니다.",Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(context,"기주, 키워드는 1개~5개를 선택해야 합니다.",Toast.LENGTH_SHORT).show()
            }
        }

    }

}