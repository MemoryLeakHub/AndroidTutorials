package com.vrashkov.graphql.di

import android.content.Context
import android.os.Looper
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.CompiledField
import com.apollographql.apollo3.api.Executable
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.api.*
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {
    protected open fun baseUrl() = "https://apollo-fullstack-tutorial.herokuapp.com/graphql"

    @Singleton
    @Provides
    fun provideGraphQl(@ApplicationContext context: Context) : ApolloClient {

         val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)).build()

        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context,"launches.db")

        return ApolloClient.Builder()
            .serverUrl(baseUrl())
            .fetchPolicy(FetchPolicy.NetworkFirst)
            .normalizedCache(
                normalizedCacheFactory = sqlNormalizedCacheFactory,
                cacheKeyGenerator = TypePolicyCacheKeyGenerator,
                metadataGenerator = LaunchesMetadataGenerator(),
                apolloResolver = FieldPolicyApolloResolver,
                recordMerger = FieldRecordMerger(LaunchesFieldMerger()),
               )
            .okHttpClient(okHttpClient)
            .build()
    }
}
//
private class LaunchesMetadataGenerator : MetadataGenerator {
    override fun metadataForObject(obj: Any?, context: MetadataGeneratorContext): Map<String, Any?> {
        if (context.field.name == "launches") {
            return mapOf("merge" to true)
        }
        return emptyMap()
    }
}
private class LaunchesFieldMerger : FieldRecordMerger.FieldMerger {
    @OptIn(ApolloExperimental::class)
    override fun mergeFields(existing: FieldRecordMerger.FieldInfo, incoming: FieldRecordMerger.FieldInfo): FieldRecordMerger.FieldInfo {
        var result = if (!existing.metadata.containsKey("merge") || !incoming.metadata.containsKey("merge")) {
            incoming
        } else {
            val existingList = existing.value as List<*>
            val incomingList = incoming.value as List<*>
            val mergedList = existingList + incomingList
            val mergedMetadata = mapOf("merge" to true)
            FieldRecordMerger.FieldInfo(
                value = mergedList,
                metadata = mergedMetadata
            )
        }
        return result
    }
}