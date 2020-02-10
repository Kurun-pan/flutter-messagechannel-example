package com.example.flutter_messagechannel_example

import android.os.Handler
//import android.util.Log
import androidx.annotation.NonNull
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.plugin.common.JSONMessageCodec
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        val channel = BasicMessageChannel(
                flutterEngine.dartExecutor.binaryMessenger,
                "com.example.messagechannel/interop",
                StringCodec.INSTANCE)
        //val channel = BasicMessageChannel(
        //        flutterEngine.dartExecutor.binaryMessenger,
        //        "com.example.messagechannel/interop",
        //        JSONMessageCodec.INSTANCE)

        // Receive messages from Dart
        channel.setMessageHandler { message, reply ->
            Log.d("Android", "Received message = $message")
            reply.reply("Reply from Android")
            //reply.reply(mapOf("reply" to "Android reply"))
        }

        // Send message to Dart
        Handler().postDelayed({
            //channel.send(mapOf("name" to "Android", "launch" to 2009)) { reply ->
            channel.send("Hello World from Android") { reply ->
                Log.d("Android", "$reply")
            }
        }, 500)
    }
}
