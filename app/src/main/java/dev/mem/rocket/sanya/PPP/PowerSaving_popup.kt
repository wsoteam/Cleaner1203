package dev.mem.rocket.sanya.PPP

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import android.view.animation.OvershootInterpolator
import com.google.android.gms.ads.AdRequest

import dev.mem.rocket.sanya.OOP.PowersClass


import java.util.ArrayList

import dev.mem.rocket.sanya.R
import dev.mem.rocket.sanya.SubscriptionProvider
import dev.mem.rocket.sanya.utils.PreferencesProvider
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.powersaving_popup.*

/**
 * Created by intag pc on 2/19/2017.
 */

class PowerSaving_popup : Activity() {
    internal var items: MutableList<PowersClass> = mutableListOf()

    lateinit var mAdapter: PowerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = intent.extras
        setContentView(R.layout.powersaving_popup)

        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }


        items = ArrayList()
        applied.setOnClickListener {
            PreferencesProvider.getInstance().edit()
                .putString("mode", "1")
                .apply()

            val i = Intent(applicationContext, PowerSaving_Complition::class.java)
            startActivity(i)

            finish()
        }
        abnb_normal_charge.setAnimation("8577-battery.json")
        abnb_normal_charge.frame = 51
        abnb_normal_charge.loop(true)
        abnb_normal_charge.playAnimation()
    }

    fun add(text: String, position: Int) {


        val item = PowersClass(text)
        items.add(item)
        //        mDataSet.add(position, text);
        mAdapter.notifyItemInserted(position)
    }

    //
    override fun onBackPressed() {
        //   super.onBackPressed();
    }

}
