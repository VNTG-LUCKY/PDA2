package com.example.myapplication_pda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextId = findViewById<EditText>(R.id.editTextId)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // 기본값 세팅
        editTextId.setText("Seah")
        editTextPassword.setText("1234")

        btnLogin.setOnClickListener {
            val id = editTextId.text.toString()
            val password = editTextPassword.text.toString()
            if (id == "Seah" && password == "1234") {
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "아이디 또는 패스워드가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
} 