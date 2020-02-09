package com.example.flutter_messagechannel_example

import android.os.Handler
//import android.util.Log
import androidx.annotation.NonNull
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        val channel = BasicMessageChannel<String>(
                flutterEngine.dartExecutor.binaryMessenger,
                "com.example.messagechannel/interop",
                StringCodec.INSTANCE)

        // Receive messages from Dart
        channel.setMessageHandler { message, reply ->
            Log.d("Android", "Received message = $message")
            reply.reply("Reply from Android")
        }

        // Send message to Dart
        Handler().postDelayed({
            channel.send("Hello World from Android") { reply ->
                Log.d("Android", "Rreply = $reply")
            }
        }, 500)
    }
}
