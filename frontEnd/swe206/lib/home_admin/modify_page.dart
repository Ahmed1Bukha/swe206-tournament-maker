import 'package:flutter/material.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';

class ModifyPage extends StatefulWidget {
  ModifyPage(this.tournamentCardAdmin, {super.key});
  TournamentCardAdmin tournamentCardAdmin;
  @override
  State<ModifyPage> createState() => _ModifyPageState();
}

class _ModifyPageState extends State<ModifyPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Modify Tournament Page"),
      ),
    );
  }
}
