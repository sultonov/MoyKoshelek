package ru.yandex.moykoshelek.adapters

import android.view.ViewGroup
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import ru.yandex.moykoshelek.helpers.CardAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.TextView
import ru.yandex.moykoshelek.R
import ru.yandex.moykoshelek.models.CardItem


class CardsPagerAdapter : PagerAdapter(), CardAdapter {
    private val views: MutableList<CardView?>
    private val data: MutableList<CardItem>
    override var baseElevation: Float = 0.toFloat()

    init {
        data = ArrayList()
        views = ArrayList()
    }

    fun addCardItem(item: CardItem) {
        views.add(null)
        data.add(item)
    }

    override fun getMaxElevationFactor(): Int {
        return 8
    }

    override fun getCardViewAt(position: Int): CardView? {
        return views[position]
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
                .inflate(R.layout.card_view, container, false)
        container.addView(view)
        bind(data[position], view)
        val cardView = view.findViewById(R.id.card_layout) as CardView

        if (baseElevation == 0f) {
            baseElevation = cardView.cardElevation
        }

        cardView.maxCardElevation = baseElevation * getMaxElevationFactor()
        views[position] = cardView
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
        views[position] = null
    }

    private fun bind(item: CardItem, view: View) {
        val cardName = view.findViewById(R.id.card_name) as TextView
        val cardNumber = view.findViewById(R.id.card_number) as TextView
        val cardBalance = view.findViewById(R.id.card_balance) as TextView
        val cardDate = view.findViewById(R.id.card_date) as TextView
        cardName.text = item.name
        cardNumber.text = item.number
        cardBalance.text = item.balance
        cardDate.text = item.date
    }

}