package com.moriawe.worktimer2.domain.use_case

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


// TODO: Is this a use case or not? Can I use a use case in a use case?
class CsvExporter @Inject constructor(
    private val context: Context,
    private val getListOfMonth: GetListOfMonth
) {

    val TAG = "CSV EXPORTER"
    private val csvFile = generateFile()

    suspend fun exportDataToCsv(): Intent? {
        if (csvFile != null) {
            when (val result = getListOfMonth()) {
                // When successful display log message
                is RepositoryResults.Success -> {
                    Log.d(TAG, "SUCCESS - Got list of months")
                    val monthListFlow = result.data
                    CoroutineScope(Dispatchers.IO).launch {
                        csvWriter().openAsync(csvFile, append = false) {
                            writeRow("Date", "Work hours")
                            monthListFlow?.collect { list ->
                                list.forEach { month ->
                                    writeRow(month.name, month.totalWorkTimeInHours)
                                    month.days.forEach { day ->
                                        writeRow(day.date, day.totalWorkTime)
                                    }
                                }
                            }
                        }
                    }
                    //TODO: Need to delay so app shows the proper file,
                    // Should show message that it is loading / reading ?
                    delay(2000L)
                    return goToFileIntent()
                }
                // When unsuccessful, display error message to user
                is RepositoryResults.Error -> {
                    Log.e(TAG, "ERROR - Didn't get list of months")
                }
            }
        }
        return null
    }

    private fun generateFile(): File? {
        val csvFile = File(context.filesDir, "WorktimeSheet.csv")
        csvFile.createNewFile()

        return if (csvFile.exists()) {
            Log.d(TAG, "csv file exists")
            csvFile
        } else {
            Log.d(TAG, "csv file DOES NOT exists")
            null
        }
    }

    private fun goToFileIntent(): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val contentUri = FileProvider.getUriForFile(
            context, "${context.packageName}.fileprovider", csvFile!!
        )
        val mimeType = context.contentResolver.getType(contentUri)
        intent.setDataAndType(contentUri, mimeType)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        return intent
    }
}





