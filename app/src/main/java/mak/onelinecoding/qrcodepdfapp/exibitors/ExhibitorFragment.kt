package mak.onelinecoding.qrcodepdfapp.exibitors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_exhibitor.*
import kotlinx.android.synthetic.main.layout_exhibitor_data.*
import mak.onelinecoding.qrcodepdfapp.BaseFragment
import mak.onelinecoding.qrcodepdfapp.R

class ExhibitorFragment : BaseFragment() {

    private lateinit var adapter: MembersAdapter
    lateinit var exhibitor: Exhibitor

    override val hideActionBar: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exhibitor = ExhibitorFragmentArgs.fromBundle(
            arguments!!
        ).exhibitor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_exhibitor, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = MembersAdapter(object :
            MembersAdapter.Listener {
            override fun onItemClicked(member: Member) {
                NavHostFragment.findNavController(this@ExhibitorFragment)
                    .navigate(
                        ExhibitorFragmentDirections.actionExhibitorFragmentToMemberFragment(
                            member
                        )
                    )
            }

        })

        Picasso.get().load(exhibitor.logo).into(fakeToolbarLogo)

        catalogsButtonCardView.setOnClickListener {
            NavHostFragment.findNavController(this@ExhibitorFragment)
                .navigate(
                    ExhibitorFragmentDirections.actionExhibitorFragmentToPdfsFragment(
                        exhibitor.token
                    )
                )
        }

        companyTextInputLayout.editText!!.setText(exhibitor.name)
        countryTextInputLayout.editText!!.setText("-")
        cityTextInputLayout.editText!!.setText("-")
        emailTextInputLayout.editText!!.setText("-")
        websiteTextInputLayout.editText!!.setText("-")
        wechatTextInputLayout.editText!!.setText("-")

        recyclerView.adapter = adapter
        adapter.submitList(exhibitor.members)
    }
}