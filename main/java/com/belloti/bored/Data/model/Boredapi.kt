package com.belloti.bored.Data.model

import java.io.Serializable

class Boredapi(var type: String, var activity: String) : Serializable {
    var participants: String? = null
    var price: String? = null
    var link: String? = null
    var key: String? = null
    var accessibility: String? = null

}