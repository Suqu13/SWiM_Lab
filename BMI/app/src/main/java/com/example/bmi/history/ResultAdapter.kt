package com.example.bmi.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bmi.R


class ResultAdapter(private val results: ArrayList<Result>) : RecyclerView.Adapter<ResultAdapter.ResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_history_record, parent, false)
        return ResultHolder(view)
    }

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.height.text = results[position].height
        holder.weight.text = results[position].weight
        holder.date.text = results[position].date
        holder.bmiRate.text = results[position].bmiRate
        holder.status.text = results[position].status
    }

    class ResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val height: TextView = itemView.findViewById(R.id.height_editText_record)
        val weight: TextView = itemView.findViewById(R.id.weight_editText_record)
        val date: TextView = itemView.findViewById(R.id.date_editText_record)
        val bmiRate: TextView = itemView.findViewById(R.id.bmi_value_textView_record)
        val status: TextView = itemView.findViewById(R.id.status_textView_record)
    }
}