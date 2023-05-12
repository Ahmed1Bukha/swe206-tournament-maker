import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';
import 'package:swe206/UI_componenets/student_card_admin.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';

import '../UI_componenets/tournament_card_student.dart';
import '../requests.dart';

// For the most part it will get a joson file and i will have to convert it to
// List of tournamentWidget.
class ControllerAdmin {
  ControllerAdmin();
  List<TournamentCardAdmin> tournamentsList = [];

  List<MatchCardAdmin> getMatches() {
    //init the list that will hold the cards for the matches
    List<MatchCardAdmin> matchesList = [];
    //Loop through every tournament.
    for (TournamentCardAdmin tournament in tournamentsList) {
      //Get the matches for a given tournament.
      List<dynamic> matches = tournament.matches;
      //Loop though each match.
    }
    return matchesList;
  }

  List<TournamentCardAdmin> getTournaments() => tournamentsList;

  searchResult(String inputSearch) async {
    List<dynamic> tournamentsList = await Requests.getTournamentsAdmin();
    List<dynamic> studentsList = await Requests.getStudents();
    List<Widget> results = [];
    for (TournamentCardAdmin name in tournamentsList) {
      if (name.title.toLowerCase().contains(inputSearch.toLowerCase())) {
        results.add(name);
      }
    }

    for (StudentCardAdmin student in studentsList) {
      if (student.studentName
          .toLowerCase()
          .contains(inputSearch.toLowerCase())) {
        print(student.studentName);
        results.add(student);
      }
    }
    print("result is ");

    if (results.isEmpty) {
      return [Text("No search found :(")];
    } else if (inputSearch == "") {
      return [];
    }
    return results;
  }
}
