import 'package:flutter/material.dart';

import '../UI_componenets/tournament_card.dart';

class TournamentsManager {
  TournamentsManager(this.tournamentsList);
  List<TournamentWidget> tournamentsList = [];

  List<TournamentWidget> getTournaments() => tournamentsList;

  searchResult(String inputSearch) {
    List<TournamentWidget> results = [];
    for (TournamentWidget name in tournamentsList) {
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
