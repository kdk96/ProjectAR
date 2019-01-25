package com.kdk96.quests.ui

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
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
    private val adapter by lazy { QuestsAdapter { presenter.onQuestClick(it) } }
    private val viewHandler = Handler()
    @Inject
    @InjectPresenter
    lateinit var presenter: QuestsPresenter
    private var snackbar: Snackbar? = null

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<QuestsComponent>().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun buildComponent() = DaggerQuestsComponent.builder()
            .questsDependencies(findComponentDependencies())
            .build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as HasDrawerToggle).setupDrawerToggle(toolbar)
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
        (activity as HasDrawerToggle).removeDrawerToggle()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearComponentsOnDestroy(QuestsComponent::class.java)
    }

    override fun onBackPressed() = presenter.onBackPressed()
}