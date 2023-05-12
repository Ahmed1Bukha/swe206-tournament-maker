import 'dart:math';

import 'package:flutter/material.dart';
import 'package:graphview/GraphView.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';
import 'package:swe206/home_student/register_team_page_student.dart';

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
  var json = {
    "nodes": [
      {"id": 1, "label": ""},
      {"id": 2, "label": "ALGHURAIRI, HASSAN vs ahmed"},
      {"id": 3, "label": "gg vs mo"}
    ],
    "edges": [
      {"from": 1, "to": 2},
      {"from": 1, "to": 3}
    ]
  };

  Widget rectangleWidget(String title) {
    return InkWell(
      onTap: () {
        print('clicked');
      },
      child: Container(
          padding: EdgeInsets.all(16),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(4),
          ),
          child: Text(title)),
    );
  }

  final Graph graph = Graph()..isTree = true;
  BuchheimWalkerConfiguration builder = BuchheimWalkerConfiguration();
  bool isOpen = true;
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
    json["edges"]?.forEach((element) {
      var fromNodeId = element['from'];
      var toNodeId = element['to'];
      graph.addEdge(Node.Id(fromNodeId), Node.Id(toNodeId));
    });
    builder
      ..siblingSeparation = (100)
      ..levelSeparation = (150)
      ..subtreeSeparation = (150)
      ..orientation = (BuchheimWalkerConfiguration.ORIENTATION_LEFT_RIGHT);

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
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: ListView(
            children: [
              Center(
                child: Text(widget.tournamentWidget.title, style: h1),
              ),
              Card(
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
                            builder: (context) =>
                                RegisterTeamStudent(widget.tournamentWidget),
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
              SizedBox(
                height: 200,
                child: Expanded(
                  child: InteractiveViewer(
                    scaleEnabled: false,
                    constrained: false,
                    boundaryMargin: EdgeInsets.all(400),
                    minScale: 0.01,
                    maxScale: 5.6,
                    child: GraphView(
                        graph: graph,
                        algorithm: BuchheimWalkerAlgorithm(
                            builder, TreeEdgeRenderer(builder)),
                        builder: (Node node) {
                          print(node.key!.value);
                          var a = node.key!.value as int;
                          var nodes = json['nodes'];
                          var nodeValue = nodes!
                              .firstWhere((element) => element['id'] == a);

                          return rectangleWidget(nodeValue['label'] as String);
                        }),
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
