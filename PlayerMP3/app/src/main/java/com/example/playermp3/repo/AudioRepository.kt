package com.example.playermp3.repo
import android.content.ContentResolver
import android.provider.MediaStore
import com.example.playermp3.model.AudioFile

object AudioRepository {
    lateinit var repository:MutableList<AudioFile>
    var currentAudio = -1

    fun isInitialized() = currentAudio != -1

    fun getCurrent() = repository[currentAudio]

    fun getNext() {
        currentAudio = (currentAudio + 1) % repository.size
    }

    fun getPrevious() {
        currentAudio -= 1
        if (currentAudio < 0) currentAudio = repository.size - 1
    }

    fun loadAudioFiles(contentResolver: ContentResolver) {
        repository = mutableListOf()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.DURATION
        )
        val cursor = contentResolver.query(
            uri,
            projection,
            MediaStore.Audio.Media.IS_MUSIC + " != 0",
            null,
            MediaStore.Audio.Media.TITLE + " ASC"
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val path = cursor.getString(0)
                val title = cursor.getString(1)
                val artist = cursor.getString(2)
                val album = cursor.getString(3)
                val duration = cursor.getInt(4)
                val audioModel = AudioFile(path, title, artist, album, duration)
                repository.add(audioModel)
            }
            cursor.close()
        }
    }
}