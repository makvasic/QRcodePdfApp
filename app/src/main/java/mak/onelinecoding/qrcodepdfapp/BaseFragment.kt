package mak.onelinecoding.qrcodepdfapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    var callback: Callback? = null

    open val appBarExpanded = false
    open val hideActionBar = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callback?.handleAppBar(appBarExpanded, hideActionBar)
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    interface Callback {
        fun handleAppBar(expanded: Boolean, hideActionBar: Boolean)
    }

}