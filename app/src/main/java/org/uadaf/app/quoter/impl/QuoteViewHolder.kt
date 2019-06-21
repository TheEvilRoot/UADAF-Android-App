package org.uadaf.app.quoter.impl

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.quote_layout.view.*
import org.uadaf.app.quoter.QuoteRowView

class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), QuoteRowView {
    override fun setID(id: Int) {
        with(itemView) {
            idView.text = "#$id"
        }
    }

    override fun setAdder(adder: String) {
        // No-op
    }

    override fun setAuthor(author: String) {
        with(itemView) {
            authorView.text = author
        }
    }

    override fun setText(text: String) {
        with(itemView) {
            titleView.text = text
        }
    }

    override fun setEdited(by: String, at: String) {
        with(itemView) {
            editView.visibility = View.VISIBLE
            editView.text = "Edited by $by at $at"
        }
    }

    override fun setNotEdited() {
        with(itemView) {
            editView.visibility = View.INVISIBLE
        }
    }
}