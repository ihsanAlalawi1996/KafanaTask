package com.example.ihsanstask.data.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ihsanstask.R
import com.example.ihsanstask.common.App
import com.google.gson.annotations.SerializedName
import com.example.ihsanstask.BR

@Entity(tableName = "eventModel")
data class EventModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "date")
    var date: String = "",
    @ColumnInfo(name = "hijriDate")
    var hijriDate: String = "",
    @ColumnInfo(name = "title")
    private var title: String = "",
    @ColumnInfo(name = "description")
    private var description: String = "",
) : BaseObservable() {

    @Bindable
    fun getTitle(): String = title

    @Bindable
    fun getTitleError(): String =
        if (title.isEmpty()) App.instance.getString(R.string.this_field_required) else
            ""

    @Bindable
    fun setTitle(title: String) {
        this.title = title
        notifyPropertyChanged(BR.title)
        notifyPropertyChanged(BR.titleError)
    }

    @Bindable
    fun getDescription(): String = description

    @Bindable
    fun setDescription(description: String) {
        this.description = description
        notifyPropertyChanged(BR.description)
    }
}
