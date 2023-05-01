import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';
import 'package:swe206/authintication/auth_page_student.dart';
import 'package:swe206/classes/controller_admin.dart';
import 'package:swe206/classes/controller_student.dart';
import 'package:swe206/home_admin/home_page_admin.dart';
import 'package:swe206/home_admin/main_page_admin.dart';
import 'package:swe206/home_student/home_page_student.dart';
import 'package:swe206/home_student/main_page_student.dart';
import 'package:swe206/home_student/search_page_student.dart';
import 'package:swe206/home_student/tournament_page.dart';
import 'package:swe206/requests.dart';

import 'UI_componenets/tournament_card_admin.dart';
import 'UI_componenets/tournament_card_student.dart';

void main() {
  runApp(const MyApp());
  Requests.close();
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    List<TournamentCardStudent> myTournaments = const [
      TournamentCardStudent(
        "3rd Football lmfao",
        "Football",
        "Elemniation",
        "Open for register",
      ),
      TournamentCardStudent(
        "Golf gg",
        "Football",
        "Robin table",
        "On going",
      ),
      TournamentCardStudent(
        "Volley-ball 7asa",
        "Volley-ball",
        "Elemniation",
        "Finished",
      ),
    ];
    List<TournamentCardAdmin> myTournamentsAdmin = [
      TournamentCardAdmin(
        id: 2,
        title: "Volleyball al7asa",
        type: "Elemination",
        game: "VolleyBall",
        status: "Open for register",
        matches: const {
          "32": [
            MatchCardAdmin(
              id: 1,
              participantA: "Ahmed",
              participantB: "Mohammed",
              game: "VolleyBall",
              date: "13/13/2013",
              scoreA: 0,
              scoreB: 0,
              title: "lmfao",
              endDate: false,
              finished: false,
            ),
          ]
        },
        startDate: "13/13/2013",
        endDate: "14/14/2013",
        timeBetween: 2,
        tournamentBased: "Individual",
        isOpen: false,
        isFinished: false,
      )
    ];
    ControllerStudent tournamentsManager = ControllerStudent(myTournaments);
    ControllerAdmin tournamentManagerAdmin =
        ControllerAdmin(myTournamentsAdmin);
    return MaterialApp(
      title: 'homeManager',
      theme: ThemeData(),
      initialRoute: AuthPage.id,
      routes: {
        AuthPage.id: (context) => AuthPage(),
        MainPageStudent.id: (context) => MainPageStudent(tournamentsManager),
        HomePageStudent.id: (context) => HomePageStudent(tournamentsManager),
        SearchPageStudent.id: (context) =>
            SearchPageStudent(tournamentsManager),
        MainPageAdmin.id: (context) => MainPageAdmin(tournamentManagerAdmin)
      },
    );
  }
}
