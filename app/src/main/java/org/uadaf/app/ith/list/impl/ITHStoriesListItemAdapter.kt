package org.uadaf.app.ith.list.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ith_stories_list_item_layout.view.*
import org.uadaf.app.R
import org.uadaf.app.ith.list.ITHStoriesListPresenter
import org.uadaf.app.ith.list.ITHStoriesListRowView

class ITHStoriesListItemAdapter(
    val presenter: ITHStoriesListPresenter
): RecyclerView.Adapter<ITHStoriesListItemAdapter.ITHStoriesListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ITHStoriesListItemViewHolder =
        ITHStoriesListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ith_stories_list_item_layout, parent, false))

    override fun getItemCount(): Int =
        presenter.storiesCount()

    override fun onBindViewHolder(holder: ITHStoriesListItemViewHolder, position: Int) {
        presenter.bindStoriesListRow(position, holder)
    }


    class ITHStoriesListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), ITHStoriesListRowView {
        override fun setId(id: String) {
            itemView.run {
                idView.text = id
            }
        }

        override fun setTitle(title: String) {
            itemView.run {
                titleView.text = title
            }
        }

        override fun setTags(tagsString: String) {
            itemView.run {
                tagsView.text = tagsString
            }
        }

    }
}