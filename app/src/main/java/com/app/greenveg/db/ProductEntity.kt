package com.app.greenveg.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class ProductEntity (
@PrimaryKey(autoGenerate = true) val id:Int,
@ColumnInfo(name ="product_name") val product_name:String,
@ColumnInfo(name ="product_name") val product_des:String
)
