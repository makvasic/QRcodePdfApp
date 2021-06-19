package mak.onelinecoding.qrcodepdfapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pdf.view.*

class PdfModelsAdapter(private val listener: Listener) :
    ListAdapter<PdfModel, PdfModelsAdapter.PdfViewHolder>(object :
        DiffUtil.ItemCallback<PdfModel>() {
        override fun areItemsTheSame(oldItem: PdfModel, newItem: PdfModel): Boolean {
            return oldItem.filePath == newItem.filePath
        }

        override fun areContentsTheSame(oldItem: PdfModel, newItem: PdfModel): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        return PdfViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pdf, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class PdfViewHolder(itemView: View, private val listener: Listener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(pdfModel: PdfModel) = with(itemView) {

            if (pdfModel.thumbUrl.isEmpty()) {
                thumbImageView.setImageResource(R.mipmap.ic_launcher)
            } else {
                Picasso.get().load(pdfModel.thumbUrl)
                    .error(R.mipmap.ic_launcher)
                    .into(thumbImageView)
            }


            pdfNameTextView.text = pdfModel.name

            deletePdfButton.setOnClickListener {
                listener.onItemDeleted(pdfModel)
            }

            sharePdfButton.setOnClickListener {
                listener.onItemShared(pdfModel)
            }

            setOnClickListener {
                listener.onItemClicked(pdfModel)
            }

        }

    }

    interface Listener {
        fun onItemClicked(pdfModel: PdfModel)
        fun onItemDeleted(pdfModel: PdfModel)
        fun onItemShared(pdfModel: PdfModel)
    }
}