import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';
import 'package:swe206/classes/controller_student.dart';

import '../requests.dart';

class HomePageStudent extends StatefulWidget {
  const HomePageStudent(this.tournamentsManager, {super.key});
  static String id = "HomePageStudent";
  final ControllerStudent tournamentsManager;
  @override
  State<HomePageStudent> createState() => _HomePageStudentState();
}

class _HomePageStudentState extends State<HomePageStudent> {
  bool isLoading = false;
  List<dynamic> tournamentsCard = [];
  Future getTournaments() async {
    setState(() {
      isLoading = true;
    });
    tournamentsCard = await Requests.getTournaments();
    setState(() {
      isLoading = false;
    });
  }

  @override
  void initState() {
    getTournaments();
    super.initState();
  }

  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Student Home Page")),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: isLoading
            ? const Center(child: CircularProgressIndicator())
            : RefreshIndicator(
                edgeOffset: 20,
                onRefresh: getTournaments,
                child: ListView(
                  children: [
                    const Text(
                      "Current Tournaments: ",
                      style: TextStyle(fontSize: 20),
                    ),
                    ...tournamentsCard,
                  ],
                ),
              ),
      ),
    );
  }
}
