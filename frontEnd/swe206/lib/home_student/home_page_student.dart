import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/tournament_card.dart';

class HomePageStudent extends StatefulWidget {
  const HomePageStudent({super.key});
  static String id = "HomePageStudent";

  @override
  State<HomePageStudent> createState() => _HomePageStudentState();
}

class _HomePageStudentState extends State<HomePageStudent> {
  int _selectedIndex = 0;
  @override
  Widget build(BuildContext context) {
    List<TournamentWidget> myTournaments() => const [
          TournamentWidget(
            "3rd Football lmfao",
            "Football",
            "Elemniation",
            "Open for register",
          ),
          TournamentWidget(
            "Gold gg",
            "Football",
            "Robin table",
            "On going",
          ),
          TournamentWidget(
            "3rd Football lmfao",
            "Football",
            "Elemniation",
            "Open for register",
          ),
          TournamentWidget(
            "3rd Football lmfao",
            "Football",
            "Elemniation",
            "Open for register",
          ),
          TournamentWidget(
            "Gold gg",
            "Football",
            "Robin table",
            "On going",
          ),
          TournamentWidget(
            "3rd Football lmfao",
            "Football",
            "Elemniation",
            "Open for register",
          ),
          TournamentWidget(
            "3rd Football lmfao",
            "Football",
            "Elemniation",
            "Open for register",
          ),
          TournamentWidget(
            "Gold gg",
            "Football",
            "Robin table",
            "On going",
          ),
          TournamentWidget(
            "3rd Football lmfao",
            "Football",
            "Elemniation",
            "Open for register",
          )
        ];
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
              ...myTournaments(),
            ],
          ),
        ),
      ),
    );
  }
}
