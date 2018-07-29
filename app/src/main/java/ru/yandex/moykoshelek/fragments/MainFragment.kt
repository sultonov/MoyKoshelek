package ru.yandex.moykoshelek.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.yandex.moykoshelek.R
import ru.yandex.moykoshelek.adapters.CardsPagerAdapter
import ru.yandex.moykoshelek.helpers.ShadowTransformer
import ru.yandex.moykoshelek.models.CardItem

class MainFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var cardAdapter: CardsPagerAdapter
    private lateinit var cardShadowTransformer: ShadowTransformer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.cards_viewpager)
        tabLayout = view.findViewById(R.id.tab_dots) as TabLayout
        tabLayout.setupWithViewPager(viewPager, true)
        cardAdapter = CardsPagerAdapter()
        cardAdapter.addCardItem(CardItem("SBERBANK", "$ 120 000","1234 **** **** **12", "12/18"))
        cardAdapter.addCardItem(CardItem("SBERBANK", "$ 120 000","1234 **** **** **12", "12/18"))
        cardAdapter.addCardItem(CardItem("SBERBANK", "$ 120 000","1234 **** **** **12", "12/18"))
        cardAdapter.addCardItem(CardItem("SBERBANK", "$ 120 000","1234 **** **** **12", "12/18"))
        cardAdapter.addCardItem(CardItem("SBERBANK", "$ 120 000","1234 **** **** **12", "12/18"))
        cardShadowTransformer = ShadowTransformer(viewPager, cardAdapter)
        viewPager.adapter = cardAdapter
        viewPager.setPageTransformer(false, cardShadowTransformer)
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.setPadding(96, 0, 96, 0)
        viewPager.pageMargin = 48
        super.onViewCreated(view, savedInstanceState)
    }
}