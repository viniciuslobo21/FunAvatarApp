package com.lobo.userlist.view.avatars.register

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.lobo.userlist.R
import com.lobo.userlist.databinding.AvatarRegisterActivityBinding
import com.lobo.userlist.domain.Avatar
import com.lobo.userlist.domain.UserModel
import com.lobo.userlist.view.avatars.dialog.AvatarBottomSheetFragment
import com.lobo.userlist.view.avatars.dialog.adapter.AvatarAdapter
import com.lobo.userlist.view.avatars.main.MainActivity.Companion.USER_INDEX_LIST
import com.lobo.userlist.view.avatars.main.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AvatarRegisterActivity : AppCompatActivity(), AvatarAdapter.AvatarListener {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: AvatarRegisterActivityBinding
    private var currentUser: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AvatarRegisterActivityBinding.inflate(layoutInflater)
        setupAnimation()
        setContentView(binding.constraint)
        addObservers()
        setupToolbar()
        setupScreen()
        setClickListeners()
    }

    override fun avatarClicked(avatar: Avatar) {
        viewModel.drawableSelected(avatar.drawable)
        hideTapLabel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupAnimation() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding.constraint.transitionName =
            TRANSITION_NAME
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildContainerTransform()
        window.sharedElementReturnTransition = buildContainerTransform()
    }

    private fun setupScreen() {
        getScreenArguments()
        if (inEditMode()) {
            currentUser?.let { getUserInfo(it) }
            hideTapLabel()
        } else {
            configureUiToNew()
        }
    }

    private fun hideTapLabel() {
        binding.tapToSet.visibility = View.GONE
        binding.avatarImage.visibility = View.VISIBLE
    }

    private fun configureUiToNew() {
        binding.tapToSet.visibility = View.VISIBLE
        binding.avatarImage.visibility = View.INVISIBLE
    }

    private fun addObservers() {
        viewModel.userInfo.observe(this, Observer {
            loadInformationUser(it)
        })

        viewModel.userInserted.observe(this, Observer {
            goBackWithResult()
        })

        viewModel.currentUser.observe(this, Observer {
            loadInformationUser(it)
        })

        viewModel.usersUpdated.observe(this, Observer {
            goBackWithResult()
        })
    }

    private fun goBackWithResult() {
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }

    private fun inEditMode() = currentUser != null

    private fun getScreenArguments() {
        val intent = intent
        intent.getStringExtra(USER_INDEX_LIST)?.let {
            currentUser = it.toInt()
        }
    }

    private fun getUserInfo(indexUser: Int) {
        viewModel.getInfoUser(indexUser)
    }

    private fun loadInformationUser(user: UserModel) {
        binding.userName.setText(user.name)
        binding.userBio.setText(user.bio)
        binding.avatarImage.setImageResource(user.avatar.drawable)
    }

    private fun setClickListeners() {
        binding.tapToSet.setOnClickListener {
            val bottomDialogFragment = AvatarBottomSheetFragment.newInstance()
            bottomDialogFragment.show(supportFragmentManager, TAG)
        }

        binding.avatarImage.setOnClickListener {
            val bottomDialogFragment = AvatarBottomSheetFragment.newInstance()
            bottomDialogFragment.show(supportFragmentManager, TAG)
        }

        binding.fabSubmit.setOnClickListener {
            if (!inEditMode()) {
                saveUser()
            } else {
                updateUser()
            }
        }
    }

    private fun saveUser() {
        viewModel.saveUser(
            name = binding.userName.text.toString(),
            bio = binding.userBio.text.toString(),
            avatar = viewModel.drawable
        )
    }

    private fun updateUser() {
        currentUser?.let {
            viewModel.updateUser(
                currentUser = it,
                name = binding.userName.text.toString(),
                bio = binding.userBio.text.toString(),
                avatar = getDrawableAvatar()
            )
        }
    }

    private fun getDrawableAvatar(): Int {
        return if (viewModel.drawable != 0) {
            viewModel.drawable
        } else {
            viewModel.userInfo.value?.avatar?.drawable ?: 0
        }
    }

    private fun buildContainerTransform() =
        MaterialContainerTransform().apply {
            addTarget(binding.constraint)
            setAllContainerColors(
                MaterialColors.getColor(
                    binding.root,
                    R.attr.colorSurface
                )
            )
            pathMotion = MaterialArcMotion()
            duration =
                DURATION_TRANSITION
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN
        }

    companion object {
        private const val TRANSITION_NAME = "shared_element_end_root"
        private const val DURATION_TRANSITION = 500L
        private const val TAG = "AvatarBottomDialogFragment"
    }
}