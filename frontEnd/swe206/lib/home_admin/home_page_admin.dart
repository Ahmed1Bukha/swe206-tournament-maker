import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';
import 'package:swe206/classes/controller_admin.dart';
import 'package:swe206/requests.dart';

import '../UI_componenets/const.dart';

class HomePageAdmin extends StatefulWidget {
  HomePageAdmin({super.key});
  static String id = "homeAdmin";

  @override
  State<HomePageAdmin> createState() => _HomePageAdminState();
}

class _HomePageAdminState extends State<HomePageAdmin> {
  bool isLoading = false;
  List<dynamic> tournamentsCard = [];
  List<MatchCardAdmin> matchsCard = [];
  Future getTournaments() async {
    setState(() {
      isLoading = true;
    });
    tournamentsCard = await Requests.getTournamentsAdmin();
    for (int i = 0; i < tournamentsCard.length; i++) {
      // matchsCard.add(new MatchCardAdmin());
    }
    setState(() {
      isLoading = false;
    });
  }

  @override
  void initState() {
    getTournaments();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: Text(
          "Home",
          style: h2,
        ),
      ),
      body: isLoading
          ? CircularProgressIndicator()
          : RefreshIndicator(
              onRefresh: getTournaments,
              child: ListView(
                children: [
                  Text("Matches:", style: h2),
                  new MatchCardAdmin(
                      id: 2,
                      participantA: "participantA",
                      participantB: "participantB",
                      game: "game",
                      date: "13/13/2013",
                      scoreA: 0,
                      scoreB: 0,
                      title: "Ass vs Tiddest",
                      endDate: "13/13/20/13",
                      finished: false),
                  Text("Tournaments:", style: h2),
                  ...tournamentsCard,
                ],
              ),
            ),
    );
  }
}
