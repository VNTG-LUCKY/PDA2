package com.example.myapplication_pda

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

class MainMenuActivity : AppCompatActivity() {
    private val menuData = listOf(
        listOf(
            MenuItem("내수상차", R.drawable.ic_launcher_foreground),
            MenuItem("내수상차진행현황", R.drawable.ic_launcher_foreground),
            MenuItem("처리이력조회", R.drawable.ic_launcher_foreground),
            MenuItem("수출상차", R.drawable.ic_launcher_foreground),
            MenuItem("수출상차지시조회", R.drawable.ic_launcher_foreground),
            MenuItem("재고조회", R.drawable.ic_launcher_foreground)
        ),
        listOf(
            MenuItem("원자재입고", R.drawable.ic_launcher_foreground),
            MenuItem("재고창고수정", R.drawable.ic_launcher_foreground),
            MenuItem("원재료재고이전", R.drawable.ic_launcher_foreground)
        ),
        listOf(
            MenuItem("적재대조회", R.drawable.ic_launcher_foreground),
            MenuItem("적재위치등록", R.drawable.ic_launcher_foreground),
            MenuItem("적재위치조회", R.drawable.ic_launcher_foreground)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_main_menu)

            val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
            tabLayout.addTab(tabLayout.newTab().setText("출하관리"))
            tabLayout.addTab(tabLayout.newTab().setText("원자재관리"))
            tabLayout.addTab(tabLayout.newTab().setText("적재위치관리"))

            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = GridLayoutManager(this, 3)
            val adapter = MenuAdapter(menuData[0])
            recyclerView.adapter = adapter

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    adapter.updateMenu(menuData[tab.position])
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
            // 앱 시작 시 첫 번째 탭의 메뉴가 보이도록 보장
            adapter.updateMenu(menuData[0])
        } catch (e: Exception) {
            Toast.makeText(this, "오류: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    data class MenuItem(val title: String, val iconRes: Int)

    class MenuAdapter(private var items: List<MenuItem>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
        class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val icon: ImageView = view.findViewById(R.id.menuIcon)
            val title: TextView = view.findViewById(R.id.menuTitle)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
            return MenuViewHolder(view)
        }
        override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
            val item = items[position]
            holder.icon.setImageResource(item.iconRes)
            holder.title.text = item.title
            holder.itemView.setOnClickListener {
                when (item.title) {
                    "내수상차" -> {
                        val intent = Intent(holder.itemView.context, DomesticShipmentActivity::class.java)
                        holder.itemView.context.startActivity(intent)
                    }
                    "적재대조회" -> {
                        val intent = Intent(holder.itemView.context, StorageInquiryActivity::class.java)
                        holder.itemView.context.startActivity(intent)
                    }
                    else -> {
                        Toast.makeText(holder.itemView.context, "${item.title} 클릭됨", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        override fun getItemCount() = items.size
        fun updateMenu(newItems: List<MenuItem>) {
            items = newItems
            notifyDataSetChanged()
        }
    }
} 