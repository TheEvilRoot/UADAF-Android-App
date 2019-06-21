package org.uadaf.app.about.impl

import org.uadaf.app.R
import org.uadaf.app.about.AboutProvider
import org.uadaf.app.about.data.Author
import org.uadaf.app.about.data.Link

class AboutProviderImpl(

) : AboutProvider {

    private val authors = listOf(
        Author(
            "TheEvilRoot", listOf(
                Link("VK", "https://ic_vk.com/pelmen_it", R.color.color_vk, R.drawable.ic_vk)
            )
        )
    )

    private val links = listOf(
        Link("Web", "http://52.48.142.75/", R.color.color_web, R.drawable.ic_settings)
    )

    override fun getAppIcon(): Int =
        R.drawable.ic_launcher_foreground

    override fun getAppName(): Int =
        R.string.app_name

    override fun getDescription(): Int =
        R.string.app_subtitle

    override fun getAuthorCount(): Int =
        authors.count()

    override fun getAuthor(position: Int): Author =
        authors[position]

    override fun getLinksCount(): Int =
        links.count()

    override fun getLink(position: Int): Link =
        links[position]
}