package dev.mem.rocket.sanya.Volume

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.mem.rocket.sanya.MainActivity
import dev.mem.rocket.sanya.Noraml_Mode
import dev.mem.rocket.sanya.PPP.PowerSaving_popup
import dev.mem.rocket.sanya.R
import dev.mem.rocket.sanya.Ultra_PopUp
import dev.mem.rocket.sanya.utils.PreferencesProvider
import kotlinx.android.synthetic.main.battery_saver.*

import me.itangqi.waveloadingview.WaveLoadingView

/**
 * Created by intag pc on 2/12/2017.
 */

class BatterySaverFrag : Fragment() {


    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

            tvLevel.text = "$level%"


            if (level <= 5) {


                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 0.toString() + ""
                    minutesmain.text = 15.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 2.toString() + ""
                    minutesmain.text = 25.toString() + ""
                }
            }
            if (level > 5 && level <= 10) {

                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 0.toString() + ""
                    minutesmain.text = 30.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 3.toString() + ""
                    minutesmain.text = 5.toString() + ""
                }
            }
            if (level > 10 && level <= 15) {

                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 0.toString() + ""
                    minutesmain.text = 45.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 3.toString() + ""
                    minutesmain.text = 50.toString() + ""
                }
            }
            if (level > 15 && level <= 25) {

                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 1.toString() + ""
                    minutesmain.text = 30.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 4.toString() + ""
                    minutesmain.text = 45.toString() + ""
                }
            }
            if (level > 25 && level <= 35) {

                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 2.toString() + ""
                    minutesmain.text = 20.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 6.toString() + ""
                    minutesmain.text = 2.toString() + ""
                }
            }
            if (level > 35 && level <= 50) {

                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 5.toString() + ""
                    minutesmain.text = 20.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 9.toString() + ""
                    minutesmain.text = 20.toString() + ""
                }
            }
            if (level > 50 && level <= 65) {
                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 7.toString() + ""
                    minutesmain.text = 30.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 11.toString() + ""
                    minutesmain.text = 1.toString() + ""
                }
            }
            if (level > 65 && level <= 75) {
                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 9.toString() + ""
                    minutesmain.text = 10.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 14.toString() + ""
                    minutesmain.text = 25.toString() + ""
                }
            }
            if (level > 75 && level <= 85) {

                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 14.toString() + ""
                    minutesmain.text = 15.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 17.toString() + ""
                    minutesmain.text = 10.toString() + ""
                }
            }
            if (level > 85 && level <= 100) {
                if (PreferencesProvider.getInstance().getString("mode", "0") == "0") {
                    hourmain.text = 20.toString() + ""
                    minutesmain.text = 45.toString() + ""
                }

                if (PreferencesProvider.getInstance().getString("mode", "0") == "1") {
                    hourmain.text = 30.toString() + ""
                    minutesmain.text = 0.toString() + ""
                }
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val battery_saver = inflater.inflate(R.layout.battery_saver, container, false)

        return battery_saver

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {

            imageView3.setOnClickListener {
                val i = Intent(activity, Noraml_Mode::class.java)
                startActivity(i)
            }

            imageView6.setOnClickListener {
                val i = Intent(activity, PowerSaving_popup::class.java)
                startActivity(i)
            }


            imageView7.setOnClickListener {
                val i = Intent(activity, Ultra_PopUp::class.java)
                startActivity(i)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onResume() {
        super.onResume()
        //        MainActivity.name.setText("Battery Saver");
        activity!!.registerReceiver(this.mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        try {
            activity!!.unregisterReceiver(mBatInfoReceiver)
        } catch (e: Exception) {

        }

    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            MainActivity.setInfo(R.string.battery_saver)

        } else {

        }
    }


}
