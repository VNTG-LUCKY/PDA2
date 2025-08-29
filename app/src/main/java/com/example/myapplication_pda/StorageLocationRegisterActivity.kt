package com.example.myapplication_pda

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class StorageLocationRegisterActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnMenu: ImageButton
    private lateinit var btnNotification: ImageButton

    private lateinit var etPalletScan: EditText
    private lateinit var tvStorageType: TextView
    private lateinit var tvStorageNo: TextView
    private lateinit var etBarcode: EditText
    private lateinit var tvCnt: TextView

    private lateinit var rgStatus: RadioGroup
    private lateinit var rbNormal: RadioButton
    private lateinit var rbFraction: RadioButton
    private lateinit var tvFraction: TextView
    private lateinit var btnInput: Button

    private val storageTypes = listOf("선택", "물류창고(옥내)", "물류창고(옥외)", "생산창고")
    private val storageNumbers = listOf("선택", "A-01", "A-02", "B-01", "C-03")
    private val quantities = listOf("본수", "1", "2", "3", "4", "5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_location_register)
        supportActionBar?.hide()

        bindViews()
        setListeners()
    }

    private fun bindViews() {
        btnBack = findViewById(R.id.btnBack)
        btnMenu = findViewById(R.id.btnMenu)
        btnNotification = findViewById(R.id.btnNotification)
        etPalletScan = findViewById(R.id.etPalletScan)
        tvStorageType = findViewById(R.id.tvStorageType)
        tvStorageNo = findViewById(R.id.tvStorageNo)
        etBarcode = findViewById(R.id.etBarcode)
        tvCnt = findViewById(R.id.tvCnt)
        rgStatus = findViewById(R.id.rgStatus)
        rbNormal = findViewById(R.id.rbNormal)
        rbFraction = findViewById(R.id.rbFraction)
        tvFraction = findViewById(R.id.tvFraction)
        btnInput = findViewById(R.id.btnInput)
    }

    private fun setListeners() {
        btnBack.setOnClickListener { finish() }

        btnMenu.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("메뉴")
                .setItems(arrayOf("설정", "도움말")) { _, which ->
                    Toast.makeText(this, if (which == 0) "설정" else "도움말", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        btnNotification.setOnClickListener {
            Toast.makeText(this, "알림이 없습니다", Toast.LENGTH_SHORT).show()
        }

        tvStorageType.setOnClickListener { showListDialog("적재대구분", storageTypes) { tvStorageType.text = it } }
        tvStorageNo.setOnClickListener { showListDialog("적재대번호", storageNumbers) { tvStorageNo.text = it } }
        tvFraction.setOnClickListener { showListDialog("본수", quantities) { tvFraction.text = it } }

        btnInput.setOnClickListener {
            val type = tvStorageType.text.toString()
            val no = tvStorageNo.text.toString()
            val barcode = etBarcode.text.toString()
            if (type == "선택") {
                toast("적재대구분을 선택하세요")
                return@setOnClickListener
            }
            if (no == "적재대번호를 선택하세요." || no == "선택") {
                toast("적재대번호를 선택하세요")
                return@setOnClickListener
            }
            if (barcode.isBlank()) {
                toast("바코드를 입력하세요")
                return@setOnClickListener
            }
            toast("입력 완료")
        }

        // 간단한 CNT 계산 예시 (바코드 입력 길이)
        etBarcode.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvCnt.text = (s?.length ?: 0).toString()
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun showListDialog(title: String, items: List<String>, onSelect: (String) -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setItems(items.toTypedArray()) { _, which -> onSelect(items[which]) }
            .show()
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
