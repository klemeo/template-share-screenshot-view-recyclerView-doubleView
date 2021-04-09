package ru.android.easyscreenshots.home

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*
import ru.android.easyscreenshots.R
import ru.android.easyscreenshots.base.SimpleScreenshot.createScreenImageFile
import ru.android.easyscreenshots.base.SimpleScreenshot.getScreenBitmapFromView
import java.io.File

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val mList: MutableList<Model> = mutableListOf()
    private var mListener: Listener? = null

    fun add(lst: List<Model>) {
        mList.addAll(lst)
        notifyItemInserted(this.itemCount)
    }

    fun setListener(listener: Listener) {
        mListener = listener
    }

    interface Listener {
        fun onScreenshot(title: String, file: File, bitmap: Bitmap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
    )

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Model) {

            itemView.sharedItemView.setOnClickListener {
                val bitmap = getScreenBitmapFromView(this.itemView.cvPostItem)
                val file = createScreenImageFile("CardView", ".jpg")
                mListener?.onScreenshot("CardView", file, bitmap)
            }

        }
    }
}