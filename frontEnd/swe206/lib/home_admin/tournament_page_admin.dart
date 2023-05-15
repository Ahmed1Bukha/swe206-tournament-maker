import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';
import 'package:swe206/home_admin/modify_pages/modify_page_tournament.dart';

import '../UI_componenets/const.dart';
import '../classes/visuals_tournaments.dart';

class TournamentPageAdmin extends StatefulWidget {
  const TournamentPageAdmin(this.tournamentCard, {super.key});
  final TournamentCardAdmin tournamentCard;
  @override
  State<TournamentPageAdmin> createState() => _TournamentPageAdminState();
}

class _TournamentPageAdminState extends State<TournamentPageAdmin> {
  VisualsTournament visual = VisualsTournament();
  bool isModifiyable = true;
  bool enterMatchScore = false;
  dynamic graph;
  getGraph() async {
    setState(() {
      isLoading = true;
    });
    print(widget.tournamentCard.tournamentBased);
    if (widget.tournamentCard.tournamentBased == "EliminationTournament" &&
        !widget.tournamentCard.isOpen) {
      graph = await visual.getEleminationTournament(widget.tournamentCard.id);
    } else if (widget.tournamentCard.tournamentBased ==
            "RoundRobinTournament" &&
        !widget.tournamentCard.isOpen) {
      graph = await visual.getRoundRobin(widget.tournamentCard.id);
    } else {
      graph = Text("Error");
    }

    isLoading = false;
    setState(() {});
  }

  bool isOpen = true;
  bool isLoading = false;
  @override
  void initState() {
    isOpen = widget.tournamentCard.status == "true";
    if (widget.tournamentCard.status == "Finished") {
      isModifiyable = false;
    }
    getGraph();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(
          color: Colors.black,
        ),
        backgroundColor: Colors.white,
        elevation: 0,
        title: Text(
          "Tournament Info",
          style: h2,
        ),
      ),
      body: isLoading
          ? CircularProgressIndicator()
          : SafeArea(
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: ListView(
                    children: [
                      Center(
                        child: Text(
                          widget.tournamentCard.title,
                          style: h2,
                        ),
                      ),
                      const Card(
                        elevation: 20,
                        child: Image(
                          image: AssetImage("lib/assets/img/football.jpg"),
                        ),
                      ),
                      Column(
                        children: [
                          Text(
                            "Game: ${widget.tournamentCard.game}",
                            style: infoTournament,
                          ),
                          const SizedBox(
                            height: 10,
                          ),
                          Text(
                            "Type: ${widget.tournamentCard.tournamentBased}",
                            style: infoTournament,
                          ),
                          SizedBox(
                            height: 10,
                          ),
                          Text(
                            "TournamentBased: ${widget.tournamentCard.type}",
                            style: infoTournament,
                          ),
                          SizedBox(
                            height: 10,
                          ),
                          Text(
                            "Status: ${widget.tournamentCard.status}",
                            style: infoTournament,
                          ),
                          SizedBox(
                            height: 10,
                          ),
                          Text(
                            "StartDate: ${widget.tournamentCard.startDate.split(":")[0]}",
                            style: infoTournament,
                          ),
                          SizedBox(
                            height: 10,
                          ),
                          Text(
                            "EndDate: ${widget.tournamentCard.endDate.split(":")[0]}",
                            style: infoTournament,
                          ),
                        ],
                      ),
                      SizedBox(
                        height: 30,
                      ),
                      Visibility(
                        visible: widget.tournamentCard.isFinished,
                        child: Center(
                          child: Text(
                            "Winner is: ${widget.tournamentCard.winner}",
                            style: h3,
                          ),
                        ),
                      ),
                      SizedBox(
                        height: 30,
                      ),
                      Visibility(
                        visible: !widget.tournamentCard.isOpen &
                            !widget.tournamentCard.isFinished,
                        child: TextButton(
                          onPressed: () {
                            Navigator.pushReplacement(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => ModifyTournamentPage(
                                      widget.tournamentCard)),
                            );
                          },
                          child: Text("Enter Match Result"),
                        ),
                      ),
                      Visibility(
                        visible: !isOpen,
                        child: Column(
                          children: [
                            Center(
                              child: Text(
                                "Tournament Graph",
                                style: h3,
                              ),
                            ),
                            SizedBox(
                              height: 400,
                              child: InteractiveViewer(
                                scaleEnabled: false,
                                constrained: false,
                                boundaryMargin: EdgeInsets.all(400),
                                minScale: 0.01,
                                maxScale: 5.6,
                                child: isLoading
                                    ? CircularProgressIndicator()
                                    : graph,
                              ),
                            )
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
    );
  }
}
