package com.example.wuhan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class checkAdapter(
    private val context: showMyRecord,
    private var records: List<SignUpRecord>
) : RecyclerView.Adapter<checkAdapter.viewHolder>() {


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
    fun updateList(list: List<SignUpRecord>) {
        records = list
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //把layout檔的元件們拉進來，指派給當地變數
        val name: TextView = itemView.findViewById(R.id.tvName)
        val room: TextView = itemView.findViewById(R.id.tvStudentId)
        val phone: TextView = itemView.findViewById(R.id.tvPhone)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
        val time: TextView = itemView.findViewById(R.id.tvTime)


        fun bind(item: SignUpRecord) {
            val i: Int = adapterPosition
            //綁定當地變數與dataModel中的每個值
            name.text = "姓名: "+records[i].name
            room.text = "學號: "+records[i].id
            phone.text = "電話: "+records[i].phone
            location.text = "簽到地點: "+records[i].location
            time.text = "簽到時間: "+records[i].time
        }
    }
}