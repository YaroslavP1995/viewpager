package com.test.viewpagerkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.test.viewpagerkotlin.databinding.ActivityMainBinding
import com.test.viewpagerkotlin.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity(), IncrementFragmentClickListener {

    private lateinit var cardsPager: ViewPager
    private lateinit var binding: ActivityMainBinding
    private lateinit var testFragment: TestFragment
    private var viewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        initViewPagerAdapter()
        testFragment = TestFragment.newInstance(this, this, 0);
        setupMainViewPager(testFragment, true);
    }

    private fun setupMainViewPager(
        fragment: TestFragment,
        isIncrement: Boolean,
    ) {
        if (isIncrement) {
            viewPagerAdapter?.addFragment(fragment)
        } else {
            viewPagerAdapter?.removeFragment(fragment)
        }
        with(cardsPager) {
            adapter = viewPagerAdapter
            currentItem = viewPagerAdapter?.count ?: 0
        }
    }

    private fun initViewPagerAdapter() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        cardsPager = binding.myViewPager
    }

    override fun incrementClickListener(isIncrement: Boolean) {
        if (isIncrement) {
            setupMainViewPager(getTestFragment(), isIncrement)
        } else {
            setupMainViewPager(
                viewPagerAdapter?.getItem(cardsPager.currentItem) as TestFragment,
                isIncrement
            )
        }
    }

    private fun getTestFragment(): TestFragment {
        return viewPagerAdapter?.count?.let { TestFragment.newInstance(this, this, it) }!!
    }
}