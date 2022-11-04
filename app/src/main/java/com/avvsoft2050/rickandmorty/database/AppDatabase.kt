package com.avvsoft2050.rickandmorty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.avvsoft2050.rickandmorty.pojo.*

@Database(
    entities = [
        CharacterResult::class,
        Character::class,
        EpisodeResult::class,
        Episode::class,
        LocationResult::class,
        Location::class], version = 2, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }

    abstract fun characterResultDao(): CharacterResultDao
    abstract fun characterDao(): CharacterDao
    abstract fun episodeResultDao(): EpisodeResultDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun locationResultDao(): LocationResultDao
    abstract fun locationDao(): LocationDao
}