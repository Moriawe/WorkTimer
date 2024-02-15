package com.moriawe.worktimer2.domain.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.use_case.GetListOfMonth
import com.moriawe.worktimer2.domain.use_case.RepositoryResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


// TODO: Is this a use case or not? Can I use a use case in a use case?
class CsvExporter @Inject constructor(
    private val context: Context,
    private val getListOfMonth: GetListOfMonth
) {

    val TAG = "CSV EXPORTER"

    fun exportDataToCsv() {

        val csvFile = generateFile()

        if (csvFile != null) {

            when (val result = getListOfMonth()) {
                // When successful display log message
                is RepositoryResults.Success -> {
                    Log.d(TAG, "SUCCESS - Got list of months")

                    val monthListFlow = result.data

                    csvWriter().open(csvFile, append = false) {

                        CoroutineScope(Dispatchers.IO).launch {
                            monthListFlow?.collect { timelist ->
                                // Write the header row
                                writeRow(listOf("[id]", "[${TimeItem.TABLE_NAME}]"))
                                // Write data rows
                                timelist.forEachIndexed { index, item ->
                                    writeRow(listOf(index, item.days))
                                }
                            }
                        }
                    }
                }
                // When unsuccessful, display error message to user
                is RepositoryResults.Error -> {
                    Log.e(TAG, "ERROR - Didn't get list of months")
                }
            }
        }
    }

    private fun generateFile(): File? {
        val csvFile = File(context.filesDir, "WorktimeSheet.csv")
        csvFile.createNewFile()

        return if (csvFile.exists()) {
            csvFile
        } else {
            null
        }
    }

    fun goToFileIntent(context: Context, file: File): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val mimeType = context.contentResolver.getType(contentUri)
        intent.setDataAndType(contentUri, mimeType)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        return intent
    }

}





