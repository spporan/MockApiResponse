package poran.cse.mockapiresponse.data.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class MoreStudyItem(
    @SerializedName("count")
    val count: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("points")
    val points:  Int?,
    @SerializedName("topics")
    val topics: List<String?>?,
    @SerializedName("progress")
    val progress: Int? = 0,
    @SerializedName("finished")
    val finished: Boolean = false,
    @SerializedName("serial")
    val serial: Int?
)