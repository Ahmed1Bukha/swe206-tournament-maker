import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';

class TournamentPageAdmin extends StatefulWidget {
  const TournamentPageAdmin(this.tournamentCard, {super.key});
  final TournamentCardAdmin tournamentCard;
  @override
  State<TournamentPageAdmin> createState() => _TournamentPageAdminState();
}

class _TournamentPageAdminState extends State<TournamentPageAdmin> {
  bool isModifiyable = true;
  @override
  void initState() {
    if (widget.tournamentCard.status == "Finished") {
      isModifiyable = false;
    }
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Tournament Page"),
      ),
      body: SafeArea(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text(
                "Title: ${widget.tournamentCard.title}",
                style: TextStyle(fontSize: 20),
              ),
              Text(
                "Game: ${widget.tournamentCard.game}",
                style: TextStyle(fontSize: 20),
              ),
              Text(
                "Type: ${widget.tournamentCard.type}",
                style: TextStyle(fontSize: 20),
              ),
              Text(
                "Status: ${widget.tournamentCard.status}",
                style: TextStyle(fontSize: 20),
              ),
              Visibility(
                visible: isModifiyable,
                child: TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Text("Modifiy tournament"),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
