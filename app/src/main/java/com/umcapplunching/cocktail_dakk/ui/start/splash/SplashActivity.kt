package com.umcapplunching.cocktail_dakk.ui.start.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo
import com.umcapplunching.cocktail_dakk.databinding.ActivitySplashBinding
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.start.Service.*
import com.umcapplunching.cocktail_dakk.ui.start.StartActivity
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartNameActivity
import com.umcapplunching.cocktail_dakk.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import java.util.*
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.OptionalPendingResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback


class SplashActivity : AppCompatActivity(), iSFavorokView, getUserInfoView, TokenResfreshView,
    GoogleApiClient.OnConnectionFailedListener {
    lateinit var binding: ActivitySplashBinding
    val RC_SIGN_IN = 1000
    val userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            userService.setiSfavorokViewView(this)
            userService.setUserinfoView(this)
            userService.settokenRefreshView(this)
            var mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            //자동로그인 체크
//            mGoogleSignInClient.silentSignIn().addOnCompleteListener(object :
//                OnCompleteListener<GoogleSignInAccount> {
//                override fun onComplete(p0: Task<GoogleSignInAccount>) {
//                    handleSignInResult(p0)
//                }
//            })
//            val account = GoogleSignIn.getLastSignedInAccount(this)
//            updateUI(account)

            var mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this,this
                )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

            val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
            if (opr.isDone) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                val result = opr.get()
                handleSignInResult(result) // result.getSignInAccount().getIdToken(), etc.
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                opr.setResultCallback(object : ResultCallback<GoogleSignInResult?> {
                    override fun onResult(p0: GoogleSignInResult) {
                        handleSignInResult(p0) // result.getSignInAccount().getIdToken(), etc.
                    }
                })
            }

            //여기 나중에 바꾸기
//            var spf = getSharedPreferences("jwt", MODE_PRIVATE)
//            var editor: SharedPreferences.Editor = spf?.edit()!!
//            editor.putString("jwt"," ")
//            editor.apply()

        }, 1000)
    }

    fun handleSignInResult(completedTask: GoogleSignInResult) {
        if (completedTask.signInAccount != null) {
            Log.d("idtoken", completedTask.signInAccount!!.idToken.toString())
            // 프로필 이미지 가져오기.
            var spf = getSharedPreferences("profileimg", MODE_PRIVATE)
            var editor: SharedPreferences.Editor = spf?.edit()!!
//            Log.d("photourl",completedTask.signInAccount!!.photoUrl.toString())
            editor.putString("profileimg", completedTask.signInAccount!!.photoUrl.toString())
            editor.apply()
            Log.d("refreshtoken_splash", getrefreshtoken(this))
            userService.TokenRefresh(getrefreshtoken(this))
        } else {
            val intent = Intent(this, StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d("idtoken",account.idToken.toString())
            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            updateUI(null)
        }
    }

    fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            Log.d("idtoken",account.idToken.toString())
//            userService.isfavorok(getjwt(this))
        } else {
            val intent = Intent(this, StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


    private fun initUser(userinfo: Userinfo) {
        var gijulist = ""
        for (i in userinfo.userDrinks) {
            gijulist += i.drinkName + ","
        }
        var keywrodlist = ""
        for (i in userinfo.userKeywords) {
            keywrodlist += i.keywordName + ","
        }

        var userinfo = UserInfo(
            userinfo.age, userinfo.alcoholLevel,
            userinfo.nickname, userinfo.sex, gijulist, keywrodlist
        )
        val gson = Gson()
        var spf = getSharedPreferences("UserInfo", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = spf?.edit()!!
        editor.putString("UserInfo", gson.toJson(userinfo))
        editor.apply()
    }

    override fun onFavorLoading() {

    }

    override fun onFavorSuccess(isfavorok: Isfavorok) {
        Log.d("onFavorSucess",isfavorok.toString())
        userService.getUserinfo(getaccesstoken(this))
    }

    override fun onFavorFailure(code: Int, message: String) {
        val intent = Intent(this, StartNameActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onGetUinfoLoading() {
    }

    override fun onGetUinfoSuccess(userinfo: Userinfo) {
        Log.d("Set_UserInfo",userinfo.toString())
        initUser(userinfo)
        initSplash(this)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onGetUinfoFailure(code: Int, message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

    }


    override fun onTokenRefreshLoading() {

    }

    override fun onTokenRefreshSuccess(tokenbody: Tokenrespbody) {
        setaccesstoken(this,tokenbody.token)
        setrefreshtoken(this,tokenbody.refreshToken)
        Log.d("refreshtoken_splas2h", getrefreshtoken(this))
        userService.isfavorok(getaccesstoken(this))
    }

    override fun onTokenRefreshFailure(code: Int, message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, StartActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
    }

}