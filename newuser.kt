package com.alikhan.i210429

import android.os.Parcel
import android.os.Parcelable

data class newuser(
    var name: String,
    var email: String,
    var country: String,
    var city: String,
    var password: String,
)