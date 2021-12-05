package com.lobo.userlist.view.avatars.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.lobo.userlist.R
import com.lobo.userlist.core.helpers.Resources.resourceHelper
import com.lobo.userlist.databinding.ActivityMainBinding
import com.lobo.userlist.view.avatars.main.adapter.UsersAdapter
import com.lobo.userlist.view.avatars.register.AvatarRegisterActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), UsersAdapter.Callback {

    private val viewModel: MainViewModel by viewModel()
    lateinit var adapter: UsersAdapter

    private lateinit var binding: ActivityMainBinding
    val REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        addObservers()
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fetchUsersList()
        setupAdapter()
        setClickListeners()
    }

    private fun setupAdapter() {
        adapter = UsersAdapter(
            context = resourceHelper.getContext(),
            callback = this
        )
        binding.rvUsers.adapter = adapter
    }

    private fun fetchUsersList() {
        viewModel.fetchUsers()
    }

    private fun addObservers() {
        viewModel.users.observe(this, Observer {
            if (it.isEmpty()) {
                noAvatarRegisteredToast()
            } else {
                adapter.setData(it)
            }
        })

        viewModel.userAfterDelete.observe(this, Observer {
            if (it.isEmpty()) {
                noAvatarRegisteredToast()
            }
            adapter.setData(it)
        })
    }

    private fun setClickListeners() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AvatarRegisterActivity::class.java)
            startActivityWithAnimation(intent)
        }
    }

    private fun startActivityWithAnimation(intent: Intent) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@MainActivity, binding.fabAdd, "shared_element_end_root"
        )
        startActivityForResult(intent, REQUEST_CODE, options.toBundle())
    }

    private fun noAvatarRegisteredToast() =
        Toast.makeText(
            applicationContext, getString(R.string.no_avatare_registered_message), Toast.LENGTH_LONG
        ).show()

    private fun avatarRegisteredToast() =
        Toast.makeText(
            applicationContext,
            getString(R.string.avatar_successfully_registered_message),
            Toast.LENGTH_LONG
        ).show()

    private fun avatarUpdatedToast() =
        Toast.makeText(
            applicationContext,
            getString(R.string.avatar_successfully_updated_message),
            Toast.LENGTH_LONG
        ).show()

    companion object {
        const val USER_INDEX_LIST = "USER_INDEX_LIST"
    }

    override fun onItemClick(userIndex: Int) {
        val intent = Intent(this@MainActivity, AvatarRegisterActivity::class.java)
        intent.putExtra(USER_INDEX_LIST, userIndex.toString())
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onRemoveClick(userIndex: Int) {
        viewModel.removeUser(indexUser = userIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.fetchUsers()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                true
            }
        }
    }
}