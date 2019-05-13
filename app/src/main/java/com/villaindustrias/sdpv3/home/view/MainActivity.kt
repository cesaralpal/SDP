package com.villaindustrias.sdpv3.home.view

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.villaindustrias.sdpv3.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.polidea.rxandroidble2.RxBleClient
import com.villaindustrias.sdpv3.constants.App
import com.villaindustrias.sdpv3.eventos.ChangeFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe






class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var fragmentHome: TestFragment? = null
    var fragmentAlerts: AlertsFragment? = null
    var fragmentConnection: BluetoothFragment? = null
    var fragmentLocation: LocationFragment? = null
    var fragmentEnergy: EnergyFragment? = null
    var fragmentUpdateFirmware: FirmwareFragment? = null
    var fragmentMap: MapFragment? = null
    var fragmentMaintenance: MaintenanceFragment? = null
    var fragmentChart: GraphsFragment? = null

    val MY_PERMISSIONS_REQUEST_LOCATION = 99

    private var allTabs: TabLayout? = null

    companion object {
        lateinit var rxBleClient: RxBleClient
            private set
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        EventBus.getDefault().register(this)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        getAllWidgets()
        bindWidgetsWithAnEvent()
        setupTabLayout()
        getBluetoothAcces()
        setData()

    }
    private fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)

    private fun getBluetoothAcces() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
        }

        if (!mBluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, App.REQUEST_ENABLE_BT)
        }
    }



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
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
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        fragmentUpdateFirmware = FirmwareFragment()
        fragmentMaintenance = MaintenanceFragment()
        fragmentChart = GraphsFragment()

        when (item.itemId) {

            R.id.itm_navigation_18 ->{

                replaceFragment(fragmentUpdateFirmware!!)
                toolbar_title.text = getString(R.string.label_update_firmware)

            }
            R.id.itm_navigation_8 -> {
                replaceFragment(fragmentMaintenance!!)
                toolbar_title.text = getString(R.string.label_data_frigarator)
            }
            R.id.itm_navigation_9 -> {
                replaceFragment(fragmentChart!!)
                toolbar_title.text = getString(R.string.label_graph)
            }
//            R.id.nav_slideshow -> {
//
//            }
//            R.id.nav_manage -> {
//
//            }
//            R.id.nav_share -> {
//
//            }
//            R.id.nav_send -> {
//
//            }
        }


        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun setData(){

        packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) }?.also {
            Toast.makeText(this, "Bluetooth no soportado", Toast.LENGTH_SHORT).show()
            finish()
        }
      nav_view.getHeaderView(0).findViewById<ImageView>(R.id.imgBackArrow)
            .setOnClickListener {
               drawer_layout.closeDrawers()
            }
    }

    private fun getAllWidgets() {
         allTabs = findViewById<View>(R.id.tabLayout) as TabLayout

    }

    private fun setupTabLayout() {
        fragmentHome = TestFragment()
        fragmentAlerts = AlertsFragment()
        fragmentConnection = BluetoothFragment()
        fragmentLocation = LocationFragment()
        fragmentEnergy = EnergyFragment()

        allTabs?.addTab(allTabs!!.newTab().setIcon(R.drawable.ic_home).setContentDescription(getString(R.string.label_tab_alert)).setText(resources.getText(R.string.label_tab_home)), true)
        allTabs?.addTab(allTabs!!.newTab().setText(resources.getText(R.string.label_tab_alert)).setIcon(R.drawable.ic_alert))
        allTabs?.addTab(allTabs!!.newTab().setText(resources.getText(R.string.label_tab_conected)).setIcon(R.drawable.ic_conected))
        allTabs?.addTab(allTabs!!.newTab().setText(resources.getText(R.string.label_tab_location)).setIcon(R.drawable.ic_ubication))
        allTabs?.addTab(allTabs!!.newTab().setText(resources.getText(R.string.label_tab_energy)).setIcon(R.drawable.ic_quality),false)

    }

    private fun bindWidgetsWithAnEvent() {
        allTabs?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {

                setCurrentTabFragment(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
        )

    }

    private fun setCurrentTabFragment(tabPosition: Int) {
            when (tabPosition) {
                0 -> {replaceFragment(fragmentHome!!)
                    toolbar_title.text = getString(R.string.label_title_maintenance)
                }
                1 -> {replaceFragment(fragmentAlerts!!)
                    toolbar_title.text = getString(R.string.label_title_alerts)

                }
                2 -> {
                    replaceFragment(fragmentConnection!!)
                    toolbar_title.text = getString(R.string.label_title_conected)

                }
                3 -> {
                    replaceFragment(fragmentLocation!!)
                    toolbar_title.text = getString(R.string.label_title_location)

                }
                4 -> {replaceFragment(fragmentEnergy!!)
                 toolbar_title.text = getString(R.string.label_title_energy)
                }
            }

    }

    fun replaceFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.generalContainer, fragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ChangeFragment) {
        when(event.Fragment){
            1 -> {

               setCurrentTabFragment(4)
                allTabs?.setScrollPosition(4,0f,true)
                allTabs?.getTabAt(4)?.select()
            }

            2 -> {
               fragmentMap = MapFragment()
                replaceFragment(fragmentMap!!)
            }

            3 -> {
                setCurrentTabFragment(2)
                allTabs?.setScrollPosition(2,0f,true)

            }
            else ->{

            }
        }
    }


}

