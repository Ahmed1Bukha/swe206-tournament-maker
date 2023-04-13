import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/tournament_card.dart';

class TournamentPage extends StatefulWidget {
  const TournamentPage(this.tournamentWidget, {super.key});
  static String id = "TournamentPage";
  final TournamentWidget tournamentWidget;

  @override
  State<TournamentPage> createState() => _TournamentPageState();
}

class _TournamentPageState extends State<TournamentPage> {
  bool isOpen = true;
  @override
  void initState() {
    isOpen =
        widget.tournamentWidget.status == "Open for register" ? true : false;

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
            "Status: ${widget.tournamentWidget.status}",
            style: const TextStyle(fontSize: 20),
          ),
          Visibility(
            visible: isOpen,
            child: TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text("Register Now!"),
            ),
          )
        ],
      )),
    );
  }
}
