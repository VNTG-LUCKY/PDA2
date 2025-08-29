package com.example.myapplication_pda

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class StorageInquiryActivity : AppCompatActivity() {
    
    private lateinit var etBarcode: EditText
    private lateinit var etMaterialCode: EditText
    private lateinit var etSalesOrder: EditText
    private lateinit var etLineNumber: EditText
    private lateinit var etBatchNumber: EditText
    private lateinit var etSequenceNumber: EditText
    private lateinit var etFromDate: EditText
    private lateinit var etToDate: EditText
    private lateinit var btnBack: ImageButton
    private lateinit var btnNotification: ImageButton
    private lateinit var btnMenu: ImageButton
    
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_inquiry)
        
        supportActionBar?.hide()
        
        initViews()
        setupListeners()
        setDefaultDates()
    }
    
    private fun initViews() {
        etBarcode = findViewById(R.id.etBarcode)
        etMaterialCode = findViewById(R.id.etMaterialCode)
        etSalesOrder = findViewById(R.id.etSalesOrder)
        etLineNumber = findViewById(R.id.etLineNumber)
        etBatchNumber = findViewById(R.id.etBatchNumber)
        etSequenceNumber = findViewById(R.id.etSequenceNumber)
        etFromDate = findViewById(R.id.etFromDate)
        etToDate = findViewById(R.id.etToDate)
        btnBack = findViewById(R.id.btnBack)
        btnNotification = findViewById(R.id.btnNotification)
        btnMenu = findViewById(R.id.btnMenu)
    }
    
    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }
        btnNotification.setOnClickListener { showNotificationDialog() }
        btnMenu.setOnClickListener { showMenuDialog() }
        
        etFromDate.setOnClickListener { showDatePicker(etFromDate) }
        etToDate.setOnClickListener { showDatePicker(etToDate) }
        etBarcode.setOnClickListener { showBarcodeScanner() }
        
        findViewById<ImageView>(R.id.ic_search)?.setOnClickListener {
            showSequenceSearchDialog()
        }
    }
    
    private fun setDefaultDates() {
        val today = dateFormat.format(calendar.time)
        etFromDate.setText(today)
        etToDate.setText(today)
    }
    
    private fun showDatePicker(editText: EditText) {
        val currentDate = editText.text.toString()
        val dateParts = currentDate.split("-")
        
        val year = if (dateParts.size == 3) dateParts[0].toIntOrNull() ?: calendar.get(Calendar.YEAR) else calendar.get(Calendar.YEAR)
        val month = if (dateParts.size == 3) dateParts[1].toIntOrNull()?.minus(1) ?: calendar.get(Calendar.MONTH) else calendar.get(Calendar.MONTH)
        val day = if (dateParts.size == 3) dateParts[2].toIntOrNull() ?: calendar.get(Calendar.DAY_OF_MONTH) else calendar.get(Calendar.DAY_OF_MONTH)
        
        DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                editText.setText(selectedDate)
            },
            year, month, day
        ).show()
    }
    
    private fun showBarcodeScanner() {
        Toast.makeText(this, "바코드 스캐너를 실행합니다", Toast.LENGTH_SHORT).show()
    }
    
    private fun showSequenceSearchDialog() {
        val input = EditText(this).apply { hint = "순번을 입력하세요" }
        
        AlertDialog.Builder(this)
            .setTitle("순번 검색")
            .setView(input)
            .setPositiveButton("검색") { _, _ ->
                val sequence = input.text.toString()
                if (sequence.isNotEmpty()) {
                    etSequenceNumber.setText(sequence)
                    performSearch()
                }
            }
            .setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
            .show()
    }
    
    private fun showNotificationDialog() {
        AlertDialog.Builder(this)
            .setTitle("알림")
            .setMessage("새로운 알림이 없습니다.")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    
    private fun showMenuDialog() {
        val menuItems = arrayOf("설정", "도움말", "정보")
        AlertDialog.Builder(this)
            .setTitle("메뉴")
            .setItems(menuItems) { _, which ->
                val message = when (which) {
                    0 -> "설정 메뉴"
                    1 -> "도움말 메뉴"
                    2 -> "정보 메뉴"
                    else -> "알 수 없는 메뉴"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            .show()
    }
    
    private fun performSearch() {
        val barcode = etBarcode.text.toString().trim()
        val materialCode = etMaterialCode.text.toString().trim()
        val salesOrder = etSalesOrder.text.toString().trim()
        val lineNumber = etLineNumber.text.toString().trim()
        val batchNumber = etBatchNumber.text.toString().trim()
        val sequenceNumber = etSequenceNumber.text.toString().trim()
        
        if (barcode.isEmpty() && materialCode.isEmpty() && salesOrder.isEmpty() && 
            lineNumber.isEmpty() && batchNumber.isEmpty() && sequenceNumber.isEmpty()) {
            Toast.makeText(this, "검색 조건을 하나 이상 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        
        Toast.makeText(this, "검색 중...", Toast.LENGTH_SHORT).show()
    }
}

