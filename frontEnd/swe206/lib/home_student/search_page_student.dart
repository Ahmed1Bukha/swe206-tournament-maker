import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card.dart';
import 'package:swe206/classes/tournamentsManager.dart';

class SearchPageStudent extends StatefulWidget {
  SearchPageStudent(this.tournamentsManager, {super.key});
  TournamentsManager tournamentsManager;
  static String id = "SeachPageStudent";
  @override
  State<SearchPageStudent> createState() => _SearchPageStudentState();
}

class _SearchPageStudentState extends State<SearchPageStudent> {
  List<TournamentWidget> searchResult = [];
  String searchTerm = "";
  List<TournamentWidget> getSearchResult() => searchResult;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Search page"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              TextField(
                onChanged: (value) {
                  searchTerm = value;
                },
                decoration: const InputDecoration(
                  label: Text("Search"),
                  hintText: "Enter Tournament name",
                  border: OutlineInputBorder(),
                ),
              ),
              TextButton(
                onPressed: () {
                  setState(() {});
                },
                child: const Text("Search"),
              ),
              ...widget.tournamentsManager.searchResult(searchTerm),
            ],
          ),
        ),
      ),
    );
  }
}
