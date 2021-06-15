package com.example.wuhan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class checkAdapter : RecyclerView.Adapter<checkAdapter.viewHolder>() {

    var records = listOf<SignUpRecord>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.check_record_layout, parent, false)
        return viewHolder(example)

    }

    override fun getItemCount() = records.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        //呼叫上面的bind方法來綁定資料
        holder.bind(records[position])

    }

    //更新資料用
    fun updateList(list: ArrayList<SignUpRecord>) {
        records = list
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //把layout檔的元件們拉進來，指派給當地變數
        val name: TextView = itemView.findViewById(R.id.tvName)
        val room: TextView = itemView.findViewById(R.id.tvRoom)
        val phone: TextView = itemView.findViewById(R.id.tvPhone)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
        val time: TextView = itemView.findViewById(R.id.tvTime)


        fun bind(item: SignUpRecord) {
            val i: Int = adapterPosition
            //綁定當地變數與dataModel中的每個值
            name.text = records[i].name
            room.text = records[i].room
            phone.text = records[i].phone
            location.text = records[i].location
            time.text = records[i].time
        }
    }
}