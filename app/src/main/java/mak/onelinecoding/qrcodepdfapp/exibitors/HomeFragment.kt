package mak.onelinecoding.qrcodepdfapp.exibitors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import mak.onelinecoding.qrcodepdfapp.BaseFragment
import mak.onelinecoding.qrcodepdfapp.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HomeFragment : BaseFragment() {

    private lateinit var adapter: ExhibitorsAdapter

    override val appBarExpanded: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ExhibitorsAdapter(object : ExhibitorsAdapter.Listener {
            override fun onItemClicked(exhibitor: Exhibitor) {
                NavHostFragment.findNavController(this@HomeFragment)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToExhibitorFragment(exhibitor))
            }

        })

        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val exhibitorsViewModel = ViewModelProvider(this).get(ExhibitorsViewModel::class.java)
        exhibitorsViewModel.exhibitorsLiveData.observe(viewLifecycleOwner, Observer {

            progressBar.visibility = View.GONE
            when (it) {
                is ExhibitorsState.Loading -> progressBar.visibility = View.VISIBLE

                is ExhibitorsState.Error -> when (it.throwable) {
                    is ConnectException,
                    is UnknownHostException,
                    is SocketTimeoutException -> Snackbar.make(
                        progressBar.parent as View,
                        "Check your internet connection",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Retry") {
                            exhibitorsViewModel.getExhibitors(true)
                        }.show()
                    else -> Toast.makeText(
                        context,
                        it.throwable.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ExhibitorsState.Success -> adapter.submitList(it.exhibitors)
            }
        })

        exhibitorsViewModel.getExhibitors()
    }
}