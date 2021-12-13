package com.example.d048qksms

import android.content.Context
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import androidx.annotation.RequiresApi
import java.util.*
import javax.inject.Inject

class SubscriptionUtilsImpl @Inject constructor(
        context: Context,
        private val permissions: PermissionManager
) : SubscriptionUtils {
    /**
     * If we don't have the phone permission, then return a null [SubscriptionManager]
     */
    private val subscriptionManager: SubscriptionManager? = SubscriptionManager.from(context)
        get() = field?.takeIf { permissions.hasPhone() }

    override val subscriptions: List<SubscriptionInfo>
        get() = subscriptionManager?.activeSubscriptionInfoList ?: listOf()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun getSubIdForAddress(address: String): Int {
        return subscriptions.firstOrNull { PhoneNumberUtils.compare(it.number, address) }?.subscriptionId
                ?: -1
    }
}


interface SubscriptionUtils {

    val subscriptions: List<SubscriptionInfo>

    fun getSubIdForAddress(address: String): Int
}
