package com.example.ez_tour

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_search.*
class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val btn_map = findViewById<Button>(R.id.btn_map)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_appinfo = findViewById<Button>(R.id.btn_appinfo) as ImageButton

        val mRecyclerView = findViewById<RecyclerView>(R.id.review)
        val list = ArrayList<RecycleData>()

        var itemUrl:String? = null
        var itemName:String? = null
        var itemTag:String? = null
        var itemcount:Int = 0

        for(i in 1..itemcount){
            itemUrl = "URL"
            itemName = "이름"
            itemTag = "카페"

        }

        val adapter = RecyclerAdapter(list)
        review.adapter = adapter

        btn_search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        // mypage 버튼 클릭리스너
        btn_map.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        btn_appinfo.setOnClickListener {
            val intent = Intent(this, AppinfoActivity::class.java)
            startActivity(intent)
        }

    }

    public void RecordsCount(){

        FirebaseDatabase.DefaultInstance.GetReference("/scoreboard").GetValueAsync().ContinueWith(task =>
        {
            if (task.IsFaulted)
            {
                Debug.Log("error!");
                if (buildDebug)
                    buildDebug.text += "\nRecordsCount Error";
            }
            else if (task.IsCompleted)
            {
                DataSnapshot snapshot = task.Result;
                Debug.Log((int)snapshot.ChildrenCount);
                records = (int)snapshot.ChildrenCount;
                if(buildDebug)
                    buildDebug.text += "\n" + records.ToString();
            }
        });

    }
}