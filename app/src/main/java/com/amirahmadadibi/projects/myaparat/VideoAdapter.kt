package com.amirahmadadibi.projects.myaparat

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.amirahmadadibi.projects.myaparat.model.Video
import com.bumptech.glide.Glide
import ir.farshid_roohi.customadapterrecycleview.AdapterRecyclerView

class VideoAdapter() : AdapterRecyclerView<Video>(
    R.layout.video_list_item,
    R.layout.recyclerview_loading_layout,
    R.layout.recyclerview_failed_layout,
    R.id.NO_DEBUG
) {
    override fun onBindView(
        viewHolder: ItemViewHolder,
        position: Int,
        context: Context,
        element: Video?
    ) {
        val textViewTitle = viewHolder.itemView.findViewById<TextView>(R.id.textViewTitle)
        val imageViewThumbnail =
            viewHolder.itemView.findViewById<ImageView>(R.id.imageViewThumbnail)

        Glide.with(context).load(element?.posterURL).placeholder(R.drawable.ic_logo_aparat)
            .into(imageViewThumbnail)
        textViewTitle.text = element?.title
    }


}