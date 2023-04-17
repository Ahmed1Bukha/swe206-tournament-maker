import 'package:flutter/material.dart';
import 'package:swe206/authintication/auth.dart';
import 'package:swe206/classes/tournamentsManager.dart';
import 'package:swe206/home_admin/home_page_admin.dart';
import 'package:swe206/home_student/home_page_student.dart';
import 'package:swe206/home_student/main_page_student.dart';
import 'package:swe206/home_student/search_page_student.dart';
import 'package:swe206/home_student/tournament_page.dart';

import 'UI_componenets/tournament_card.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    List<TournamentWidget> myTournaments = const [
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
    TournamentsManager tournamentsManager = TournamentsManager(myTournaments);
    return MaterialApp(
      title: 'homeManager',
      theme: ThemeData(),
      initialRoute: AuthPage.id,
      routes: {
        AuthPage.id: (context) => AuthPage(),
        MainPageStudent.id: (context) => MainPageStudent(tournamentsManager),
        HomePageAdmin.id: (context) => const HomePageAdmin(),
        HomePageStudent.id: (context) => HomePageStudent(tournamentsManager),
        SearchPageStudent.id: (context) =>
            SearchPageStudent(tournamentsManager),
      },
    );
  }
}
