import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:graphview/GraphView.dart';
import 'package:json_table/json_table.dart';
import 'package:swe206/requests.dart';

class VisualsTournament {
  getEleminationTournament(int tournamentID) async {
    var json = await Requests.getEleminationJson(tournamentID);
    print(json);
    final Graph graph = Graph()..isTree = true;
    BuchheimWalkerConfiguration builder = BuchheimWalkerConfiguration();
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

    return GraphView(
        graph: graph,
        algorithm: BuchheimWalkerAlgorithm(builder, TreeEdgeRenderer(builder)),
        builder: (Node node) {
          print(node.key!.value);
          var a = node.key!.value as int;
          var nodes = json['nodes'];
          var nodeValue = nodes!.firstWhere((element) => element['id'] == a);

          return rectangleWidget(nodeValue['label'] as String);
        });
  }

  getRoundRobin(int tournamentID) async {
    var json = await Requests.getRoundRobinJson(tournamentID);

    return JsonTable(json);
  }
}

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
