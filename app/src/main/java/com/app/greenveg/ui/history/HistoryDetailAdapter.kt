package com.app.greenveg.ui.history

import com.app.greenveg.R
import com.app.greenveg.databinding.ItemHistoryDetailBinding


class HistoryDetailAdapter(val item: HistoryDetailItem.Data.Response) :
    com.xwray.groupie.databinding.BindableItem<ItemHistoryDetailBinding>() {
    override fun getLayout(): Int = R.layout.item_history_detail

    override fun bind(viewHolder: ItemHistoryDetailBinding, position: Int) {
        viewHolder.data = item

    }

}