package sa.med.imc.myimc.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_date.view.*
import sa.med.imc.myimc.Listeners.DateSelectionListener
import sa.med.imc.myimc.R
import sa.med.imc.myimc.models.Date

class DatesAdapter(val context: Context, val dates: List<Date>, val currentDate: Int, val listener: DateSelectionListener) : RecyclerView.Adapter<DatesAdapter.DateViewHolder>() {

    private var lastSelectedItemIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder =
        DateViewHolder(LayoutInflater.from(context).inflate(R.layout.item_date, parent, false))

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) =
        holder.bind(dates.get(position))

    override fun getItemCount(): Int = dates.size

    override fun getItemViewType(position: Int): Int = position

    inner class DateViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(date: Date) {
            itemView.day.text = date.day
            itemView.date.text = date.date.toString()

            if (date.isDisabled) {
                itemView.child.setOnClickListener(null)
                itemView.day.alpha = 0.5f
                itemView.date.alpha = 0.5f
            } else {
                itemView.child.setOnClickListener {

                    if (lastSelectedItemIndex != RecyclerView.NO_POSITION) {
                        dates.get(lastSelectedItemIndex).isSelected = false
                        notifyItemChanged(lastSelectedItemIndex)

                        lastSelectedItemIndex = adapterPosition
                        date.isSelected = true
                        notifyItemChanged(adapterPosition)

                        listener.onSelected(dates.get(adapterPosition))
                    } else {
                        lastSelectedItemIndex = adapterPosition
                        date.isSelected = true

                        notifyItemChanged(adapterPosition)

                        listener.onSelected(dates.get(adapterPosition))
                    }
                }
            }

            doSelectCurrentDate()

            if (date.isSelected) {
                itemView.date.setTextColor(ContextCompat.getColor(context, R.color.white))
                itemView.bg_date.visibility = View.VISIBLE
            } else {
                itemView.date.setTextColor(ContextCompat.getColor(context, R.color.black))
                itemView.bg_date.visibility = View.INVISIBLE
            }
        }
    }

    private fun doSelectCurrentDate() {
        if (lastSelectedItemIndex == -1) {
            lastSelectedItemIndex = currentDate
            dates.get(lastSelectedItemIndex).isSelected = true
        }
    }
}