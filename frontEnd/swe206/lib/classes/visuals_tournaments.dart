import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:graphview/GraphView.dart';
import 'package:http/http.dart';
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

//     final String jsonSample =
//         '[{"name":"Ram","email":"ram@gmail.com","age":23,"DOB":"1990-12-01"},'
//         '{"name":"Shyam","email":"shyam23@gmail.com","age":18,"DOB":"1995-07-01"},'
//         '{"name":"John","email":"john@gmail.com","age":10,"DOB":"2000-02-24"},'
//         '{"name":"Ram","age":12,"DOB":"2000-02-01"}]';
//     var t = jsonDecode(jsonSample);
// //Create your column list
//     var columns = [
//       JsonTableColumn("name", label: "Name"),
//       JsonTableColumn("age", label: "Age"),
//       JsonTableColumn("email", label: "E-mail", defaultValue: "NA"),
//     ];
//     return JsonTable(t, columns: columns);

    int lengthList = json[0].length;

    List<JsonTableColumn> headers = [];
    for (int i = 0; i < lengthList; i++) {
      headers.add(JsonTableColumn("Round ${i + 1}", label: "Round ${i + 1}"));
    }
    print("roubin");
    return JsonTable(columns: headers, json);
  }

  getRoundRobinTable(int tournamentID) async {
    var json = await Requests.getRoundRobinJsonTable(tournamentID);
    print(json);
    List<JsonTableColumn> headers = [
      JsonTableColumn("Participant", label: "Participant"),
      JsonTableColumn("Score", label: "Score")
    ];
    return JsonTable(columns: headers, json);
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
