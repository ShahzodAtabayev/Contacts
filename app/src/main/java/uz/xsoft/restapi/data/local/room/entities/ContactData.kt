package uz.xsoft.restapi.data.local.room.entities

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactData(
    @PrimaryKey
    val id: Int = 0,
    val phoneNumber: String?,
    val lastName: String?,
    val firstName: String,
    var isDelete: Boolean = false
) {
    companion object {
        val CALL_BACK = object : DiffUtil.ItemCallback<ContactData>() {
            override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData) =
                oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName && oldItem.phoneNumber == newItem.phoneNumber
        }
    }
}