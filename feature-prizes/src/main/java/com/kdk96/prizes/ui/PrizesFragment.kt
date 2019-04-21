package com.kdk96.prizes.ui

import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseFragment
import com.kdk96.common.ui.HasDrawerToggle
import com.kdk96.prizes.R
import com.kdk96.prizes.di.DaggerPrizesComponent
import com.kdk96.prizes.di.PrizesComponent
import com.kdk96.prizes.domain.Prize
import com.kdk96.prizes.presentation.PrizesPresenter
import com.kdk96.prizes.presentation.PrizesView
import kotlinx.android.synthetic.main.fragment_prizes.*
import javax.inject.Inject

class PrizesFragment : BaseFragment(), PrizesView {
    override val layoutRes: Int = R.layout.fragment_prizes

    init {
        componentBuilder = {
            DaggerPrizesComponent.builder().prizesDependencies(findComponentDependencies()).build()
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: PrizesPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val adapter by lazy { PrizesAdapter() }
    private val viewHandler = Handler()
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<PrizesComponent>().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (parentFragment as? HasDrawerToggle)?.setupDrawerToggle(toolbar)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        swipeRefreshLayout.setOnRefreshListener(presenter::onRefresh)
        with(prizesRV) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@PrizesFragment.adapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun showRefreshProgress(show: Boolean) {
        viewHandler.post { swipeRefreshLayout.isRefreshing = show }
    }

    override fun showPrizes(prizes: List<Prize>) {
        viewHandler.post { adapter.submitList(prizes) }
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