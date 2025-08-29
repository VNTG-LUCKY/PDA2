package com.example.myapplication_pda

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class DomesticShipmentActivity : AppCompatActivity() {
    
    private val shipmentData = listOf(
        ShipmentItem("SH001", "철근", "100톤", "서울시 강남구", "2024-01-15", "배송 중"),
        ShipmentItem("SH002", "철판", "50톤", "부산시 해운대구", "2024-01-16", "배송 준비"),
        ShipmentItem("SH003", "파이프", "75톤", "대구시 수성구", "2024-01-17", "배송 완료"),
        ShipmentItem("SH004", "앵글", "30톤", "인천시 연수구", "2024-01-18", "배송 중"),
        ShipmentItem("SH005", "채널", "45톤", "광주시 서구", "2024-01-19", "배송 준비")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domestic_shipment)
        
        // 액션바 제목 설정
        supportActionBar?.title = "내수상차 관리"
        
        // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        // 상단 정보 설정
        setupHeaderInfo()
        
        // 상차 목록 설정
        setupShipmentList()
        
        // 새 상차 등록 버튼
        setupAddButton()
    }
    
    private fun setupHeaderInfo() {
        val tvTotalCount = findViewById<TextView>(R.id.tvTotalCount)
        val tvInProgress = findViewById<TextView>(R.id.tvInProgress)
        val tvCompleted = findViewById<TextView>(R.id.tvCompleted)
        
        val totalCount = shipmentData.size
        val inProgressCount = shipmentData.count { it.status == "배송 중" }
        val completedCount = shipmentData.count { it.status == "배송 완료" }
        
        tvTotalCount.text = "총 ${totalCount}건"
        tvInProgress.text = "진행중 ${inProgressCount}건"
        tvCompleted.text = "완료 ${completedCount}건"
    }
    
    private fun setupShipmentList() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewShipments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ShipmentAdapter(shipmentData)
        recyclerView.adapter = adapter
    }
    
    private fun setupAddButton() {
        val btnAddShipment = findViewById<Button>(R.id.btnAddShipment)
        btnAddShipment.setOnClickListener {
            Toast.makeText(this, "새 상차 등록 기능이 호출됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    
    data class ShipmentItem(
        val orderNumber: String,
        val productName: String,
        val quantity: String,
        val destination: String,
        val date: String,
        val status: String
    )
    
    class ShipmentAdapter(private val items: List<ShipmentItem>) : 
        RecyclerView.Adapter<ShipmentAdapter.ShipmentViewHolder>() {
        
        class ShipmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvOrderNumber: TextView = view.findViewById(R.id.tvOrderNumber)
            val tvProductName: TextView = view.findViewById(R.id.tvProductName)
            val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
            val tvDestination: TextView = view.findViewById(R.id.tvDestination)
            val tvDate: TextView = view.findViewById(R.id.tvDate)
            val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipmentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shipment, parent, false)
            return ShipmentViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ShipmentViewHolder, position: Int) {
            val item = items[position]
            holder.tvOrderNumber.text = item.orderNumber
            holder.tvProductName.text = item.productName
            holder.tvQuantity.text = item.quantity
            holder.tvDestination.text = item.destination
            holder.tvDate.text = item.date
            holder.tvStatus.text = item.status
            
            // 상태에 따른 색상 설정
            when (item.status) {
                "배송 중" -> holder.tvStatus.setTextColor(0xFF1E3A8A.toInt()) // 파란색
                "배송 완료" -> holder.tvStatus.setTextColor(0xFF10B981.toInt()) // 초록색
                "배송 준비" -> holder.tvStatus.setTextColor(0xFFF59E0B.toInt()) // 주황색
            }
            
            holder.itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, 
                    "${item.orderNumber} 상세정보 보기", Toast.LENGTH_SHORT).show()
            }
        }
        
        override fun getItemCount() = items.size
    }
}
