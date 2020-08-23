package uz.xsoft.restapi.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.edit_item_dialog.view.*
import uz.xsoft.restapi.R
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.utils.SingleBlock

class EditDialog(context: Context) : AlertDialog(context) {
    @SuppressLint("InflateParams")
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.edit_item_dialog, null, false)
    private var listener: SingleBlock<ContactData>? = null
    private var id1 = 0

    init {
        setView(contentView)
        setTitle("Edit")
        contentView.apply {
            btEditDialog.setOnClickListener { listener?.invoke(getData()) }
            btCancelEditDialog.setOnClickListener { cancel() }
        }
    }

    fun setOnEditClickListener(f: SingleBlock<ContactData>) {
        listener = f
    }

    fun loadData(contactData: ContactData) {
        contentView.apply {
            id1 = contactData.id
            firstName.setText(contactData.firstName)
            lastName.setText(contactData.lastName)
            phoneNumber.setText(contactData.phoneNumber)
        }
    }

    private fun getData(): ContactData {
        contentView.apply {
            return ContactData(
                id = id1,
                firstName = firstName.text.toString(),
                lastName = lastName.text.toString(),
                phoneNumber = phoneNumber.text.toString()
            )
        }
    }
}