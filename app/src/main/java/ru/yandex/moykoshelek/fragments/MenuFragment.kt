package ru.yandex.moykoshelek.fragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ru.yandex.moykoshelek.activities.MainActivity
import ru.yandex.moykoshelek.R
import ru.yandex.moykoshelek.utils.FragmentCodes

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<LinearLayout>(R.id.menu_home).setOnClickListener { (activity as MainActivity).showFragment(FragmentCodes.MAIN_FRAGMENT, false)}
        view.findViewById<LinearLayout>(R.id.menu_statistics).setOnClickListener { (activity as MainActivity).showFragment(FragmentCodes.MAIN_FRAGMENT, false)}
        view.findViewById<LinearLayout>(R.id.menu_settings).setOnClickListener { (activity as MainActivity).showFragment(FragmentCodes.MAIN_FRAGMENT, false)}
        view.findViewById<LinearLayout>(R.id.menu_about).setOnClickListener { (activity as MainActivity).showFragment(FragmentCodes.MAIN_FRAGMENT, false)}
        view.findViewById<ConstraintLayout>(R.id.menu_layout).setOnClickListener { (activity as MainActivity).showOrHideMenu()}
    }
}