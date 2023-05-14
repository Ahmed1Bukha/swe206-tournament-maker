import 'dart:math';

import 'package:flutter/material.dart';
import 'package:graphview/GraphView.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';
import 'package:swe206/classes/visuals_tournaments.dart';
import 'package:swe206/home_student/register_team_page_student.dart';
import 'package:json_table/json_table.dart';
import '../UI_componenets/const.dart';
import '../requests.dart';

class TournamentPage extends StatefulWidget {
  const TournamentPage(this.tournamentWidget, {super.key});
  static String id = "TournamentPage";
  final TournamentCardStudent tournamentWidget;
  @override
  State<TournamentPage> createState() => _TournamentPageState();
}

class _TournamentPageState extends State<TournamentPage> {
  VisualsTournament visual = VisualsTournament();
  dynamic graph;
  getGraph() async {
    setState(() {
      isLoading = true;
    });
    if (widget.tournamentWidget.type == "EliminationTournament") {
      graph = await visual.getEleminationTournament(widget.tournamentWidget.id);
    } else if (widget.tournamentWidget.type == "RoundRobinTournament") {
      print("Round robin");
      graph = await visual.getRoundRobin(widget.tournamentWidget.id);
    } else {
      graph = Text("Error");
    }

    isLoading = false;
    setState(() {});
  }

  bool isOpen = true;
  bool isLoading = false;
  final snackBarRegistering = SnackBar(
    content: const Text('Processing'),
    action: SnackBarAction(
      label: 'Done',
      onPressed: () {
        // Some code to undo the change.
      },
    ),
  );
  final doneRegisterSnack = SnackBar(
    content: const Text('Done Registering.'),
    action: SnackBarAction(
      label: 'Done',
      onPressed: () {
        // Some code to undo the change.
      },
    ),
  );
  final alreadyRegistered = SnackBar(
    content: const Text('This ID is already registered to this tournament.'),
    action: SnackBarAction(
      label: 'Done',
      onPressed: () {},
    ),
  );

  @override
  void initState() {
    isOpen = widget.tournamentWidget.status == "true" ? true : false;
    print(isOpen);
    getGraph();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: IconThemeData(color: Colors.black),
        backgroundColor: Colors.white,
        title: Text(
          "Tournament Info",
          style: h2,
        ),
      ),
      body: isLoading
          ? CircularProgressIndicator()
          : SafeArea(
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: ListView(
                  children: [
                    Center(
                      child: Text(widget.tournamentWidget.title, style: h1),
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
                          "Game: ${widget.tournamentWidget.game}",
                          style: infoTournament,
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                        Text(
                          "Type: ${widget.tournamentWidget.type}",
                          style: infoTournament,
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text(
                          "TournamentBased: ${widget.tournamentWidget.based}",
                          style: infoTournament,
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text(
                          "Status: ${widget.tournamentWidget.status}",
                          style: infoTournament,
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text(
                          "StartDate: ${widget.tournamentWidget.startDate.split(":")[0]}",
                          style: infoTournament,
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text(
                          "StartDate: ${widget.tournamentWidget.endDate.split(":")[0]}",
                          style: infoTournament,
                        ),
                      ],
                    ),
                    Visibility(
                      visible: isOpen,
                      child: ElevatedButton.icon(
                        icon: Icon(Icons.plus_one),
                        onPressed: () async {
                          if (widget.tournamentWidget.based == "TEAM_BASED") {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => RegisterTeamStudent(
                                      widget.tournamentWidget),
                                ));
                          } else {
                            ScaffoldMessenger.of(context)
                                .showSnackBar(snackBarRegistering);
                            var response = await Requests.addParticipant(
                                widget.tournamentWidget.id);
                            ScaffoldMessenger.of(context).clearSnackBars();
                            if (response == "done") {
                              ScaffoldMessenger.of(context)
                                  .showSnackBar(doneRegisterSnack);
                            } else if (response == "registered") {
                              ScaffoldMessenger.of(context)
                                  .showSnackBar(alreadyRegistered);
                            }
                            Navigator.pop(context);
                          }
                        },
                        label: Text("Register Now!"),
                      ),
                    ),
                    SizedBox(
                      height: 40,
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
                            child: Expanded(
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
                            ),
                          )
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
    );
  }
}
