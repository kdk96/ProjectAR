package com.kdk96.quests.ui

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseFragment
import com.kdk96.common.ui.HasDrawerToggle
import com.kdk96.quests.R
import com.kdk96.quests.di.DaggerQuestsComponent
import com.kdk96.quests.di.QuestsComponent
import com.kdk96.quests.domain.entity.QuestShortInfo
import com.kdk96.quests.presenatation.QuestsPresenter
import com.kdk96.quests.presenatation.QuestsView
import kotlinx.android.synthetic.main.fragment_quests.*
import javax.inject.Inject

class QuestsFragment : BaseFragment(), QuestsView {
    override val layoutRes = R.layout.fragment_quests

    init {
        componentBuilder = {
            DaggerQuestsComponent.builder().questsDependencies(findComponentDependencies()).build()
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: QuestsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val adapter by lazy { QuestsAdapter { presenter.onQuestClick(it) } }
    private val viewHandler = Handler()
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<QuestsComponent>().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (parentFragment as? HasDrawerToggle)?.setupDrawerToggle(toolbar)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        swipeRefreshLayout.setOnRefreshListener(presenter::onRefresh)
        with(questsRV) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@QuestsFragment.adapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun showRefreshProgress(show: Boolean) {
        viewHandler.post { swipeRefreshLayout.isRefreshing = show }
    }

    override fun showQuests(quests: List<QuestShortInfo>) {
        viewHandler.post { adapter.submitList(quests) }
    }

    override fun showError(resId: Int) {
        snackbar = Snackbar.make(view!!, resId, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { presenter.onRefresh() }
        snackbar?.show()
    }

    override fun onDestroyView() {
        snackbar?.dismiss()
        snackbar = null
        (parentFragment as? HasDrawerToggle)?.removeDrawerToggle()
        super.onDestroyView()
    }

    override fun onBackPressed() = presenter.onBackPressed()
}