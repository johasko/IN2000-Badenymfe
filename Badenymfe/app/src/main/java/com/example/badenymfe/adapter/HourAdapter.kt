package com.example.badenymfe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.badenymfe.R
import com.example.badenymfe.databinding.HourItemBinding
import org.joda.time.DateTime
import java.util.*

/**
 * A bridge between the UI component and data set.
 */
class HourAdapter(private val callback: HourClick) : RecyclerView.Adapter<HourViewHolder>() {

    var timeAndDays = listOf<DateTime>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val withDataBinding: HourItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            HourViewHolder.LAYOUT,
            parent,
            false
        )
        return HourViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int = timeAndDays.count()

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.timeAndDay = timeAndDays[position]
            it.hourCallback = callback
            val monthName = timeAndDays[position].toString("MMM", Locale.forLanguageTag("nb"))
            it.formattedDatetime = timeAndDays[position].toString("dd. ") + monthName.toString() + " kl." + timeAndDays[position].toString("HH:mm")
        }
    }
}

/**
 * Click listener for location in the location recycler view.
 */
class HourClick(val block: (DateTime) -> Unit) {
    /**
     * Called when an hour is clicked in the hourly recycler view.
     */
    fun onClick(timeAndDays: DateTime)
            = block(timeAndDays)
}

/**
 * View holder for hour item.
 */
class HourViewHolder(val viewDataBinding: HourItemBinding):
        RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.hour_item
    }
}
