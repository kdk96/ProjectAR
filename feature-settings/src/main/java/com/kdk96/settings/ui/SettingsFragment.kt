package com.kdk96.settings.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseFragment
import com.kdk96.common.ui.HasDrawerToggle
import com.kdk96.glide.GlideApp
import com.kdk96.settings.PermissionHelper
import com.kdk96.settings.R
import com.kdk96.settings.data.storage.AvatarFileProcessor
import com.kdk96.settings.di.DaggerSettingsComponent
import com.kdk96.settings.di.SettingsComponent
import com.kdk96.settings.presentation.SettingsPresenter
import com.kdk96.settings.presentation.SettingsView
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsView {
    companion object {
        const val TAKE_PHOTO_REQUEST = 1
        const val PICK_IMAGE_REQUEST = 2
        const val TAKE_PHOTO_WRITE_EXTERNAL_STORAGE_REQUEST = 101
        const val PICK_IMAGE_WRITE_EXTERNAL_STORAGE_REQUEST = 102
    }

    override val layoutRes = R.layout.fragment_settings
    @Inject
    lateinit var avatarFileProcessor: AvatarFileProcessor
    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter
    @Inject
    lateinit var permissionHelper: PermissionHelper

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<SettingsComponent>().inject(this)
        super.onCreate(savedInstanceState)
        permissionHelper.listener = object : PermissionHelper.RequestPermissionsResultListener {
            override fun onPermissionGranted(requestCode: Int) {
                when (requestCode) {
                    TAKE_PHOTO_WRITE_EXTERNAL_STORAGE_REQUEST -> takePhoto()
                    PICK_IMAGE_WRITE_EXTERNAL_STORAGE_REQUEST -> pickImageFromGallery()
                }
            }
        }
    }

    override fun buildComponent() = DaggerSettingsComponent.builder()
            .settingsDependencies(findComponentDependencies())
            .build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as HasDrawerToggle).setupDrawerToggle(toolbar)
        avatarIV.setOnClickListener { presenter.onAvatarClick() }
        signOutButton.setOnClickListener { presenter.onSignOutClick() }
    }

    override fun updateAccountInfo(name: String, email: String, avatarUrl: String?) {
        nameTV.text = name
        emailTV.text = email
        GlideApp.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.avatar_placeholder_gray)
                .circleCrop()
                .into(avatarIV)
    }

    override fun showImageSourceDialog(show: Boolean) {
        if (show) AlertDialog.Builder(context!!)
                .setItems(R.array.image_source_array) { _, which ->
                    when (which) {
                        0 -> presenter.onTakePhotoClick()
                        1 -> presenter.onPickFromGalleryClick()
                    }
                }.setOnDismissListener { presenter.onAvatarDismiss() }
                .create().show()
    }

    override fun openCamera() {
        if (permissionHelper.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        TAKE_PHOTO_WRITE_EXTERNAL_STORAGE_REQUEST,
                        R.string.write_external_storage_permission_rationale_dialog_message)) {
            takePhoto()
        }
    }

    private fun takePhoto() {
        val takePhotoIntent = avatarFileProcessor.getTakePhotoIntent()
        takePhotoIntent?.let { startActivityForResult(it, TAKE_PHOTO_REQUEST) }
                ?: presenter.onCameraOpenFailure()
    }

    override fun openGallery() {
        if (permissionHelper.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PICK_IMAGE_WRITE_EXTERNAL_STORAGE_REQUEST,
                        R.string.write_external_storage_permission_rationale_dialog_message)) {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() = startActivityForResult(
            Intent(Intent.ACTION_PICK).apply { type = "image/*" },
            PICK_IMAGE_REQUEST
    )

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
            permissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults,
                    mapOf(
                            TAKE_PHOTO_WRITE_EXTERNAL_STORAGE_REQUEST to R.string.write_external_storage_permission_rationale_dialog_message,
                            PICK_IMAGE_WRITE_EXTERNAL_STORAGE_REQUEST to R.string.write_external_storage_permission_rationale_dialog_message
                    )
            )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            TAKE_PHOTO_REQUEST -> presenter.onAvatarSelected(avatarFileProcessor.imagePath!!)
            PICK_IMAGE_REQUEST -> presenter.onAvatarSelected(avatarFileProcessor.getImagePathFromUri(data!!.data!!))
        }
        if (resultCode == Activity.RESULT_CANCELED && requestCode == TAKE_PHOTO_REQUEST)
            avatarFileProcessor.deletePhotoFile()
    }

    override fun showProgress(show: Boolean) = showProgressDialog(show)

    override fun showError(resId: Int) = Snackbar.make(view!!, resId, Snackbar.LENGTH_SHORT).show()

    override fun onDestroyView() {
        (activity as HasDrawerToggle).removeDrawerToggle()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionHelper.listener = null
        clearComponentOnDestroy()
    }

    override fun onBackPressed() = presenter.onBackPressed()
}