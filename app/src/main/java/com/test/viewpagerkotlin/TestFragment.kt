package com.test.viewpagerkotlin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.viewpagerkotlin.databinding.FragmentLayoutBinding

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class TestFragment(
    private val clickListener: IncrementFragmentClickListener,
    private val activity: Activity
) : Fragment() {

    private lateinit var viewBinding: FragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLayoutBinding.bind(view)
        initUi()
        initOnClickListeners()
    }

    private fun initUi() {
        with(viewBinding) {
            when {
                getCurrentItem() == 0 -> {
                    removeFragment.visibility = View.GONE
                }
                else -> {
                    removeFragment.visibility = View.VISIBLE
                }
            }
            textIncrement.text = getCurrentItem().toString()
        }
    }

    private fun initOnClickListeners() {
        with(viewBinding) {
            removeFragment.setOnClickListener {
                clickListener.incrementClickListener(false)
            }
            addFragment.setOnClickListener {
                clickListener.incrementClickListener(true)
            }
            addNotification.setOnClickListener {
                createNotification(getCurrentItem())
            }
        }

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