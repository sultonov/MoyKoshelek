package ru.yandex.moykoshelek

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import ru.yandex.moykoshelek.fragments.MainFragment
import ru.yandex.moykoshelek.fragments.MenuFragment
import ru.yandex.moykoshelek.utils.FragmentCodes


class MainActivity : AppCompatActivity(){

    private var isMenuShowed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_hamburger)
        setActionBarTitle("Мой кошелёк")
        this.showFragment(FragmentCodes.MAIN_FRAGMENT, false)
    }

    override fun onBackPressed() {
        if (isMenuShowed) {
            showOrHideMenu()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_add -> return true
            android.R.id.home -> showOrHideMenu()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun showOrHideMenu() {
        if (isMenuShowed) {
            toolbar.setNavigationIcon(R.drawable.ic_hamburger)
            super.onBackPressed()
        }
        else {
            toolbar.setNavigationIcon(android.R.drawable.ic_lock_lock)
            showFragment(FragmentCodes.MENU_FRAGMENT, true)
        }
        isMenuShowed = !isMenuShowed
    }

    fun showFragment(fragmentCode: Int, addBackStack: Boolean) {
        this.hideKeyboard()
        if (isMenuShowed) showOrHideMenu()
        var transaction = supportFragmentManager.beginTransaction()
        transaction = when (fragmentCode) {
            FragmentCodes.ADD_TRANSACTION_FRAGMENT -> {
                transaction.add(R.id.frame_content, MainFragment())
            }
            FragmentCodes.MENU_FRAGMENT -> {
                transaction.add(R.id.frame_content, MenuFragment())
            }
            else -> {
                transaction.replace(R.id.frame_content, MainFragment())
            }
        }
        if (addBackStack)
            transaction = transaction.addToBackStack("fragment$fragmentCode")
        transaction.commit()
    }

    fun setActionBarTitle(title: String) {
        val s = SpannableString(title)
        if (s.indexOf(' ') != -1) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.toolbar_text_red)), 0, s.indexOf(' '), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.toolbar_text_black)), s.indexOf(' '), s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorAccent)), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        supportActionBar?.title = s
    }
}
