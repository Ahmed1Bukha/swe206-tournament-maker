import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/classes/controller_admin.dart';

class HomePageAdmin extends StatefulWidget {
  HomePageAdmin(this.tournamentManagerAdmin, {super.key});
  static String id = "homeAdmin";
  ControllerAdmin tournamentManagerAdmin;
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
      body: Column(
        children: [
          const Text(
            "Matches:",
            style: TextStyle(fontSize: 25),
          ),
          ...widget.tournamentManagerAdmin.getMatches(),
          const Text(
            "Tournaments:",
            style: TextStyle(fontSize: 25),
          ),
          ...widget.tournamentManagerAdmin.getTournaments(),
        ],
      ),
    );
  }
}
