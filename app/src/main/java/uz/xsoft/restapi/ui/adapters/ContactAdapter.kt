package uz.xsoft.restapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contact.view.*
import uz.xsoft.restapi.R
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.databinding.ItemContactBinding


class ContactAdapter(@MenuRes private val moreMenu: Int) :
    ListAdapter<ContactData, ContactAdapter.ViewHolder>(ContactData.CALL_BACK) {
    private var listenerDelete: ((ContactData) -> Unit)? = null
    private var listenerEdit: ((ContactData) -> Unit)? = null

    fun setOnDeleteListener(f: (ContactData) -> Unit) {
        listenerDelete = f
    }

    fun setOnEditListener(f: (ContactData) -> Unit) {
        listenerEdit = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemContactBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private var binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.apply {
                btMore.setOnClickListener { view1 ->
                    val menu = PopupMenu(context, view1)
                    menu.inflate(moreMenu)
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuDelete -> {
                                listenerDelete?.invoke(getItem(adapterPosition))
                            }
                            R.id.menuEdit -> {
                                listenerEdit?.invoke(getItem(adapterPosition))
                            }
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

        fun bind(contactData: ContactData) {
            binding.contactData = contactData
            binding.executePendingBindings()
        }
    }
}