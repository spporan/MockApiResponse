package poran.cse.mockapiresponse.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response

@Keep
data class StudyMore(
    @SerializedName("bundles")
    val wordBundles: List<MoreStudyItem?>?,
    @SerializedName("lessons")
    val lessons: List<MoreStudyItem?>?,
    @SerializedName("pageSize")
    val pageSize: Int?
)


fun Response<StudyMore>.toStudyItems(): List<MoreStudyItem?> {
    return if (isSuccessful) {
        body()?.lessons ?: emptyList()
    } else {
        emptyList()
    }
}