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
  Random r = Random();

  Widget rectangleWidget(int a) {
    return InkWell(
      onTap: () {
        print('clicked');
      },
      child: Container(
          padding: EdgeInsets.all(16),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(4),
          ),
          child: Text('Node ${a}')),
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
      onPressed: () {
        // Some code to undo the change.
      },
    ),
  );

  @override
  void initState() {
    isOpen = widget.tournamentWidget.status == "true" ? true : false;
    final node1 = Node.Id(1);
    final node2 = Node.Id(2);
    final node3 = Node.Id(3);
    final node4 = Node.Id(4);
    final node5 = Node.Id(5);
    final node6 = Node.Id(6);
    final node8 = Node.Id(7);
    final node7 = Node.Id(8);
    final node9 = Node.Id(9);
    final node10 = Node.Id(10);
    final node11 = Node.Id(11);
    final node12 = Node.Id(12);

    graph.addEdge(node1, node2);
    graph.addEdge(node1, node3, paint: Paint()..color = Colors.red);
    graph.addEdge(node1, node4, paint: Paint()..color = Colors.blue);
    graph.addEdge(node2, node5);
    graph.addEdge(node2, node6);
    graph.addEdge(node6, node7, paint: Paint()..color = Colors.red);
    graph.addEdge(node6, node8, paint: Paint()..color = Colors.red);
    graph.addEdge(node4, node9);
    graph.addEdge(node4, node10, paint: Paint()..color = Colors.black);
    graph.addEdge(node4, node11, paint: Paint()..color = Colors.red);
    graph.addEdge(node11, node12);

    builder
      ..siblingSeparation = (100)
      ..levelSeparation = (150)
      ..subtreeSeparation = (150)
      ..orientation = (BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM);

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
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.center,
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
              // Expanded(
              //   child: InteractiveViewer(
              //     constrained: false,
              //     boundaryMargin: EdgeInsets.all(100),
              //     minScale: 0.01,
              //     maxScale: 5.6,
              //     child: GraphView(
              //         graph: graph,
              //         algorithm: BuchheimWalkerAlgorithm(
              //             builder, TreeEdgeRenderer(builder)),
              //         builder: (Node node) {
              //           // I can decide what widget should be shown here based on the id
              //           var a = node.key?.value as int;
              //           return rectangleWidget(a);
              //         }),
              //   ),
              // )
            ],
          ),
        ),
      ),
    );
  }
}
