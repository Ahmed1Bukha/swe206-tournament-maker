import 'dart:math';

import 'package:flutter/material.dart';
import 'package:graphview/GraphView.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';
import 'package:swe206/home_student/register_team_page_student.dart';

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
        title: const Text("Tournament Page"),
      ),
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Center(
              child: Text(
                widget.tournamentWidget.title,
                style: const TextStyle(
                  fontSize: 30,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            Text(
              "Game: ${widget.tournamentWidget.game}",
              style: const TextStyle(fontSize: 20),
            ),
            Text(
              "Type: ${widget.tournamentWidget.type}",
              style: const TextStyle(fontSize: 20),
            ),
            Text(
              "TournamentBased: ${widget.tournamentWidget.based}",
              style: const TextStyle(fontSize: 20),
            ),
            Text(
              "Status: ${widget.tournamentWidget.status}",
              style: const TextStyle(fontSize: 20),
            ),
            Visibility(
              visible: isOpen,
              child: TextButton(
                onPressed: () async {
                  if (widget.tournamentWidget.based == "TEAM_BASED") {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) =>
                              RegisterTeamStudent(widget.tournamentWidget),
                        ));
                  } else {
                    await Requests.addParticipant(widget.tournamentWidget.id);
                    Navigator.pop(context);
                  }
                },
                child: Text("Register Now!"),
              ),
            ),
            Expanded(
              child: InteractiveViewer(
                constrained: false,
                boundaryMargin: EdgeInsets.all(100),
                minScale: 0.01,
                maxScale: 5.6,
                child: GraphView(
                    graph: graph,
                    algorithm: BuchheimWalkerAlgorithm(
                        builder, TreeEdgeRenderer(builder)),
                    builder: (Node node) {
                      // I can decide what widget should be shown here based on the id
                      var a = node.key?.value as int;
                      return rectangleWidget(a);
                    }),
              ),
            )
          ],
        ),
      ),
    );
  }
}
