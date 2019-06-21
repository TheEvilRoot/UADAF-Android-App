package org.uadaf.app.quoter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import org.uadaf.app.R
import org.uadaf.app.quoter.impl.QuoteViewHolder

class QuoterAdapter(
    private val presenter: QuoterPresenter
) : RecyclerView.Adapter<QuoteViewHolder>(), FastScrollRecyclerView.SectionedAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder =
        QuoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quote_layout, parent, false))

    override fun getItemCount(): Int =
        presenter.getQuotesCount()

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        presenter.bindQuoteView(position, holder)
    }

    override fun getSectionName(position: Int): String =
        presenter.getSectionName(position)

}