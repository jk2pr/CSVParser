package com.jk.csvparser

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_data.view.*

class DataAdapter : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private var datas = ArrayList<Restaurant>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }


    fun addItems(items: List<Restaurant>) {
        datas.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        datas.clear()

    }

    override fun getItemCount(): Int {
        return datas.size
    }


    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Restaurant) = with(itemView) {
            itemView.txt_rest_name.text = item.name
            val stringBuffer=StringBuffer()
            item.openingHours.forEach {
                stringBuffer.append(it.key).append(" ").append(it.value).append("\n")
            }
            itemView.txt_timing.text=stringBuffer.toString()


        }
    }
}