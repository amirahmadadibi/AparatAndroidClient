package com.amirahmadadibi.projects.myaparat.model

import com.google.gson.annotations.SerializedName

class Video constructor(
    var id: String,
    var title: String,
    var username: String,
    var userid: String,
    @SerializedName("sender_name")
    var uploaderName: String,
    @SerializedName("visit_cnt")
    var visitCount: Int,
    @SerializedName("big_poster")
    var posterURL: String,
    @SerializedName("frame")
    var videoPlaybackUrl:String
)