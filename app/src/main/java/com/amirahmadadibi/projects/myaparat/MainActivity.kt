package com.amirahmadadibi.projects.myaparat

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirahmadadibi.projects.myaparat.listeners.OnPayForWatchSelectedWay
import com.amirahmadadibi.projects.myaparat.model.UserVideos
import com.amirahmadadibi.projects.myaparat.model.Video
import com.amirahmadadibi.projects.myaparat.networking.getApiService
import com.zarinpal.PaymentCallback
import com.zarinpal.ZarinPal
import com.zarinpal.provider.model.response.Receipt
import com.zarinpal.provider.repository.paymentRequest.Request
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.farshid_roohi.customadapterrecycleview.extensions.onItemClickListener
import ir.farshid_roohi.customadapterrecycleview.extensions.onLoadMoreListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), OnPayForWatchSelectedWay {
    var selectedViewItem: Video? = null
    var numberOfItemsToFetch = 0
    var recyclerVieMain: RecyclerView? = null
    var progresBarMain: ProgressBar? = null
    var videoAdapter: VideoAdapter? = null
    var hasPagination = true
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerVieMain = findViewById(R.id.recyclerViewMain)
        progresBarMain = findViewById(R.id.progressBarMain)
        videoAdapter = VideoAdapter()
        recyclerVieMain?.visibility = View.INVISIBLE
        progresBarMain?.visibility = View.VISIBLE
        recyclerVieMain?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = videoAdapter

            onLoadMoreListener {
                if (!hasPagination) {
                    return@onLoadMoreListener
                }
                videoAdapter?.loadingState()
                getVideos(APARAT_USERNAME, numberOfItemsToFetch + 10)
            }

            onItemClickListener({ postion ->
                showDialogForPayment()
                selectedViewItem = videoAdapter?.getItem(postion)
            }, {})

        }

        getVideos(APARAT_USERNAME, numberOfItemsToFetch)
    }

    fun showDialogForPayment() {
        val dialog = PayForWatchDialog()
        dialog.listener = this@MainActivity
        dialog.show(supportFragmentManager, "tag")
    }

    override fun payForWatch() {
        //شی اصلی برای آغاز به کار SDK زرین‌پال
        val zarinpalClient = ZarinPal.init(application)

        //نمایش رسید توسط sdk پس از پرداخت
        zarinpalClient.enableShowInvoice = true

        //درخواست پرداخت شما برای ارسال به زرین‌پال
        val requestByPaymentRequest = Request.asPaymentRequest(
            MERCHANT_ID,
            5000,
            "app://myaparat",
            "پرداخت درون برنامه‌ای با زرین‌پال"
        )


        //شروع فرایند پرداخت و نمایش رابط‌کاربری SDK پرداخت درون برنامه‌ایی زرین‌پال
        zarinpalClient.start(requestByPaymentRequest, object : PaymentCallback {
            override fun onClose() {
                //کاربر فرایند را با dismiss کردن رابط کاربری خاتمه داده است
            }

            override fun onException(ex: Exception) {
                //فرایند با خطا رو به رو شده است
            }

            override fun onSuccess(receipt: Receipt, raw: String) {
                //پرداخت موفقیت ب
                goHeadToWatch()
            }
        })
    }

    override fun goHeadToWatch() {
        val uri = Uri.parse(selectedViewItem?.videoPlaybackUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun onReceiveDataFromApi(videoList: List<Video>?) {
        videoAdapter?.loadedState(videoList)
    }


    private fun getVideos(userName: String, numberOfItemsToFetch: Int) {
        getApiService().getVideos(userName, "$numberOfItemsToFetch")
            .enqueue(object : Callback<UserVideos> {
                override fun onResponse(call: Call<UserVideos>, response: Response<UserVideos>) {
                    recyclerVieMain?.visibility = View.VISIBLE
                    progresBarMain?.visibility = View.INVISIBLE
                    val videoList = response.body()?.userVideoList
                    onReceiveDataFromApi(videoList)
                    if (response.body()?.url?.paginationUrl.equals("")) {
                        hasPagination = false
                        return
                    }

                }

                override fun onFailure(call: Call<UserVideos>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }
}

