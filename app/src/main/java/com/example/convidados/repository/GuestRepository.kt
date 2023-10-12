package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }


    fun insert(guest: GuestModel): Boolean {
        try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val bd = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            bd.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(guestID: Int): Boolean {
        return try {
            val bd = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guestID.toString())

            bd.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }


    fun getAll(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val bd = guestDataBase.readableDatabase


            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )


            val cursor = bd.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection,
                null, null, null,
                null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }

            }
            cursor.close()
            return list
        } catch (e: Exception) {
            return list
        }
    }

    fun getPresent(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val bd = guestDataBase.readableDatabase

            val columId = DataBaseConstants.GUEST.COLUMNS.ID
            val columName = DataBaseConstants.GUEST.COLUMNS.NAME
            val columPresence = DataBaseConstants.GUEST.COLUMNS.PRESENCE

            val cursor = bd.rawQuery(
                "SELECT " + columId + ", "
                        + columName + ", " + columPresence +
                        " FROM " + DataBaseConstants.GUEST.TABLE_NAME + " WHERE "
                        + columPresence + " = 1", null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(columId))
                    val name =
                        cursor.getString(cursor.getColumnIndex(columName))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(columPresence))

                    list.add(GuestModel(id, name, presence == 1))
                }

            }
            cursor.close()
            return list
        } catch (e: Exception) {
            return list
        }
    }

    fun getAbsent(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val bd = guestDataBase.readableDatabase

            val columId = DataBaseConstants.GUEST.COLUMNS.ID
            val columName = DataBaseConstants.GUEST.COLUMNS.NAME
            val columPresence = DataBaseConstants.GUEST.COLUMNS.PRESENCE

            val cursor = bd.rawQuery(
                "SELECT " + columId + ", "
                        + columName + ", " + columPresence +
                        " FROM " + DataBaseConstants.GUEST.TABLE_NAME + " WHERE "
                        + columPresence + " = 0", null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(columId))
                    val name =
                        cursor.getString(cursor.getColumnIndex(columName))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(columPresence))

                    list.add(GuestModel(id, name, presence == 1))
                }

            }
            cursor.close()
            return list
        } catch (e: Exception) {
            return list
        }
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? =  null
        try {
            val bd = guestDataBase.readableDatabase


            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = bd.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection,
                selection, args, null,
                null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    guest = GuestModel(id, name, presence == 1)
                }

            }
            cursor.close()
            return guest
        } catch (e: Exception) {
            return guest
        }
    }
}
