package dev.mem.rocket.sanya.AAA

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.animation.AccelerateInterpolator
import com.google.android.gms.ads.AdRequest

import dev.mem.rocket.sanya.BCJ.BatterySaver_Black
import dev.mem.rocket.sanya.PPP.PowerSaving_Complition
import dev.mem.rocket.sanya.R

import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import dev.mem.rocket.sanya.SubscriptionProvider
import kotlinx.android.synthetic.main.applying_ultra.*
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.phone_booster.dynamicArcView2
import kotlinx.android.synthetic.main.powersaving_completion.abnb_1
import kotlinx.android.synthetic.main.powersaving_completion.abnb_2
import kotlinx.android.synthetic.main.powersaving_completion.abnb_3
import kotlinx.android.synthetic.main.powersaving_completion.abnb_4

/**
 * Created by intag pc on 2/21/2017.
 */

class Applying_Ultra : Activity() {


    //// activate ultra powersaving mode by closing system services

    internal var check = 0
//    private var mAdView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.applying_ultra)

        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }

        setAnim()


        dynamicArcView2.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
            .setRange(0f, 100f, 0f)
            .setInterpolator(AccelerateInterpolator())
            .build())

        dynamicArcView2.addSeries(SeriesItem.Builder(Color.parseColor("#00ca71"))
            .setRange(0f, 100f, 100f)
            .setInitialVisibility(false)
            .setLineWidth(32f)
            .build())

        val seriesItem2 = SeriesItem.Builder(Color.parseColor("#00ca71"))
            .setRange(0f, 100f, 0f)
            .setLineWidth(32f)
            .build()

        val series1Index2 = dynamicArcView2.addSeries(seriesItem2)

        seriesItem2.addArcSeriesItemListener(object : SeriesItem.SeriesItemListener {
            override fun onSeriesItemAnimationProgress(
                v: Float,
                v1: Float
            ) {

                val i = v1.toInt()
                completion.text = "$i%"
                if (v1 >= 1 && v1 <= 5) {
                    abnb_1.playAnimation()
                } else if (v1 >= 10 && v1 < 30) {
                    ist.setTextColor(Color.parseColor("#00ca71"))
                }else if (v1 >= 30 && v1 < 35) {
                    abnb_2.playAnimation()
                } else if (v1 >= 40 && v1 < 50) {
                    sec.setTextColor(Color.parseColor("#00ca71"))
                }else if (v1 >= 50 && v1 < 60) {
                    abnb_3.playAnimation()
                } else if (v1 >= 65 && v1 < 70) {
                    thi.setTextColor(Color.parseColor("#00ca71"))
                }else if (v1 >= 70 && v1 < 75) {
                    abnb_4.playAnimation()
                } else if (v1 >= 80 && v1 < 85) {
                    fou.setTextColor(Color.parseColor("#00ca71"))
                }else if (v1 >= 85 && v1 < 90) {
                    abnb_5.playAnimation()
                } else if (v1 >= 95 && v1 < 100) {
                    fif.setTextColor(Color.parseColor("#00ca71"))
                }
            }

            override fun onSeriesItemDisplayProgress(v: Float) {

            }
        })

        dynamicArcView2.addEvent(
            DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(0)
                .setListener(object : DecoEvent.ExecuteEventListener {
                    override fun onEventStart(decoEvent: DecoEvent) {
                    }

                    override fun onEventEnd(decoEvent: DecoEvent) {
                    }
                })
                .build()
        )

        dynamicArcView2.addEvent(
            DecoEvent.Builder(100f).setIndex(series1Index2).setDelay(1000).setListener(object :
                DecoEvent.ExecuteEventListener {
                override fun onEventStart(decoEvent: DecoEvent) {
                }

                override fun onEventEnd(decoEvent: DecoEvent) {
                    check = 1
                    youDesirePermissionCode(this@Applying_Ultra)
                    enablesall()
                }
            }).build()
        )
    }

    private fun setAnim() {
        lottieAnimationView2.setAnimation("7301-loader-animation.json")
        lottieAnimationView2.loop(true)
        lottieAnimationView2.playAnimation()
        abnb_1.setAnimation("433-checked-done.json")
        abnb_2.setAnimation("433-checked-done.json")
        abnb_3.setAnimation("433-checked-done.json")
        abnb_4.setAnimation("433-checked-done.json")
        abnb_5.setAnimation("433-checked-done.json")
    }

    fun enablesall() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.disable()
        }

        val wifiManager = application.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager


        val wifiEnabled = wifiManager.isWifiEnabled
        if (wifiEnabled) {
            wifiManager.isWifiEnabled = false
        }
    }


    override fun onBackPressed() {
        //        super.onBackPressed();
    }


    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && Settings.System.canWrite(this)) {
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success")


            //            Toast.makeText(getApplicationContext(),"onActivityResult",Toast.LENGTH_LONG).show();
            //do your code
            PowerSaving_Complition.setAutoOrientationEnabled(applicationContext, false)

            Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 20)

            ContentResolver.setMasterSyncAutomatically(false)


            val i = Intent(this@Applying_Ultra, BatterySaver_Black::class.java)
            startActivity(i)
            finish()
        }
    }


    fun youDesirePermissionCode(context: Activity) {
        val permission: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context)
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED
            //            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
            //            setAutoOrientationEnabled(context, false);
        }
        if (permission) {
            //do your code
            PowerSaving_Complition.setAutoOrientationEnabled(applicationContext, false)

            Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 20)

            ContentResolver.setMasterSyncAutomatically(false)


            val i = Intent(this@Applying_Ultra, BatterySaver_Black::class.java)
            startActivity(i)
            finish()

        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + context.packageName)
                context.startActivityForResult(intent, 1)
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_SETTINGS), 1)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code

            //            Toast.makeText(getApplicationContext(),"onRequestPermissionsResult",Toast.LENGTH_LONG).show();

            PowerSaving_Complition.setAutoOrientationEnabled(applicationContext, false)

            Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 20)

            ContentResolver.setMasterSyncAutomatically(false)


            val i = Intent(this@Applying_Ultra, BatterySaver_Black::class.java)
            startActivity(i)
            finish()
        }

    }


    override fun onResume() {
        super.onResume()
        if (check == 1) {
            try {
                PowerSaving_Complition.setAutoOrientationEnabled(applicationContext, false)

                Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 20)

                ContentResolver.setMasterSyncAutomatically(false)

            } catch (e: Exception) {
                val i = Intent(this@Applying_Ultra, BatterySaver_Black::class.java)
                startActivity(i)
                finish()
            }

            val i = Intent(this@Applying_Ultra, BatterySaver_Black::class.java)
            startActivity(i)
            finish()
        }
    }
}
