package net.infolibertaire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.infolibertaire.data.Favorite

class FavoritesAdapter(
    private val onItemClick: (String) -> Unit,
    private val onDeleteClick: (Favorite) -> Unit
) : ListAdapter<Favorite, FavoritesAdapter.ViewHolder>(FavoriteDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.titleText)
        val urlText: TextView = view.findViewById(R.id.urlText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.titleText.text = favorite.title
        holder.urlText.text = favorite.url
        
        holder.itemView.setOnClickListener {
            onItemClick(favorite.url)
        }
        
        holder.deleteButton.setOnClickListener {
            onDeleteClick(favorite)
        }
    }
}

class FavoriteDiffCallback : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
    }
}
