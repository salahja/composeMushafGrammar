package com.example.compose

import android.content.Context
import androidx.room.Room
import com.example.mushafconsolidated.QuranAppDatabase

class DependencyInjector(applicationContext: Context) {

    //  val repository: Repository by lazy { provideRepository(database) }

    private val database: QuranAppDatabase by lazy { provideDatabase(applicationContext) }
    //  private val dbMapper: DbMapper = DbMapperImpl()

    private fun provideDatabase(applicationContext: Context): QuranAppDatabase =
        Room.databaseBuilder(
            applicationContext,
            QuranAppDatabase::class.java,
            QuranAppDatabase.DATABASE_NAME
        ).build()

    /*    private fun provideRepository(database: QuranAppDatabase): Repository {
            val postDao = database.postDao()

            return RepositoryImpl(postDao, dbMapper)
        }*/
}