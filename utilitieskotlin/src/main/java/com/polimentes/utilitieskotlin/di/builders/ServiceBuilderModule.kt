package com.polimentes.utilitieskotlin.di.builders

import android.support.v4.app.JobIntentService
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * @author Luis L. Polimentes
 *         Description: Modulo utilizado para construir los injectores de dependencias.
 *         @ContributesAndroidInjector : Esta anotacion es utilizada para generar un injector
 *         del tipo especificado
 *         created on 18/07/2018
 *
 */
@Module
abstract class ServiceBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeService(): JobIntentService

}