package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class mypage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val btn_map = findViewById<Button>(R.id.btn_map)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_appinfo = findViewById<Button>(R.id.btn_appinfo) as ImageButton

        btn_search.setOnClickListener {
            val intent = Intent(this, search::class.java)
            startActivity(intent)
        }
        // mypage 버튼 클릭리스너
        btn_map.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        btn_appinfo.setOnClickListener {
            val intent = Intent(this, appinfo::class.java)
            startActivity(intent)
        }

    }
}