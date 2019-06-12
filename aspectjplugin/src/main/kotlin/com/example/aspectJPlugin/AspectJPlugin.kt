package com.example.aspectJPlugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.ApplicationVariant
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import com.android.build.gradle.internal.variant.ApplicationVariantData
import com.android.build.gradle.internal.api.ApplicationVariantImpl
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.reflect.TypeOf
import sun.net.ApplicationProxy

class AspectJPlugin : Plugin<Project> {


    override fun apply(project: Project) {
        val log = project.logger
        //获取variants
        log.debug("apply project name:"+project.name)

        project.plugins.all{
            log.debug("plugin name:"+it.toString())
            when(it){
                //应用com.android.library插件，即library模块
                is LibraryPlugin->{

                    val libraryExtension = project.extensions.getByType(LibraryExtension::class.java)

                            libraryExtension.libraryVariants.all{

                                if (!it.buildType.isDebuggable) {
                                    log.debug("Skipping non-debuggable build type '${it.buildType.name}'.")
                                    //return;
                                }
                                log.debug("library build type '${it.buildType.name}'.")

                                //编译时做如下处理
                                val javaCompile = it.javaCompileProvider.get()
                                javaCompile.doLast {
                                    val args:Array<String> = arrayOf("-showWeaveInfo",
                                            "-1.8",
                                            "-inpath", javaCompile.destinationDir.toString(),
                                            "-aspectpath", javaCompile.classpath.asPath,
                                            "-d", javaCompile.destinationDir.toString(),
                                            "-classpath", javaCompile.classpath.asPath,
                                            "-bootclasspath", libraryExtension.bootClasspath
                                            .joinToString(File.pathSeparator))


                                    val handler = MessageHandler(true)

                                    Main().run(args, handler)
                                    for (message in handler.getMessages(null, true)) {
                                        when (message.kind) {
                                            IMessage.ABORT ->{}
                                            IMessage.ERROR ->{}
                                            IMessage.FAIL ->log.error(message.message, message.thrown)
                                            IMessage.WARNING ->log.warn(message.message, message.thrown)
                                            IMessage.INFO ->log.info(message.message, message.thrown)
                                            IMessage.DEBUG ->log.debug(message.message, message.thrown)
                                            else ->{

                                            }
                                        }
                                    }

                                }
                            }

                }
                //应用com.android.application插件，即application模块
                is AppPlugin ->{

                    val appExtension = project.extensions.getByType(AppExtension::class.java)

                    appExtension.applicationVariants.all{
                        if (!it.buildType.isDebuggable) {
                            log.warn("Skipping non-debuggable build type '${it.buildType.name}'.")
                             //return;
                        }
                        log.debug("application build type '${it.buildType.name}'.")

                        //编译时做如下处理
                        val javaCompile = it.javaCompileProvider.get()
                        javaCompile.doLast {
                            val args:Array<String> = arrayOf("-showWeaveInfo",
                                    "-1.8",
                                    "-inpath", javaCompile.destinationDir.toString(),
                                    "-aspectpath", javaCompile.classpath.asPath,
                                    "-d", javaCompile.destinationDir.toString(),
                                    "-classpath", javaCompile.classpath.asPath,
                                    "-bootclasspath", appExtension.bootClasspath
                                    .joinToString(File.pathSeparator))


                            val handler = MessageHandler(true)

                            Main().run(args, handler)
                            for (message in handler.getMessages(null, true)) {
                                when (message.kind) {
                                    IMessage.ABORT ->{}
                                    IMessage.ERROR ->{}
                                    IMessage.FAIL ->log.error(message.message, message.thrown)
                                    IMessage.WARNING ->log.warn(message.message, message.thrown)
                                    IMessage.INFO ->log.info(message.message, message.thrown)
                                    IMessage.DEBUG ->log.debug(message.message, message.thrown)
                                    else ->{

                                    }
                                }
                            }

                        }
                    }


                }

                else->{

                }
            }



        }




    }
}