import 'package:flutter/material.dart';

import '../UI_componenets/tournament_card_student.dart';
import '../requests.dart';

class ControllerStudent {
  ControllerStudent();
  List<TournamentCardStudent> tournamentsList = [];

  List<TournamentCardStudent> getTournaments() => tournamentsList;

  Future getTournamentsHttp() async {
    await Requests.getTournaments();
  }

  searchResult(String inputSearch) async {
    List<TournamentCardStudent> results = await Requests.getTournaments();
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
