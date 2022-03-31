package com.umcapplunching.cocktail_dakk.ui.start.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo
import com.umcapplunching.cocktail_dakk.databinding.ActivitySplashBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.start.Service.*
import com.umcapplunching.cocktail_dakk.ui.start.StartActivity
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartNameActivity
import com.umcapplunching.cocktail_dakk.utils.*
import com.google.gson.Gson
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback


class SplashActivity : AppCompatActivity(), iSFavorokView, getUserInfoView, TokenResfreshView,
    GoogleApiClient.OnConnectionFailedListener {
    lateinit var binding: ActivitySplashBinding
//    val RC_SIGN_IN = 1000
    val userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            userService.setiSfavorokViewView(this)
            userService.setUserinfoView(this)
            userService.settokenRefreshView(this)
            //자동로그인 체크
            val mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this,this
                )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

            val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
            if (opr.isDone) {
                val result = opr.get()
                handleSignInResult(result) // result.getSignInAccount().getIdToken(), etc.
            } else {
                opr.setResultCallback { p0 ->
                    handleSignInResult(p0) // result.getSignInAccount().getIdToken(), etc.
                }
            }

        }, 1000)

//            var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//            mGoogleSignInClient.silentSignIn().addOnCompleteListener(object :
//                OnCompleteListener<GoogleSignInAccount> {
//                override fun onComplete(p0: Task<GoogleSignInAccount>) {
//                    handleSignInResult(p0)
//                }
//            })
//            val account = GoogleSignIn.getLastSignedInAccount(this)
//            updateUI(account)
    }

    fun handleSignInResult(completedTask: GoogleSignInResult) {
        if (completedTask.signInAccount != null) {
            // 프로필 이미지 가져오기
            var spf = getSharedPreferences("profileimg", MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
            editor.putString("profileimg", completedTask.signInAccount!!.photoUrl.toString())
            editor.apply()

            spf = getSharedPreferences("useremail", MODE_PRIVATE)
            val editor2: SharedPreferences.Editor = spf?.edit()!!
            editor2.putString("useremail", completedTask.signInAccount!!.email.toString())
            editor2.apply()

            userService.TokenRefresh(getrefreshtoken(this))

            Log.d("idtoken", completedTask.signInAccount!!.idToken.toString())
            Log.d("refreshtoken_splash", getrefreshtoken(this))
        } else {
            val intent = Intent(this, StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
////            updateUI(account)
//        } catch (e: ApiException) {
////            updateUI(null)
//        }
//    }

//    fun updateUI(account: GoogleSignInAccount?) {
//        if (account != null) {
////            Log.d("idtoken",account.idToken.toString())
////            userService.isfavorok(getjwt(this))
//        } else {
//            val intent = Intent(this, StartActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
//    }


    private fun initUser(userinfo: Userinfo) {
        var gijulist = ""
        for (i in userinfo.userDrinks) {
            gijulist += i.drinkName + ","
        }
        var keywrodlist = ""
        for (i in userinfo.userKeywords) {
            keywrodlist += i.keywordName + ","
        }

        val userinfo = UserInfo(
            userinfo.age, userinfo.alcoholLevel,
            userinfo.nickname, userinfo.sex, gijulist, keywrodlist
        )
        val gson = Gson()
        val spf = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putString("UserInfo", gson.toJson(userinfo))
        editor.apply()
    }

    override fun onFavorLoading() {
    }

    override fun onFavorSuccess(isfavorok: Isfavorok) {
        userService.getUserinfo(getaccesstoken(this))
    }

    override fun onFavorFailure(code: Int, message: String) {
        //구글로그인은 되있으나 취향설정을 하지 않았을 때
        val intent = Intent(this, StartNameActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onGetUinfoLoading() {
    }

    override fun onGetUinfoSuccess(userinfo: Userinfo) {
        initUser(userinfo = userinfo)
        initSplash(context = this)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onGetUinfoFailure(code: Int, message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }


    override fun onTokenRefreshLoading() {

    }

    override fun onTokenRefreshSuccess(tokenresult: Tokenrespbody) {
        setaccesstoken(this,tokenresult.token)
        setrefreshtoken(this,tokenresult.refreshToken)
        Log.d("refreshtoken_splash", getrefreshtoken(this))
        userService.isfavorok(getaccesstoken(this))
    }

    override fun onTokenRefreshFailure(code: Int, message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

}