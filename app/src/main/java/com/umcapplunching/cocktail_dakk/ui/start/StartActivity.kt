package com.umcapplunching.cocktail_dakk.ui.start

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.umcapplunching.cocktail_dakk.data.entities.UserInfo
import com.umcapplunching.cocktail_dakk.databinding.ActivityStartBinding
import com.umcapplunching.cocktail_dakk.ui.BaseActivity
import com.umcapplunching.cocktail_dakk.ui.main.MainActivity
import com.umcapplunching.cocktail_dakk.ui.start.Service.*
import com.umcapplunching.cocktail_dakk.ui.start.setting.StartNameActivity
import com.umcapplunching.cocktail_dakk.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import com.umcapplunching.cocktail_dakk.CocktailDakkApplication


class StartActivity : BaseActivity<ActivityStartBinding>(ActivityStartBinding::inflate)
    ,iSFavorokView,TokenSigninView, getUserInfoView {

    val RC_SIGN_IN = 1000
    val userService = UserService()

    override fun initAfterBinding() {
        userService.setiSfavorokViewView(this)
        userService.settokenSigninView(this)
        userService.setUserinfoView(this)
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.startGoogleSignin.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    @SuppressLint("LongLogTag")
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken!!

            CocktailDakkApplication.userImgUrl = completedTask.result.photoUrl!!
            CocktailDakkApplication.userEmail = completedTask.result.email.toString()

            userService.TokenSignin(TokenSigninRequest(idToken))
        } catch (e: ApiException) {
            Log.d("StartActivity : LoginError ",e.toString())
        }
    }

    override fun onFavorLoading() {

    }

    override fun onFavorSuccess(isfavorok: Isfavorok) {
        userService.getUserinfo(CocktailDakkApplication.AccessToken)
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onFavorFailure(code: Int, message: String) {
        if (code == 502) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            startActivityWithClear(StartNameActivity::class.java)
        }
    }

    override fun onTokenSigninSuccess(tokenSigninbody: Tokenrespbody) {
        CocktailDakkApplication.AccessToken = tokenSigninbody.token
        CocktailDakkApplication.RefreshToken = tokenSigninbody.refreshToken
        setrefreshtoken(this, tokenSigninbody.refreshToken)
        userService.isfavorok(CocktailDakkApplication.AccessToken)
    }

    override fun onTokenSigninFailure(code: Int, message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetUinfoSuccess(userinfo: Userinfo) {
        Log.d("Set_UserInfo",userinfo.toString())
        initUser(userinfo)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onGetUinfoFailure(code: Int, message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
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

        val userinfo = UserInfo(
            userinfo.age, userinfo.alcoholLevel,
            userinfo.nickname, userinfo.sex, gijulist, keywrodlist
        )
        CocktailDakkApplication.userInfo = userinfo
    }


}