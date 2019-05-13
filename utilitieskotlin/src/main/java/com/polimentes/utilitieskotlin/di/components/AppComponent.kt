package com.polimentes.utilitieskotlin.di.components

import android.app.Application
import com.polimentes.utilitieskotlin.di.base.BaseApplication
import com.polimentes.utilitieskotlin.di.builders.ServiceBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


//@Component(modules = [
//    AndroidSupportInjectionModule::class,
//    AndroidInjectionModule::class,
//    ServiceBuilderModule::class,
//    AppModule::class])
//@Singleton
//interface AppComponent {
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun build(): AppComponent
//    }
//
//    fun inject(app: BaseApplication)
//
//}