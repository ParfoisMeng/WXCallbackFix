package com.parfoismeng.wxcallbackfix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parfoismeng.annotation.WXCallbackFix

@WXCallbackFix(BuildConfig.APPLICATION_ID + ".wxapi")
open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
