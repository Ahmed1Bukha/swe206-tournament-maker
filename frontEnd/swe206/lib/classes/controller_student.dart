
import 'package:flutter/material.dart';

import '../UI_componenets/tournament_card_student.dart';

class ControllerStudent {
  ControllerStudent(this.tournamentsList);
  List<TournamentCardStudent> tournamentsList = [];

  List<TournamentCardStudent> getTournaments() => tournamentsList;

  searchResult(String inputSearch) {
    List<TournamentCardStudent> results = [];
    for (TournamentCardStudent name in tournamentsList) {
      if (name.title.toLowerCase().contains(inputSearch.toLowerCase())) {
        results.add(name);
      }
    }
    print(results);
    if (results.isEmpty) {
      return [Text("No search found :(")];
    } else if (inputSearch == "") {
      return [];
    }
    return results;
  }
}
