package ru.yandex.moykoshelek.activities

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import com.treebo.internetavailabilitychecker.InternetConnectivityListener
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.selector
import org.json.JSONObject
import ru.yandex.moykoshelek.R
import ru.yandex.moykoshelek.database.AppDatabase
import ru.yandex.moykoshelek.fragments.AddTransactionFragment
import ru.yandex.moykoshelek.fragments.AddWalletFragment
import ru.yandex.moykoshelek.fragments.MainFragment
import ru.yandex.moykoshelek.fragments.MenuFragment
import ru.yandex.moykoshelek.helpers.CurrencyPref
import ru.yandex.moykoshelek.hideKeyboard
import ru.yandex.moykoshelek.utils.DbWorkerThread
import ru.yandex.moykoshelek.utils.FragmentCodes


class MainActivity : AppCompatActivity(), InternetConnectivityListener {

    private var isMenuShowed = false
    var appDb: AppDatabase? = null
    lateinit var dbWorkerThread: DbWorkerThread
    val uiHandler = Handler()
    private lateinit var internetAvailabilityChecker: InternetAvailabilityChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_hamburger)
        appDb = AppDatabase.getInstance(this)
        onInternetConnectivityChanged(true)
        this.showFragment(FragmentCodes.MAIN_FRAGMENT, false)
    }

    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        if (isConnected) {
            async(UI) {
                bg {
                    getCurrencyFromInternet()
                }
            }
        }
    }

    private fun getCurrencyFromInternet() {
        AndroidNetworking.get("https://free.currencyconverterapi.com/api/v6/convert?q=USD_RUB&compact=y")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val currency: Float = response.getJSONObject("USD_RUB").getDouble("val").toFloat()
                        CurrencyPref(this@MainActivity).setCurrentConvert(currency)
                    }

                    override fun onError(error: ANError) {
                        Log.e("CurrencyError", error.errorBody)
                    }
                })
    }

    override fun onStart() {
        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()
        internetAvailabilityChecker = InternetAvailabilityChecker.getInstance()
        internetAvailabilityChecker.addInternetConnectivityListener(this)
        super.onStart()
    }

    override fun onStop() {
        dbWorkerThread.interrupt()
        internetAvailabilityChecker.removeInternetConnectivityChangeListener(this)
        super.onStop()
    }

    override fun onBackPressed() {
        if (isMenuShowed) {
            showOrHideMenu()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> showSelectAddDialog()
            android.R.id.home -> showOrHideMenu()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showSelectAddDialog() {
        val array = arrayOf("Счет", "Доход/Расход")
        selector("Выберите что добавить", array.toList()) { _, i ->
            run {
                when (i) {
                    0 -> showFragment(FragmentCodes.ADD_WALLET_FRAGMENT, true)
                    1 -> showFragment(FragmentCodes.ADD_TRANSACTION_FRAGMENT, true)
                }
            }
        }
    }

    fun showOrHideMenu() {
        if (isMenuShowed) {
            toolbar.setNavigationIcon(R.drawable.ic_hamburger)
            super.onBackPressed()
        } else {
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
                setActionBarTitle("Добавить кошелек")
                transaction.replace(R.id.frame_content, AddTransactionFragment())
            }
            FragmentCodes.ADD_WALLET_FRAGMENT -> {
                setActionBarTitle("Добавить кошелек")
                transaction.replace(R.id.frame_content, AddWalletFragment())
            }
            FragmentCodes.MENU_FRAGMENT -> {
                setActionBarTitle("Мой меню")
                transaction.add(R.id.frame_content, MenuFragment())
            }
            else -> {
                setActionBarTitle("Мой кошелёк")
                transaction.replace(R.id.frame_content, MainFragment())
            }
        }
        if (addBackStack)
            transaction = transaction.addToBackStack("fragment$fragmentCode")
        transaction.commit()
    }

    private fun setActionBarTitle(title: String) {
        val s = SpannableString(title)
        if (s.indexOf(' ') != -1) {
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.toolbar_text_red)), 0, s.indexOf(' '), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.toolbar_text_black)), s.indexOf(' '), s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else
            s.setSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorAccent)), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        supportActionBar?.title = s
    }
}
