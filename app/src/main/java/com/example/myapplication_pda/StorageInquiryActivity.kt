package com.example.myapplication_pda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StorageInquiryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_inquiry)
        
        // 액션바 제목 설정
        supportActionBar?.title = "적재대조회"
        
        // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

