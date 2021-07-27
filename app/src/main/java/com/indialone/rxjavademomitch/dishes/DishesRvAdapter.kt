package com.indialone.rxjavademomitch.dishes

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.indialone.rxjavademomitch.MainActivity
import com.indialone.rxjavademomitch.databinding.DishItemLayoutBinding
import com.indialone.rxjavademomitch.dishes.models.search.RecipesItem

class DishesRvAdapter(
    private val activity: MainActivity,
    private var dishes: ArrayList<RecipesItem>
) : RecyclerView.Adapter<DishesRvAdapter.DishesRvViewHolder>() {
    class DishesRvViewHolder(itemView: DishItemLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvTitle = itemView.tvTitle
        val tvPublisher = itemView.tvPublisher
        val ivDish = itemView.ivDish
        val ivNext = itemView.ivNext

        fun bind(dish: RecipesItem) {
            tvTitle.text = dish.title
            tvPublisher.text = dish.publisher
            Glide.with(itemView.context)
                .load(dish.image_url)
                .centerCrop()
                .into(ivDish)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesRvViewHolder {
        val view = DishItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishesRvViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishesRvViewHolder, position: Int) {
        holder.bind(dish = dishes[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DishDetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_DISH_ID, dishes[position].recipe_id)

            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity,
                Pair<View , String>(holder.ivDish, "recipe_image"),
                Pair<View , String>(holder.tvTitle, "recipe_title"),
                Pair<View , String>(holder.tvPublisher, "recipe_publisher"),
            )

            it.context.startActivity(intent, options.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }
}