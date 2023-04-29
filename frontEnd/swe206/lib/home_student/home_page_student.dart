import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';
import 'package:swe206/classes/controller_student.dart';

class HomePageStudent extends StatefulWidget {
  const HomePageStudent(this.tournamentsManager, {super.key});
  static String id = "HomePageStudent";
  final ControllerStudent tournamentsManager;
  @override
  State<HomePageStudent> createState() => _HomePageStudentState();
}

class _HomePageStudentState extends State<HomePageStudent> {
  int _selectedIndex = 0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Student Home Page")),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                "Current Tournaments: ",
                style: TextStyle(fontSize: 20),
              ),
              ...widget.tournamentsManager.getTournaments(),
            ],
          ),
        ),
      ),
    );
  }
}
