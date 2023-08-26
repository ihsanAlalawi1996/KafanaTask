package com.example.ihsanstask.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ihsanstask.data.models.EventModel
import com.example.ihsanstask.databinding.EventCellBinding

class EventsAdapter(private val listener: OnClickListener) :
    ListAdapter<EventModel, EventsAdapter.ViewHolder>(JobDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            EventCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: EventCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: EventModel) {
            binding.let {
                it.model = model
                it.viewHolder = this
                it.executePendingBindings()
            }
        }

        fun edit(id: Int) {
            listener.editEvent(id)
        }

        fun selectEvent(view: AppCompatCheckBox, id: Int) {
            if (view.isChecked) listener.selectEvent(id)
            else listener.removeEvent(id)
        }
    }

    interface OnClickListener {
        fun editEvent(id: Int)
        fun selectEvent(id: Int)
        fun removeEvent(id: Int)
    }
}

object JobDiffCallBack : DiffUtil.ItemCallback<EventModel>() {
    override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean =
        oldItem == newItem
}
