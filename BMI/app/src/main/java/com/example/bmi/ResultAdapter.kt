package com.example.bmi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ResultAdapter(private val results: ArrayList<Result>) : RecyclerView.Adapter<ResultAdapter.ResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_result, parent, false)
        return ResultHolder(view)
    }

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.height.text = results[position].height.toString()
        holder.weight.text = results[position].weight.toString()
        holder.date.text = results[position].date
        holder.bmiRate.text = results[position].bmiRate.toString()
        holder.status.text = results[position].status
    }

    class ResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val height: TextView = itemView.findViewById(R.id.height)
        val weight: TextView = itemView.findViewById(R.id.weight)
        val date: TextView = itemView.findViewById(R.id.date)
        val bmiRate: TextView = itemView.findViewById(R.id.bmiRate)
        val status: TextView = itemView.findViewById(R.id.status)
    }
}