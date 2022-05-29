package com.umcapplunching.cocktail_dakk.ui.settings

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.utils.gso
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.common.ConnectionResult
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication
import com.umcapplunching.cocktail_dakk.R
import com.umcapplunching.cocktail_dakk.databinding.ActivitySettingsBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivityByDataBinding
import com.umcapplunching.cocktail_dakk.ui.start.splash.SplashActivity


class SettingActivity: BaseActivityByDataBinding<ActivitySettingsBinding>(R.layout.activity_settings),
    GoogleApiClient.OnConnectionFailedListener {
    lateinit var mGoogleApiClient : GoogleApiClient

    override fun initView() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(
                this, this
            )
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
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

        binding.settingsPlot01IdFirstTv.text =  CocktailDakkApplication.userEmail

        binding.settingsPlot03LogoutTv.setOnClickListener {
            googleLogout()
        }

        binding.settingsBackBaseIv.setOnClickListener {
            finish()
        }

        binding.settingsPlot03LogoutTv.setOnClickListener(View.OnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
//            builder.setMessage("로그아웃 하시겠습니까?")
            val view = layoutInflater.inflate(R.layout.custumview_layout,null)
            builder.setView(view)
//            builder.setTitle("종료 알림창")
                .setCancelable(false)
                .setPositiveButton("",
                    DialogInterface.OnClickListener { dialog, i -> googleLogout()})
                .setNegativeButton("",
                    DialogInterface.OnClickListener { dialog, i -> dialog.cancel() })
            val alert: android.app.AlertDialog? = builder.create()
//            alert!!.setTitle("종료 알림창")
            alert!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE)

            alert!!.show()
            view.findViewById<Button>(R.id.logoutdialogYes_button).setOnClickListener {
                googleLogout()
            }
            view.findViewById<Button>(R.id.logoutdialogNo_button).setOnClickListener {
                alert.dismiss()
            }
        })

    }

    private fun googleLogout() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("SettingActivity",p0.toString())
        Toast.makeText(this,"인터넷 연결에 실패했습니다.",Toast.LENGTH_SHORT).show()
    }


}