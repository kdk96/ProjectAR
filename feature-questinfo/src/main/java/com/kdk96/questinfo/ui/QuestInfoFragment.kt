package com.kdk96.questinfo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseFragment
import com.kdk96.glide.GlideApp
import com.kdk96.permission.PermissionHelper
import com.kdk96.permission.showOnNeverAskAgainSnackbar
import com.kdk96.questinfo.R
import com.kdk96.questinfo.di.DaggerQuestInfoComponent
import com.kdk96.questinfo.di.QuestInfoComponent
import com.kdk96.questinfo.domain.entity.Prize
import com.kdk96.questinfo.domain.entity.QuestInfo
import com.kdk96.questinfo.presentation.ButtonState
import com.kdk96.questinfo.presentation.QuestInfoPresenter
import com.kdk96.questinfo.presentation.QuestInfoView
import kotlinx.android.synthetic.main.content_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_quest_info.*
import kotlinx.android.synthetic.main.layout_network_error.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuestInfoFragment : BaseFragment(), QuestInfoView, CancelParticipationDialog.CancelConfirmedListener {
    companion object {
        private const val ARG_QUEST_ID = "quest id argument"
        fun newInstance(id: String) = QuestInfoFragment().apply {
            arguments = Bundle().apply { putString(ARG_QUEST_ID, id) }
        }

        const val ACCESS_FINE_LOCATION_REQUEST = 101
    }

    override val layoutRes = R.layout.fragment_quest_info

    @Inject
    @InjectPresenter
    lateinit var presenter: QuestInfoPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private var map: GoogleMap? = null
    private val permissionHelper = PermissionHelper()

    private val onParticipateClickListener: (view: View) -> Unit = { presenter.onParticipateClick() }
    private val onStartClickListener: (view: View) -> Unit = { presenter.onStartClick() }
    private val onWaitClickListener: (view: View) -> Unit = { presenter.onRemainingTimeClick() }

    private val waitButtonString by lazy { getString(R.string.quest_will_start_in) }
    private val timeFormatter by lazy { DateTimeFormatter.ofPattern("HH:mm, d MMM") }

    private val isLocationPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        componentBuilder = {
            DaggerQuestInfoComponent.builder()
                .questId(arguments!!.getString(ARG_QUEST_ID)!!)
                .questInfoDependencies(findComponentDependencies())
                .build()
        }
        getComponent<QuestInfoComponent>().inject(this)
        super.onCreate(savedInstanceState)
        permissionHelper.listener = object : PermissionHelper.RequestPermissionsResultListener {
            override fun onPermissionGranted(requestCode: Int) = when (requestCode) {
                ACCESS_FINE_LOCATION_REQUEST -> enableMyLocation()
                else -> Unit
            }

            override fun onNeverAskAgain(requestCode: Int, permissionDeniedMessageId: Int) {
                showOnNeverAskAgainSnackbar(permissionDeniedMessageId)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retryButton.setOnClickListener { presenter.onRetryClick() }
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            this.map = it
            it.setPadding(0, 0, 0, BottomSheetBehavior.from(bottomSheet).peekHeight)
            presenter.onMapReady()
            if (isLocationPermissionGranted) {
                enableMyLocation()
            }
        }
    }

    override fun showUserLocation() = permissionHelper.doActionWithCheckPermissions(
        this,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        ACCESS_FINE_LOCATION_REQUEST,
        R.string.access_fine_location_permission_rationale_dialog_message
    )

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        map?.isMyLocationEnabled = true
    }

    override fun showInfo(questInfo: QuestInfo) {
        showContentBottomSheet()
        toolbar.title = questInfo.title
        questDescriptionTV.text = questInfo.description
        GlideApp.with(this)
            .load(questInfo.companyLogoUrl)
            .into(companyLogoIV)
        companyNameTV.text = questInfo.companyName
        val startTime = Instant.ofEpochMilli(questInfo.startTime)
            .atZone(ZoneId.of("UTC"))
            .withZoneSameInstant(ZoneId.systemDefault())
        timeTV.text = startTime.format(timeFormatter)
        prizesTV.text = prizesToString(questInfo.prizes)
    }

    private fun showContentBottomSheet() {
        layoutNetworkError.visibility = View.GONE
        contentBottomSheet.visibility = View.VISIBLE
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun prizesToString(prizes: List<Prize>): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendln(getString(R.string.prizes))
        val place = getString(R.string.place)
        (0 until prizes.lastIndex).forEach {
            val prize = prizes[it]
            stringBuilder.appendln("${prize.place} $place: ${prize.description}")
        }
        stringBuilder.append("${getString(R.string.others)}: ${prizes.last().description}")
        return stringBuilder.toString()
    }

    override fun showNetworkError() {
        layoutNetworkError.visibility = View.VISIBLE
        contentBottomSheet.visibility = View.GONE
    }

    override fun showStartPoint(latLngPair: Pair<Double, Double>) {
        val latLng = LatLng(latLngPair.first, latLngPair.second)
        val marker = map?.addMarker(MarkerOptions().position(latLng).title(getString(R.string.start_point)))
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        map?.animateCamera(cameraUpdate, object : GoogleMap.CancelableCallback {
            override fun onFinish() {
                marker?.showInfoWindow()
            }

            override fun onCancel() = Unit
        })
    }

    override fun changeButtonState(state: ButtonState) {
        when (state) {
            ButtonState.PARTICIPATE -> {
                participateButton.setText(R.string.participate_for_free)
                participateButton.setOnClickListener(onParticipateClickListener)
            }
            ButtonState.START -> {
                participateButton.setText(R.string.start_the_quest)
                participateButton.setOnClickListener(onStartClickListener)
            }
            ButtonState.WAIT -> {
                participateButton.setOnClickListener(onWaitClickListener)
            }
        }
        participateButton.isEnabled = true
    }

    override fun showRemainingTime(remainingTime: Long) {
        participateButton.text = getRemainingTimeString(remainingTime)
    }

    private fun getRemainingTimeString(remainingTime: Long): String {
        val timeString: String
        val days = TimeUnit.MILLISECONDS.toDays(remainingTime)
        if (days > 0) {
            timeString = resources.getQuantityString(R.plurals.days, days.toInt(), days)
        } else {
            val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
            var time = remainingTime - TimeUnit.HOURS.toMillis(hours)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
            time -= TimeUnit.MINUTES.toMillis(minutes)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(time)
            timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
        return "$waitButtonString $timeString"
    }

    override fun showCancelConfirmationDialog() =
        CancelParticipationDialog.newInstance(this)
            .show(fragmentManager, "cancel dialog")

    override fun onCancelConfirmed() = presenter.onCancelConfirmed()

    override fun showError(resId: Int) {
        Snackbar.make(view!!, resId, Snackbar.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        if (isLocationPermissionGranted) {
            enableMyLocation()
        }
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        permissionHelper.onRequestPermissionsResult(
            this, requestCode, permissions, grantResults,
            mapOf(ACCESS_FINE_LOCATION_REQUEST to R.string.access_fine_location_permission_rationale_dialog_message)
        )

    override fun onBackPressed() = presenter.onBackPressed()

    override fun onDestroyView() {
        presenter.onMapDestroy()
        mapView.onDestroy()
        super.onDestroyView()
    }
}