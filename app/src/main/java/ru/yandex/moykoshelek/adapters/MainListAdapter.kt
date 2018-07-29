package ru.yandex.moykoshelek.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.yandex.moykoshelek.database.entities.TransactionData
import ru.yandex.moykoshelek.R
import ru.yandex.moykoshelek.helpers.expandablelayout.ExpandableLayout

class MainListAdapter(var transactionList: List<TransactionData>, private val context: Context?): RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.transaction_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val layout = itemView.findViewById<ExpandableLayout>(R.id.expandable_layout)!!
    }

}
