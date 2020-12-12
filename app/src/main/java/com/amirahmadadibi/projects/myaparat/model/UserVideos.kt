package com.amirahmadadibi.projects.myaparat.model

import com.google.gson.annotations.SerializedName

class UserVideos(
    @SerializedName("videobyuser")
    val userVideoList: List<Video>,
    @SerializedName("ui")
    var url: PaginationUrl
)