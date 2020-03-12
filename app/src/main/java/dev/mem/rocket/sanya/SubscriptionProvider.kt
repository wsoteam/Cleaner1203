package dev.mem.rocket.sanya

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.android.billingclient.api.*

object SubscriptionProvider : PurchasesUpdatedListener, BillingClientStateListener {

    lateinit private var playStoreBillingClient: BillingClient
    private lateinit var preferences: SharedPreferences

    private const val SUBSCRIPTION_ID = "noads"
    private const val HAS_SUBSCRIPTION = "has_subscription"
    private const val IS_APPROVED = "is_approved"

    fun init(context: Context) {
        preferences = context.getSharedPreferences("subscription", Context.MODE_PRIVATE)
        playStoreBillingClient = BillingClient.newBuilder(context.applicationContext)
                .enablePendingPurchases() // required or app will crash
                .setListener(this).build()
        connectToPlayBillingService()
    }

    private fun connectToPlayBillingService(): Boolean {
        if (!playStoreBillingClient.isReady) {
            playStoreBillingClient.startConnection(this)
            return true
        }
        return false
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {

    }

    override fun onBillingServiceDisconnected() {

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            var hasSubscription = false
            val result = playStoreBillingClient.queryPurchases(BillingClient.SkuType.SUBS)
            result.purchasesList.forEach {
                if (it.sku == SUBSCRIPTION_ID) {
                    hasSubscription = true
                    val params = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(it
                            .purchaseToken).build()
                    playStoreBillingClient.acknowledgePurchase(params) { billingResult ->
                        when (billingResult.responseCode) {
                            BillingClient.BillingResponseCode.OK -> {
                                preferences.edit().putBoolean(IS_APPROVED, true).apply()
                            }
                            else -> {
                                preferences.edit().putBoolean(HAS_SUBSCRIPTION, false).apply()
                            }
                        }
                    }

                }
            }
            preferences.edit().putBoolean(HAS_SUBSCRIPTION, hasSubscription).apply()
        }
    }

    fun hasSubscription() = preferences.getBoolean(HAS_SUBSCRIPTION, false)

    fun startSubscription(activity: Activity) {
        val params = SkuDetailsParams.newBuilder().setSkusList(arrayListOf(SUBSCRIPTION_ID))
                .setType(BillingClient.SkuType.SUBS).build()
        playStoreBillingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    if (skuDetailsList.orEmpty().isNotEmpty()) {
                        skuDetailsList.forEach {
                            val perchaseParams = BillingFlowParams.newBuilder().setSkuDetails(it)
                                    .build()
                            playStoreBillingClient.launchBillingFlow(activity, perchaseParams)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }


}
