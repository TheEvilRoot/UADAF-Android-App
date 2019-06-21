package org.uadaf.app.ith

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.uadaf.app.R
import org.uadaf.app.ith.impl.*

class ITHStoryAdapter(
    private val presenter: ITHRowViewPresenter
) : RecyclerView.Adapter<ITHStoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ITHStoryViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            0 -> ITHStoryTitleViewHolder(inflater.inflate(R.layout.ith_story_title_layout, parent, false))
            1 -> ITHStoryTagsViewHolder(inflater.inflate(R.layout.ith_story_tags_layout, parent, false))
            2 -> ITHStoryTextViewHolder(inflater.inflate(R.layout.ith_story_text_layout, parent, false))
            3 -> ITHStoryInfoViewHolder(inflater.inflate(R.layout.ith_story_info_layout, parent, false))
            else -> throw RuntimeException("Unknown view type in ITHStoryAdapter $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

    override fun getItemCount(): Int =
        presenter.getRowsCount()

    override fun onBindViewHolder(holder: ITHStoryViewHolder, position: Int) {
        when (holder) {
            is ITHStoryTitleView -> presenter.bindStoryTitle(holder)
            is ITHStoryTagsView -> presenter.bindStoryTags(holder)
            is ITHStoryTextView -> presenter.bindStoryText(holder)
            is ITHStoryInfoView -> presenter.bindStoryInfo(holder)
        }
    }

}