package com.example.aspectJPlugin

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
        var log = project.logger
        //获取variants

        var android = project.extensions.getByName("android")


        project.plugins.all{

            when(it){

                is LibraryPlugin->{

                    var libraryExtension:LibraryExtension = project.extensions
                            .getByName("com.android.library") as LibraryExtension

                            libraryExtension.libraryVariants.all{variant->{

                            }

                            }
                }
            }


        }

        project.plugins.withId("com.android.application"){


            var variants = project.android.applicationVariants
            variants.all { variant ->

                if (!variant.buildType.isDebuggable()) {
                    log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
                    //return;
                }
                log.debug("build type '${variant.buildType.name}'.")

                //编译时做如下处理
                var javaCompile = variant.javaCompile
                javaCompile.doLast {
                    var args:Array<String> = arrayOf("-showWeaveInfo",
                            "-1.8",
                            "-inpath", javaCompile.destinationDir.toString(),
                            "-aspectpath", javaCompile.classpath.asPath,
                            "-d", javaCompile.destinationDir.toString(),
                            "-classpath", javaCompile.classpath.asPath,
                            "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator))


                    var handler:MessageHandler = MessageHandler(true)

                    Main().run(args, handler)
                    for (message in handler.getMessages(null, true)) {
                    when (message.getKind()) {
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


    }
}