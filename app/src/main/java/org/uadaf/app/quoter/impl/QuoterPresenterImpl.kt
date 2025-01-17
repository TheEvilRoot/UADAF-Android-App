package org.uadaf.app.quoter.impl

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.uadaf.app.internal.NoConnectivityException
import org.uadaf.app.internal.UADAFServiceException
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.quoter.QuoteRowView
import org.uadaf.app.quoter.QuoterPresenter
import org.uadaf.app.quoter.QuoterRepository
import org.uadaf.app.quoter.QuoterView
import java.util.*

class QuoterPresenterImpl(
    private val notificationCenter: NotificationCenter,
    private val quoterRepository: QuoterRepository,
    private val view: QuoterView,
    private val exceptionDispatcher: ExceptionDispatcher
) : QuoterPresenter {

    private val compositeDisposable = CompositeDisposable()

    private var repoName: String = "uadaf"

    override fun getQuotesCount(): Int =
        quoterRepository.getQuotesCount()


    override fun getSectionName(position: Int): String =
        quoterRepository.sectionNameBySorting(position)

    override fun bindQuoteView(position: Int, view: QuoteRowView) {
        val quote = quoterRepository.getQuote(position)

        view.setID(quote.id)
        view.setAdder(quote.adder)
        view.setAuthor(quote.authors.joinToString(separator = ", "))
        view.setText(quote.content)

        if (quote.editedAt >= 0 && quote.editedAt > 0) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = quote.editedAt
            calendar.timeZone = TimeZone.getTimeZone("Europe/Moscow")
            val editedAt = "%02d.%02d.%04d %02d:%02d"
                .format(
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE)
                )
            view.setEdited(quote.editedBy, editedAt)
        } else {
            view.setNotEdited()
        }
    }

    override fun loadQuotes(force: Boolean) {
        if (!force && quoterRepository.getQuotesCount() > 0) {
            view.updateQuotesView()
            view.updateRepo(repoName)
        } else {
            view.displayLoading()
            compositeDisposable.add(Completable.fromCallable {
                quoterRepository.fetchQuotes(repoName)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                view.stopLoading()
                view.updateRepo(repoName)
                if (quoterRepository.getQuotesCount() > 0) {
                    view.updateQuotesView()
                } else {
                    view.displayNoQuotes()
                }
            }, {
                view.stopLoading()
                view.updateRepo(repoName)
                when (it) {
                    is NoConnectivityException -> view.displayNoInternet()
                    is UADAFServiceException -> view.displayServiceUnavailable()
                    else -> exceptionDispatcher.dispatch(it, view)
                }
                it.printStackTrace()
            }))
        }
    }

    override fun updateRepo(repoNameIn: String) {
        if (repoName == repoNameIn) return
        compositeDisposable.add(Completable.fromCallable {
            quoterRepository.checkRepo(repoNameIn)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            repoName = repoNameIn
            view.updateRepo(repoName)
            quoterRepository.clearRepo()
            loadQuotes(true)
        }) {
            view.repoError(it.message ?: "Repository $repoNameIn not found, sorry")
        })
    }

    override fun repoName(): String =
        repoName

}