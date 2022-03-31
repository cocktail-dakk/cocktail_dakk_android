package com.umcapplunching.cocktail_dakk.ui.settings

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.umcapplunching.cocktail_dakk.databinding.FragmentSettingsBinding
import com.umcapplunching.cocktail_dakk.ui.BaseFragment
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.utils.gso
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.umcapplunching.cocktail_dakk.R


class SettingFragment: BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun initAfterBinding() {
        binding.settingsBackBaseIv.setOnClickListener {
            (activity as MainActivity).DetailBackArrow()
        }

        var str = binding.settingsPersonalPolicyTv.text as String
        str =str.replace("\\\n", System.getProperty("line.separator"))
        binding.settingsPersonalPolicyTv.text = str

        binding.settingsBackPolicyIv.setOnClickListener {
            binding.settingsBaseLy.visibility= View.VISIBLE
            binding.settingsPolicyLy.visibility= View.GONE
        }

        binding.settingsPlot02PersonalPolicyLa.setOnClickListener{
            binding.settingsBaseLy.visibility= View.GONE
            binding.settingsPolicyLy.visibility= View.VISIBLE
        }

        val spf = requireContext().getSharedPreferences("useremail", AppCompatActivity.MODE_PRIVATE)
        binding.settingsPlot01IdFirstTv.text =  spf.getString("useremail","error")
//        binding.settingsPlot03LogoutTv.setOnClickListener {
//            (activity as MainActivity).logout()
//
//        }
        binding.settingsPlot03LogoutTv.setOnClickListener(View.OnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(requireContext())
//            builder.setMessage("로그아웃 하시겠습니까?")
            val view = layoutInflater.inflate(R.layout.custumview_layout,null)
            builder.setView(view)
//            builder.setTitle("종료 알림창")
//                .setCancelable(false)
//                .setPositiveButton("Yes",
//                    DialogInterface.OnClickListener { dialog, i -> (activity as MainActivity).logout()})
//                .setNegativeButton("No",
//                    DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            val alert: android.app.AlertDialog? = builder.create()
//            alert!!.setTitle("종료 알림창")
            alert!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)

            alert!!.show()
            view.findViewById<Button>(R.id.logoutdialogYes_button).setOnClickListener {
                (activity as MainActivity).logout()
            }
            view.findViewById<Button>(R.id.logoutdialogNo_button).setOnClickListener {
                alert.dismiss()
            }
        })

    }
}