package com.umcapplunching.cocktail_dakk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.viewbinding.ViewBinding

import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater) -> T): AppCompatActivity(), CoroutineScope{
    protected lateinit var binding: T
        private set

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var imm : InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?

        //코루틴 잡
        job = Job()
        initAfterBinding()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    protected abstract fun initAfterBinding()

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun startNextActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)

    }

    fun startActivityWithClear(activity: Class<*>?) {//이전액티비티는 모두 날린다.
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    // 키보드 숨기기
    fun hideKeyboard(v: View){
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}