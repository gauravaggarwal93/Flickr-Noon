package com.github.flickr.di.module

import com.github.flickr.data.source.db.AppDatabase
import com.github.flickr.data.source.db.AppDbOpenHelper
import com.github.flickr.data.source.repository.local.AppLocalDataSource
import com.github.flickr.data.source.prefs.AppPreferencesHelper
import com.github.flickr.data.source.prefs.PreferencesHelper
import com.github.flickr.data.source.repository.remote.AppRemoteDataSource
import com.github.flickr.data.source.repository.AppDataRepository
import com.github.flickr.data.source.repository.AppDataSource
import com.github.flickr.data.source.repository.AppRepository
import com.github.flickr.di.DatabaseInfo
import com.github.flickr.di.Local
import com.github.flickr.di.PreferenceInfo
import com.github.flickr.di.Remote
import com.github.flickr.utils.AppConstants

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Modules related to data and repository
 *
 * Created by gaurav.
 */

@Module
class DataModule {

    @Provides
    @Singleton
    @DatabaseInfo
    internal fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    @Local
    internal fun provideAppLocalDataSource(appLocalDataSource: AppLocalDataSource): AppDataSource {
        return appLocalDataSource
    }

    @Provides
    @Singleton
    @Remote
    internal fun provideAppRemoteDataSource(appRemoteDataSource: AppRemoteDataSource): AppDataSource {
        return appRemoteDataSource
    }

    @Provides
    @Singleton
    internal fun provideAppRepository(dataRepository: AppDataRepository): AppRepository {
        return dataRepository
    }

    @Provides
    @Singleton
    internal fun provideAppDb(appDbOpenHelper: AppDbOpenHelper): AppDatabase {
        return appDbOpenHelper.database
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

}
