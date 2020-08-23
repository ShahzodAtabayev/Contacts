package uz.xsoft.restapi.data.local.room.daos

import androidx.room.Dao
import androidx.room.Query
import uz.xsoft.restapi.data.local.room.entities.ContactData

@Dao
interface ContactDao : BaseDao<ContactData> {

    @Query("DELETE FROM ContactData")
    fun deleteAll()

    @Query("SELECT *FROM ContactData WHERE isDelete=:active")
    fun getAll(active: Boolean):List<ContactData>


}