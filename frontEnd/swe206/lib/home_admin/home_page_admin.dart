import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';

class HomePageAdmin extends StatefulWidget {
  const HomePageAdmin({super.key});
  static String id = "homeAdmin";

  @override
  State<HomePageAdmin> createState() => _HomePageAdminState();
}

class _HomePageAdminState extends State<HomePageAdmin> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Home page admin"),
      ),
      body: Container(
        child: const Text("Hello"),
      ),
    );
  }
}
