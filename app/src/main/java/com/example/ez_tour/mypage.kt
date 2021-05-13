package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class mypage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val

        btn_search.setOnClickListener {
            val intent = Intent(this, search::class.java)
            startActivity(intent)
        }
        // mypage 버튼 클릭리스너
        btn_mypage.setOnClickListener {
            val intent = Intent(this, mypage::class.java)
            startActivity(intent)
        }
    }
}