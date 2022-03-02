package com.umcapplunching.cocktail_dakk.ui.start

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.gson.Gson


class StartActivity : BaseActivity<ActivityStartBinding>(ActivityStartBinding::inflate),iSFavorokView,TokenSigninView, getUserInfoView {

    val RC_SIGN_IN = 1000
    val userService = UserService()

    override fun initAfterBinding() {

        userService.setiSfavorokViewView(this)
        userService.settokenSigninView(this)
        userService.setUserinfoView(this)
        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //자동로그인 체크
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        updateUI(account)
//        mGoogleSignInClient.silentSignIn().addOnCompleteListener(object : OnCompleteListener<GoogleSignInAccount>{
//            override fun onComplete(p0: Task<GoogleSignInAccount>) {
//                handleSignInResult(p0)
//            }
//        })

        //여기 나중에 바꾸기
//        var spf = getSharedPreferences("jwt", MODE_PRIVATE)
//        var editor: SharedPreferences.Editor = spf?.edit()!!
//        editor.putString("jwt","eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJidW4wMzczQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNjQ1NDUxOTA2LCJleHAiOjE2NDU0NTkxMDZ9.8sIOCibWEl9bW5g0AN8rwpv9WyWQ9DqR3oc_KBuvgl0")
//        editor.apply()


        binding.startGoogleSignin.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        })

//        binding.startLogoutBt.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                mGoogleSignInClient.signOut().addOnCompleteListener(object : OnCompleteListener<Void?> {
//                        override fun onComplete(p0: Task<Void?>) {
//                        }
//                    })
//                mGoogleSignInClient.revokeAccess()
//                    .addOnCompleteListener(object: OnCompleteListener<Void?> {
//                        override fun onComplete(p0: Task<Void?>) {
//                        }
//                    })
//                updateUI(null)
//                binding.startTestTv.text = "로그아웃됨"
//            }
//        })
    }

    fun updateUI(account: GoogleSignInAccount?){
        if (account !=null){
            binding.startGoogleSignin.visibility = View.GONE
//            binding.startTestTv.text = account.idToken + account.email
            Log.d("idToken",account.idToken.toString())
//            userService.isfavorok(getjwt(this))
        }
        else{
            binding.startGoogleSignin.visibility = View.VISIBLE
//            binding.startTestTv.text = "재 시도 해주세요"
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken!!
            Log.d("StartActivity_handleSigninResult",idToken.toString())
            userService.TokenSignin(TokenSigninRequest(idToken))
            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            updateUI(null)
            Log.d("StartActivity_handleSigninResult",e.toString())
        }
    }

    override fun onFavorLoading() {

    }

    override fun onFavorSuccess(isfavorok: Isfavorok) {
        userService.getUserinfo(getaccesstoken(this))
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onFavorFailure(code: Int, message: String) {
        startActivityWithClear(StartNameActivity::class.java)
    }

    override fun onTokenSigninLoading() {
    }

    override fun onTokenSigninSuccess(tokenSigninbody: Tokenrespbody) {
        setaccesstoken(this,tokenSigninbody.token)
        setrefreshtoken(this,tokenSigninbody.refreshToken)
        userService.isfavorok(getaccesstoken(this))
    }

    override fun onTokenSigninFailure(code: Int, message: String) {
        Log.d("tokenSigninFailure",message.toString())
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


}