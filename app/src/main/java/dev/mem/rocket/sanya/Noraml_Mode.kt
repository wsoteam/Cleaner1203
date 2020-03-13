package dev.mem.rocket.sanya

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdRequest

import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent

import dev.mem.rocket.sanya.PPP.PowerSaving_Complition

import dev.mem.rocket.sanya.Constants.adsBatterySaver
import dev.mem.rocket.sanya.Constants.adsShow
import dev.mem.rocket.sanya.utils.PreferencesProvider
import kotlinx.android.synthetic.main.revert_to_normal.*

/**
 * Created by intag pc on 2/21/2017.
 */

class Noraml_Mode : Activity(), AdMobFullscreenManager.AdMobFullscreenDelegate {


    //todo Prefs
    internal var check = 0
    internal var fullscreenManager: AdMobFullscreenManager? = null
//    private var mAdView: AdView? = null

    private val adManager: AdMobFullscreenManager?
        get() {
            if (fullscreenManager == null) {
                configureManager()
            }
            return fullscreenManager
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.revert_to_normal)

        if (adsBatterySaver && adsShow) {
        }
        adsBatterySaver = !adsBatterySaver

        abnb_low_charge.setAnimation("low_charge.json")
        abnb_low_charge.playAnimation()
        abnb_low_charge.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                tvLoad.visibility = View.VISIBLE
                if (adsShow) {
                    adManager!!.completed()
                } else {
                    check = 1
                    youDesirePermissionCode(this@Noraml_Mode)
                    PreferencesProvider.getInstance().edit()
                        .putString("mode", "0")
                        .apply()
                }
            }
        })
    }

    fun enablesall() {
        PowerSaving_Complition.setAutoOrientationEnabled(applicationContext, true)
        Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 255)
        ContentResolver.setMasterSyncAutomatically(true)
    }

    override fun onBackPressed() {
        //        super.onBackPressed();
    }


    fun youDesirePermissionCode(context: Activity) {
        val permission: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context)
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED

        }
        if (permission) {
            //do your code
            enablesall()
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

    //
    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && Settings.System.canWrite(this)) {
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success")
            enablesall()
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enablesall()
            finish()
        }

    }


    override fun onResume() {
        super.onResume()
        if (check == 1) {
            try {
                enablesall()
            } catch (e: Exception) {
                finish()
            }

            finish()
        }
    }

    private fun configureManager() {
        if (fullscreenManager == null) {
            fullscreenManager = AdMobFullscreenManager(this, this)
        } else {
            fullscreenManager!!.reloadAd()
        }
    }

    override fun ADLoaded() {
        if (adManager!!.tryingShowDone) {
            adManager!!.showAdd()
        }
    }

    override fun ADIsClosed() {
        if (adManager!!.tryingShowDone) {
            check = 1
            youDesirePermissionCode(this@Noraml_Mode)
            //                enablesall();
            PreferencesProvider.getInstance().edit()
                    .putString("mode", "0")
                    .apply()
        }
    }


}
