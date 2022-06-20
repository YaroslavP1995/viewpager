package com.test.viewpagerkotlin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import com.test.viewpagerkotlin.databinding.FragmentLayoutBinding

const val ZERO = 0

class TestFragment(
    private val clickListener: IncrementFragmentClickListener,
    private val activity: Activity,
) : BaseFragment<FragmentLayoutBinding>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initOnClickListeners()
    }

    private fun initUi() {
        when {
            getCurrentItem() == ZERO -> {
                showBtn(false)
            }
            else -> {
                showBtn(true)
            }
        }
        binding.textIncrement.text = getCurrentItem().toString()
    }

    private fun showBtn(isVisible: Boolean) {
        binding.removeFragment.isVisible = isVisible
    }

    private fun initOnClickListeners() {
        with(binding) {
            removeFragment.setOnClickListener {
                incrementItem(false)
            }
            addFragment.setOnClickListener {
                incrementItem(true)
            }
            addNotification.setOnClickListener {
                createNotification(getCurrentItem())
            }
        }

    }

    private fun incrementItem(isIncrement: Boolean) {
        clickListener.incrementClickListener(isIncrement)
    }

    private fun createNotification(index: Int) {
        val builder = NotificationCompat.Builder(activity, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Notification")
            .setContentText("Notification $index")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(activity)) {
            notify(index, builder.build())
        }
    }

    private fun getCurrentItem(): Int {
        return arguments?.getInt(CURRENT_ITEM) ?: 0
    }

    companion object {

        private const val CURRENT_ITEM = "CURRENT_ITEM"

        fun newInstance(
            clickListener: IncrementFragmentClickListener,
            activity: Activity,
            currentItem: Int,
        ): TestFragment {
            val fragment = TestFragment(clickListener, activity)
            fragment.arguments = Bundle().also {
                it.putInt(CURRENT_ITEM, currentItem)
            }
            return fragment
        }
    }
}