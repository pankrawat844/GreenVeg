package com.app.greenveg.ui.history

import android.graphics.Color
import com.app.greenveg.R
import com.app.greenveg.databinding.ItemHistoryBinding


class HistoryAdapter(val item: HistoryItem) :
    com.xwray.groupie.databinding.BindableItem<ItemHistoryBinding>() {
    override fun getLayout(): Int = R.layout.item_history

    override fun bind(viewHolder: ItemHistoryBinding, position: Int) {
        viewHolder.data = item
        if (item.orderStatus.equals("cancelled", ignoreCase = false)) {
            viewHolder.status.setTextColor(Color.RED)
        }
        if (item.orderStatus.equals("delivered", ignoreCase = false)) {
            viewHolder.status.setTextColor(Color.GREEN)
        }
    }

}