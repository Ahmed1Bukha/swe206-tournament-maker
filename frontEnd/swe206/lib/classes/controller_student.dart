import 'package:flutter/material.dart';

import '../UI_componenets/tournament_card_student.dart';
import '../requests.dart';

class ControllerStudent {
  ControllerStudent();

  Future getTournamentsHttp() async {
    await Requests.getTournaments();
  }

  searchResult(String inputSearch) async {
    List<dynamic> tournamentsList = await Requests.getTournaments();
    List<TournamentCardStudent> results = [];
    for (TournamentCardStudent name in tournamentsList) {
      if (name.title.toLowerCase().contains(inputSearch.toLowerCase())) {
        results.add(name);
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
