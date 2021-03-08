package com.example.myapplication.payComplete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.mainPage.Sikdang_main

class PayCompletePage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        Log.d("확인 PayCompletePage 호출됨", "1")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paycompletepage)
        var intent = getIntent()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent:Intent = Intent(getApplicationContext(),Sikdang_main::class.java);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

}