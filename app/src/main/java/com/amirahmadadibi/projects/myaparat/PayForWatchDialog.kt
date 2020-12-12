package com.amirahmadadibi.projects.myaparat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.amirahmadadibi.projects.myaparat.listeners.OnPayForWatchSelectedWay
import com.google.android.material.button.MaterialButton

class PayForWatchDialog() : DialogFragment() {
    var listener: OnPayForWatchSelectedWay? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner_background_dialog)
        return inflater.inflate(R.layout.dialog_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonDonate = view.findViewById<MaterialButton>(R.id.buttonPayDonate)
        val buttonGoHeadWatchVideo = view.findViewById<MaterialButton>(R.id.buttonGoHeadWatch)

        buttonDonate.setOnClickListener {
            dialog?.dismiss()
            listener?.payForWatch()
        }
        buttonGoHeadWatchVideo.setOnClickListener {
            dialog?.dismiss()
            listener?.goHeadToWatch()
        }
    }


}