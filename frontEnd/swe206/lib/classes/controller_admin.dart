import 'package:swe206/UI_componenets/match_card_admin.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';

import '../UI_componenets/tournament_card_student.dart';

// For the most part it will get a joson file and i will have to convert it to
// List of tournamentWidget.
class ControllerAdmin {
  ControllerAdmin(this.tournamentsList);
  List<TournamentCardAdmin> tournamentsList = [];

  List<MatchCardAdmin> getMatches() {
    //init the list that will hold the cards for the matches
    List<MatchCardAdmin> matchesList = [];
    //Loop through every tournament.
    for (TournamentCardAdmin tournament in tournamentsList) {
      //Get the matches for a given tournament.
      Map<String, List<MatchCardAdmin>> matches = tournament.matches;
      //Loop though each match.
      matches.forEach((key, value) {
        for (int i = 0; i < value.length; i++) {
          //add the matches
          matchesList.add(value[i]);
        }
      });
    }
    return matchesList;
  }

  List<TournamentCardAdmin> getTournaments() => tournamentsList;
}
