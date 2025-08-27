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
            
            // 입력 유효성 검사
            if (id.isEmpty()) {
                editTextId.error = "사원번호를 입력해주세요"
                return@setOnClickListener
            }
            
            if (password.isEmpty()) {
                editTextPassword.error = "비밀번호를 입력해주세요"
                return@setOnClickListener
            }
            
            // 로그인 처리
            if (id == "Seah" && password == "1234") {
                // 로그인 성공 시 버튼 비활성화 (중복 클릭 방지)
                btnLogin.isEnabled = false
                btnLogin.text = "로그인 중..."
                
                // 실제 환경에서는 네트워크 요청을 여기서 처리
                // 현재는 즉시 메인 메뉴로 이동
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // 로그인 실패 시 에러 메시지 표시
                editTextPassword.error = "사원번호 또는 비밀번호가 올바르지 않습니다"
                Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 엔터키 처리
        editTextId.setOnEditorActionListener { _, _, _ ->
            editTextPassword.requestFocus()
            true
        }
        
        editTextPassword.setOnEditorActionListener { _, _, _ ->
            btnLogin.performClick()
            true
        }
    }
} 