import 'package:flutter/services.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter MessageChannel API',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter MessageChannel API Example'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const _channel = BasicMessageChannel<String>('com.example.messagechannel/interop', StringCodec());
  //static const _channel = BasicMessageChannel<dynamic>('com.example.messagechannel/interop', JSONMessageCodec());

  String _platformMessage;

  void _sendMessage() async {
    final String reply = await _channel.send('Hello World from Dart');
    /*
    final reply = await _channel.send(<String, dynamic>{
      'name': 'Dart',
      'launch': 2018,
    });
    */
    print(reply);
  }

  @override
  initState() {
    super.initState();

    // Receive messages from platform
    _channel.setMessageHandler((String message) async {
      print('Received message = $message');
      setState(() => _platformMessage = message);
      return 'Reply from Dart';
    });
    /*
    _channel.setMessageHandler((dynamic message) async {
      print('Received message = $message');
      setState(() => _platformMessage = '$message');
      return <String, dynamic>{
        'reply': 'Dart reply',
      };
    });
    */

    // Send message to platform
    _sendMessage();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Your Platform: $_platformMessage',
            ),
          ],
        ),
      ),
    );
  }
}
