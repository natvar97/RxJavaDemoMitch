package com.indialone.rxjavademomitch.dishes

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.indialone.rxjavademomitch.MainActivity
import com.indialone.rxjavademomitch.databinding.ItemCustomListLayoutBinding

class CustomListItemAdapter(
    private val activity: Activity,
    private val list: ArrayList<String>,
    private val selection: String
) : RecyclerView.Adapter<CustomListItemAdapter.CustomListItemViewHolder>() {
    class CustomListItemViewHolder(itemView: ItemCustomListLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvTitle = itemView.tvText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomListItemViewHolder {
        val view =
            ItemCustomListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomListItemViewHolder, position: Int) {
        holder.tvTitle.text = list[position]
        holder.itemView.setOnClickListener {
            if (activity is MainActivity) {
                activity.filterSelection(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}