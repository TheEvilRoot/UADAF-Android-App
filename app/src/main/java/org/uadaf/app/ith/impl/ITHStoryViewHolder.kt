package org.uadaf.app.ith.impl

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.ith_story_info_layout.view.*
import kotlinx.android.synthetic.main.ith_story_tags_layout.view.*
import kotlinx.android.synthetic.main.ith_story_text_layout.view.*
import kotlinx.android.synthetic.main.ith_story_title_layout.view.*
import org.uadaf.app.ith.ITHStoryInfoView
import org.uadaf.app.ith.ITHStoryTagsView
import org.uadaf.app.ith.ITHStoryTextView
import org.uadaf.app.ith.ITHStoryTitleView

abstract class ITHStoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ITHStoryTitleViewHolder(itemView: View) : ITHStoryViewHolder(itemView), ITHStoryTitleView {
    override fun setTitle(id: String, title: String) {
        with(itemView) {
            titleView.text = "$id: $title"
        }
    }
}

class ITHStoryInfoViewHolder(itemView: View) : ITHStoryViewHolder(itemView), ITHStoryInfoView {
    override fun setDate(date: String) {
        with(itemView) {
            dateView.text = date
        }
    }
}

class ITHStoryTextViewHolder(itemView: View) : ITHStoryViewHolder(itemView), ITHStoryTextView {
    override fun setText(text: String) {
        with(itemView) {
            textView.text = text
        }
    }
}

class ITHStoryTagsViewHolder(itemView: View) : ITHStoryViewHolder(itemView), ITHStoryTagsView {

    override fun clearTags() {
        with(itemView) {
            tagsGroup.removeAllViews()
        }
    }

    override fun addTag(tag: String) {
        with(itemView) {
            tagsGroup.addView(Chip(itemView.context).apply {
                this.text = tag
            })
        }
    }

}