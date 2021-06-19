package mak.onelinecoding.qrcodepdfapp.exibitors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_member.*
import kotlinx.android.synthetic.main.layout_member_data.*
import mak.onelinecoding.qrcodepdfapp.BaseFragment
import mak.onelinecoding.qrcodepdfapp.R

class MemberFragment : BaseFragment() {

    lateinit var member: Member

    override val hideActionBar: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        member = MemberFragmentArgs.fromBundle(
            arguments!!
        ).member
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_member, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fakeToolbarLogo.setImageResource(R.drawable.fake_logo)

        Picasso.get().load(member.photo).into(photoImageView)

        nameTextView.text = member.name

        emailTextInputLayout.editText!!.setText(member.email1)
        wechatTextInputLayout.editText!!.setText(member.wechat)
        phoneTextInputLayout.editText!!.setText(member.phone1)
        skypeTextInputLayout.editText!!.setText("-")
        whatsappTextInputLayout.editText!!.setText("-")


        backButtonCardView.setOnClickListener {
            activity?.onBackPressed()
        }


    }
}